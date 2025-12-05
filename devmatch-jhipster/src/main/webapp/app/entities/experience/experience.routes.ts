import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import ExperienceResolve from './route/experience-routing-resolve.service';

const experienceRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/experience.component').then(m => m.ExperienceComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/experience-detail.component').then(m => m.ExperienceDetailComponent),
    resolve: {
      experience: ExperienceResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/experience-update.component').then(m => m.ExperienceUpdateComponent),
    resolve: {
      experience: ExperienceResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/experience-update.component').then(m => m.ExperienceUpdateComponent),
    resolve: {
      experience: ExperienceResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default experienceRoute;
