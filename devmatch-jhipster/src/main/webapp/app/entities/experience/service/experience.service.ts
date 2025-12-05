import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IExperience, NewExperience } from '../experience.model';

export type PartialUpdateExperience = Partial<IExperience> & Pick<IExperience, 'id'>;

export type EntityResponseType = HttpResponse<IExperience>;
export type EntityArrayResponseType = HttpResponse<IExperience[]>;

@Injectable({ providedIn: 'root' })
export class ExperienceService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/experiences');

  create(experience: NewExperience): Observable<EntityResponseType> {
    return this.http.post<IExperience>(this.resourceUrl, experience, { observe: 'response' });
  }

  update(experience: IExperience): Observable<EntityResponseType> {
    return this.http.put<IExperience>(`${this.resourceUrl}/${this.getExperienceIdentifier(experience)}`, experience, {
      observe: 'response',
    });
  }

  partialUpdate(experience: PartialUpdateExperience): Observable<EntityResponseType> {
    return this.http.patch<IExperience>(`${this.resourceUrl}/${this.getExperienceIdentifier(experience)}`, experience, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IExperience>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IExperience[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getExperienceIdentifier(experience: Pick<IExperience, 'id'>): number {
    return experience.id;
  }

  compareExperience(o1: Pick<IExperience, 'id'> | null, o2: Pick<IExperience, 'id'> | null): boolean {
    return o1 && o2 ? this.getExperienceIdentifier(o1) === this.getExperienceIdentifier(o2) : o1 === o2;
  }

  addExperienceToCollectionIfMissing<Type extends Pick<IExperience, 'id'>>(
    experienceCollection: Type[],
    ...experiencesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const experiences: Type[] = experiencesToCheck.filter(isPresent);
    if (experiences.length > 0) {
      const experienceCollectionIdentifiers = experienceCollection.map(experienceItem => this.getExperienceIdentifier(experienceItem));
      const experiencesToAdd = experiences.filter(experienceItem => {
        const experienceIdentifier = this.getExperienceIdentifier(experienceItem);
        if (experienceCollectionIdentifiers.includes(experienceIdentifier)) {
          return false;
        }
        experienceCollectionIdentifiers.push(experienceIdentifier);
        return true;
      });
      return [...experiencesToAdd, ...experienceCollection];
    }
    return experienceCollection;
  }
}
