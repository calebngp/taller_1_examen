import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import DeveloperResolve from './route/developer-routing-resolve.service';

const developerRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/developer.component').then(m => m.DeveloperComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/developer-detail.component').then(m => m.DeveloperDetailComponent),
    resolve: {
      developer: DeveloperResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/developer-update.component').then(m => m.DeveloperUpdateComponent),
    resolve: {
      developer: DeveloperResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/developer-update.component').then(m => m.DeveloperUpdateComponent),
    resolve: {
      developer: DeveloperResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default developerRoute;
