import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { VolverInicioComponent } from './volver-inicio.component';

describe('VolverInicioComponent', () => {
  let component: VolverInicioComponent;
  let fixture: ComponentFixture<VolverInicioComponent>;
  let router: Router;

  beforeEach(async () => {
    const routerSpy = jasmine.createSpyObj('Router', ['navigate']);

    await TestBed.configureTestingModule({
      declarations: [VolverInicioComponent],
      providers: [
        { provide: Router, useValue: routerSpy }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(VolverInicioComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should navigate to home when volverAlInicio is called', () => {
    component.volverAlInicio();
    expect(router.navigate).toHaveBeenCalledWith(['/']);
  });
});


