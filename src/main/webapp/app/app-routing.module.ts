import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { VolverInicioComponent } from './volver-inicio/volver-inicio.component';

const routes: Routes = [
  {
    path: 'volver',
    component: VolverInicioComponent,
  },
  // Add other routes here as needed
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }


