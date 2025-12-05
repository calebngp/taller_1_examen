import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAuditHistory } from '../audit-history.model';
import { AuditHistoryService } from '../service/audit-history.service';

const auditHistoryResolve = (route: ActivatedRouteSnapshot): Observable<null | IAuditHistory> => {
  const id = route.params.id;
  if (id) {
    return inject(AuditHistoryService)
      .find(id)
      .pipe(
        mergeMap((auditHistory: HttpResponse<IAuditHistory>) => {
          if (auditHistory.body) {
            return of(auditHistory.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default auditHistoryResolve;
