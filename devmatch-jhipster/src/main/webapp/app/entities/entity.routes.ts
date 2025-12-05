import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'Authorities' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'technology',
    data: { pageTitle: 'Technologies' },
    loadChildren: () => import('./technology/technology.routes'),
  },
  {
    path: 'developer',
    data: { pageTitle: 'Developers' },
    loadChildren: () => import('./developer/developer.routes'),
  },
  {
    path: 'project',
    data: { pageTitle: 'Projects' },
    loadChildren: () => import('./project/project.routes'),
  },
  {
    path: 'experience',
    data: { pageTitle: 'Experiences' },
    loadChildren: () => import('./experience/experience.routes'),
  },
  {
    path: 'match-result',
    data: { pageTitle: 'MatchResults' },
    loadChildren: () => import('./match-result/match-result.routes'),
  },
  {
    path: 'audit-history',
    data: { pageTitle: 'AuditHistories' },
    loadChildren: () => import('./audit-history/audit-history.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
