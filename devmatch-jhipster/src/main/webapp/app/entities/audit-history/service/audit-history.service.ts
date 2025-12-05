import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAuditHistory, NewAuditHistory } from '../audit-history.model';

export type PartialUpdateAuditHistory = Partial<IAuditHistory> & Pick<IAuditHistory, 'id'>;

type RestOf<T extends IAuditHistory | NewAuditHistory> = Omit<T, 'fechaModificacion'> & {
  fechaModificacion?: string | null;
};

export type RestAuditHistory = RestOf<IAuditHistory>;

export type NewRestAuditHistory = RestOf<NewAuditHistory>;

export type PartialUpdateRestAuditHistory = RestOf<PartialUpdateAuditHistory>;

export type EntityResponseType = HttpResponse<IAuditHistory>;
export type EntityArrayResponseType = HttpResponse<IAuditHistory[]>;

@Injectable({ providedIn: 'root' })
export class AuditHistoryService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/audit-histories');

  create(auditHistory: NewAuditHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(auditHistory);
    return this.http
      .post<RestAuditHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(auditHistory: IAuditHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(auditHistory);
    return this.http
      .put<RestAuditHistory>(`${this.resourceUrl}/${this.getAuditHistoryIdentifier(auditHistory)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(auditHistory: PartialUpdateAuditHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(auditHistory);
    return this.http
      .patch<RestAuditHistory>(`${this.resourceUrl}/${this.getAuditHistoryIdentifier(auditHistory)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAuditHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAuditHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAuditHistoryIdentifier(auditHistory: Pick<IAuditHistory, 'id'>): number {
    return auditHistory.id;
  }

  compareAuditHistory(o1: Pick<IAuditHistory, 'id'> | null, o2: Pick<IAuditHistory, 'id'> | null): boolean {
    return o1 && o2 ? this.getAuditHistoryIdentifier(o1) === this.getAuditHistoryIdentifier(o2) : o1 === o2;
  }

  addAuditHistoryToCollectionIfMissing<Type extends Pick<IAuditHistory, 'id'>>(
    auditHistoryCollection: Type[],
    ...auditHistoriesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const auditHistories: Type[] = auditHistoriesToCheck.filter(isPresent);
    if (auditHistories.length > 0) {
      const auditHistoryCollectionIdentifiers = auditHistoryCollection.map(auditHistoryItem =>
        this.getAuditHistoryIdentifier(auditHistoryItem),
      );
      const auditHistoriesToAdd = auditHistories.filter(auditHistoryItem => {
        const auditHistoryIdentifier = this.getAuditHistoryIdentifier(auditHistoryItem);
        if (auditHistoryCollectionIdentifiers.includes(auditHistoryIdentifier)) {
          return false;
        }
        auditHistoryCollectionIdentifiers.push(auditHistoryIdentifier);
        return true;
      });
      return [...auditHistoriesToAdd, ...auditHistoryCollection];
    }
    return auditHistoryCollection;
  }

  protected convertDateFromClient<T extends IAuditHistory | NewAuditHistory | PartialUpdateAuditHistory>(auditHistory: T): RestOf<T> {
    return {
      ...auditHistory,
      fechaModificacion: auditHistory.fechaModificacion?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restAuditHistory: RestAuditHistory): IAuditHistory {
    return {
      ...restAuditHistory,
      fechaModificacion: restAuditHistory.fechaModificacion ? dayjs(restAuditHistory.fechaModificacion) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAuditHistory>): HttpResponse<IAuditHistory> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestAuditHistory[]>): HttpResponse<IAuditHistory[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
