import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import AuditHistoryResolve from './route/audit-history-routing-resolve.service';

const auditHistoryRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/audit-history.component').then(m => m.AuditHistoryComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/audit-history-detail.component').then(m => m.AuditHistoryDetailComponent),
    resolve: {
      auditHistory: AuditHistoryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/audit-history-update.component').then(m => m.AuditHistoryUpdateComponent),
    resolve: {
      auditHistory: AuditHistoryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/audit-history-update.component').then(m => m.AuditHistoryUpdateComponent),
    resolve: {
      auditHistory: AuditHistoryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default auditHistoryRoute;
