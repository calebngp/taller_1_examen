import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDeveloper, NewDeveloper } from '../developer.model';

export type PartialUpdateDeveloper = Partial<IDeveloper> & Pick<IDeveloper, 'id'>;

type RestOf<T extends IDeveloper | NewDeveloper> = Omit<T, 'fechaCreacion' | 'fechaModificacion'> & {
  fechaCreacion?: string | null;
  fechaModificacion?: string | null;
};

export type RestDeveloper = RestOf<IDeveloper>;

export type NewRestDeveloper = RestOf<NewDeveloper>;

export type PartialUpdateRestDeveloper = RestOf<PartialUpdateDeveloper>;

export type EntityResponseType = HttpResponse<IDeveloper>;
export type EntityArrayResponseType = HttpResponse<IDeveloper[]>;

@Injectable({ providedIn: 'root' })
export class DeveloperService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/developers');

  create(developer: NewDeveloper): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(developer);
    return this.http
      .post<RestDeveloper>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(developer: IDeveloper): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(developer);
    return this.http
      .put<RestDeveloper>(`${this.resourceUrl}/${this.getDeveloperIdentifier(developer)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(developer: PartialUpdateDeveloper): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(developer);
    return this.http
      .patch<RestDeveloper>(`${this.resourceUrl}/${this.getDeveloperIdentifier(developer)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestDeveloper>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestDeveloper[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDeveloperIdentifier(developer: Pick<IDeveloper, 'id'>): number {
    return developer.id;
  }

  compareDeveloper(o1: Pick<IDeveloper, 'id'> | null, o2: Pick<IDeveloper, 'id'> | null): boolean {
    return o1 && o2 ? this.getDeveloperIdentifier(o1) === this.getDeveloperIdentifier(o2) : o1 === o2;
  }

  addDeveloperToCollectionIfMissing<Type extends Pick<IDeveloper, 'id'>>(
    developerCollection: Type[],
    ...developersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const developers: Type[] = developersToCheck.filter(isPresent);
    if (developers.length > 0) {
      const developerCollectionIdentifiers = developerCollection.map(developerItem => this.getDeveloperIdentifier(developerItem));
      const developersToAdd = developers.filter(developerItem => {
        const developerIdentifier = this.getDeveloperIdentifier(developerItem);
        if (developerCollectionIdentifiers.includes(developerIdentifier)) {
          return false;
        }
        developerCollectionIdentifiers.push(developerIdentifier);
        return true;
      });
      return [...developersToAdd, ...developerCollection];
    }
    return developerCollection;
  }

  protected convertDateFromClient<T extends IDeveloper | NewDeveloper | PartialUpdateDeveloper>(developer: T): RestOf<T> {
    return {
      ...developer,
      fechaCreacion: developer.fechaCreacion?.toJSON() ?? null,
      fechaModificacion: developer.fechaModificacion?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restDeveloper: RestDeveloper): IDeveloper {
    return {
      ...restDeveloper,
      fechaCreacion: restDeveloper.fechaCreacion ? dayjs(restDeveloper.fechaCreacion) : undefined,
      fechaModificacion: restDeveloper.fechaModificacion ? dayjs(restDeveloper.fechaModificacion) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestDeveloper>): HttpResponse<IDeveloper> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestDeveloper[]>): HttpResponse<IDeveloper[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
