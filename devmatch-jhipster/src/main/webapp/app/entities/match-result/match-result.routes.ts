import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import MatchResultResolve from './route/match-result-routing-resolve.service';

const matchResultRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/match-result.component').then(m => m.MatchResultComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/match-result-detail.component').then(m => m.MatchResultDetailComponent),
    resolve: {
      matchResult: MatchResultResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/match-result-update.component').then(m => m.MatchResultUpdateComponent),
    resolve: {
      matchResult: MatchResultResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/match-result-update.component').then(m => m.MatchResultUpdateComponent),
    resolve: {
      matchResult: MatchResultResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default matchResultRoute;
