import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMatchResult, NewMatchResult } from '../match-result.model';

export type PartialUpdateMatchResult = Partial<IMatchResult> & Pick<IMatchResult, 'id'>;

export type EntityResponseType = HttpResponse<IMatchResult>;
export type EntityArrayResponseType = HttpResponse<IMatchResult[]>;

@Injectable({ providedIn: 'root' })
export class MatchResultService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/match-results');

  create(matchResult: NewMatchResult): Observable<EntityResponseType> {
    return this.http.post<IMatchResult>(this.resourceUrl, matchResult, { observe: 'response' });
  }

  update(matchResult: IMatchResult): Observable<EntityResponseType> {
    return this.http.put<IMatchResult>(`${this.resourceUrl}/${this.getMatchResultIdentifier(matchResult)}`, matchResult, {
      observe: 'response',
    });
  }

  partialUpdate(matchResult: PartialUpdateMatchResult): Observable<EntityResponseType> {
    return this.http.patch<IMatchResult>(`${this.resourceUrl}/${this.getMatchResultIdentifier(matchResult)}`, matchResult, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMatchResult>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMatchResult[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMatchResultIdentifier(matchResult: Pick<IMatchResult, 'id'>): number {
    return matchResult.id;
  }

  compareMatchResult(o1: Pick<IMatchResult, 'id'> | null, o2: Pick<IMatchResult, 'id'> | null): boolean {
    return o1 && o2 ? this.getMatchResultIdentifier(o1) === this.getMatchResultIdentifier(o2) : o1 === o2;
  }

  addMatchResultToCollectionIfMissing<Type extends Pick<IMatchResult, 'id'>>(
    matchResultCollection: Type[],
    ...matchResultsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const matchResults: Type[] = matchResultsToCheck.filter(isPresent);
    if (matchResults.length > 0) {
      const matchResultCollectionIdentifiers = matchResultCollection.map(matchResultItem => this.getMatchResultIdentifier(matchResultItem));
      const matchResultsToAdd = matchResults.filter(matchResultItem => {
        const matchResultIdentifier = this.getMatchResultIdentifier(matchResultItem);
        if (matchResultCollectionIdentifiers.includes(matchResultIdentifier)) {
          return false;
        }
        matchResultCollectionIdentifiers.push(matchResultIdentifier);
        return true;
      });
      return [...matchResultsToAdd, ...matchResultCollection];
    }
    return matchResultCollection;
  }
}
