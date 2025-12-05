import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMatchResult } from '../match-result.model';
import { MatchResultService } from '../service/match-result.service';

const matchResultResolve = (route: ActivatedRouteSnapshot): Observable<null | IMatchResult> => {
  const id = route.params.id;
  if (id) {
    return inject(MatchResultService)
      .find(id)
      .pipe(
        mergeMap((matchResult: HttpResponse<IMatchResult>) => {
          if (matchResult.body) {
            return of(matchResult.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default matchResultResolve;
