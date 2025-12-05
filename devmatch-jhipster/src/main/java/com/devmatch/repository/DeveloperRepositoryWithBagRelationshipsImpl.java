package com.devmatch.repository;

import com.devmatch.domain.Developer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class DeveloperRepositoryWithBagRelationshipsImpl implements DeveloperRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String DEVELOPERS_PARAMETER = "developers";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Developer> fetchBagRelationships(Optional<Developer> developer) {
        return developer.map(this::fetchSkills);
    }

    @Override
    public Page<Developer> fetchBagRelationships(Page<Developer> developers) {
        return new PageImpl<>(fetchBagRelationships(developers.getContent()), developers.getPageable(), developers.getTotalElements());
    }

    @Override
    public List<Developer> fetchBagRelationships(List<Developer> developers) {
        return Optional.of(developers).map(this::fetchSkills).orElse(Collections.emptyList());
    }

    Developer fetchSkills(Developer result) {
        return entityManager
            .createQuery(
                "select developer from Developer developer left join fetch developer.skills where developer.id = :id",
                Developer.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Developer> fetchSkills(List<Developer> developers) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, developers.size()).forEach(index -> order.put(developers.get(index).getId(), index));
        List<Developer> result = entityManager
            .createQuery(
                "select developer from Developer developer left join fetch developer.skills where developer in :developers",
                Developer.class
            )
            .setParameter(DEVELOPERS_PARAMETER, developers)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
