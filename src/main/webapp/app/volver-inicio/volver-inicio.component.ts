import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'jhi-volver-inicio',
  templateUrl: './volver-inicio.component.html',
  styleUrls: ['./volver-inicio.component.scss']
})
export class VolverInicioComponent {
  constructor(private router: Router) {}

  volverAlInicio(): void {
    this.router.navigate(['/']);
  }
}


