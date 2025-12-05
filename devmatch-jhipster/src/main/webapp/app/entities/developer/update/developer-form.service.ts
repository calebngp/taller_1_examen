import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDeveloper, NewDeveloper } from '../developer.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDeveloper for edit and NewDeveloperFormGroupInput for create.
 */
type DeveloperFormGroupInput = IDeveloper | PartialWithRequiredKeyOf<NewDeveloper>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IDeveloper | NewDeveloper> = Omit<T, 'fechaCreacion' | 'fechaModificacion'> & {
  fechaCreacion?: string | null;
  fechaModificacion?: string | null;
};

type DeveloperFormRawValue = FormValueOf<IDeveloper>;

type NewDeveloperFormRawValue = FormValueOf<NewDeveloper>;

type DeveloperFormDefaults = Pick<NewDeveloper, 'id' | 'fechaCreacion' | 'fechaModificacion' | 'skills'>;

type DeveloperFormGroupContent = {
  id: FormControl<DeveloperFormRawValue['id'] | NewDeveloper['id']>;
  name: FormControl<DeveloperFormRawValue['name']>;
  email: FormControl<DeveloperFormRawValue['email']>;
  experienceLevel: FormControl<DeveloperFormRawValue['experienceLevel']>;
  bio: FormControl<DeveloperFormRawValue['bio']>;
  location: FormControl<DeveloperFormRawValue['location']>;
  githubProfile: FormControl<DeveloperFormRawValue['githubProfile']>;
  linkedin: FormControl<DeveloperFormRawValue['linkedin']>;
  motivation: FormControl<DeveloperFormRawValue['motivation']>;
  usuarioCreacion: FormControl<DeveloperFormRawValue['usuarioCreacion']>;
  usuarioModificacion: FormControl<DeveloperFormRawValue['usuarioModificacion']>;
  fechaCreacion: FormControl<DeveloperFormRawValue['fechaCreacion']>;
  fechaModificacion: FormControl<DeveloperFormRawValue['fechaModificacion']>;
  skills: FormControl<DeveloperFormRawValue['skills']>;
};

export type DeveloperFormGroup = FormGroup<DeveloperFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DeveloperFormService {
  createDeveloperFormGroup(developer: DeveloperFormGroupInput = { id: null }): DeveloperFormGroup {
    const developerRawValue = this.convertDeveloperToDeveloperRawValue({
      ...this.getFormDefaults(),
      ...developer,
    });
    return new FormGroup<DeveloperFormGroupContent>({
      id: new FormControl(
        { value: developerRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(developerRawValue.name, {
        validators: [Validators.required, Validators.maxLength(200)],
      }),
      email: new FormControl(developerRawValue.email, {
        validators: [Validators.maxLength(200)],
      }),
      experienceLevel: new FormControl(developerRawValue.experienceLevel, {
        validators: [Validators.maxLength(50)],
      }),
      bio: new FormControl(developerRawValue.bio),
      location: new FormControl(developerRawValue.location, {
        validators: [Validators.maxLength(200)],
      }),
      githubProfile: new FormControl(developerRawValue.githubProfile, {
        validators: [Validators.maxLength(500)],
      }),
      linkedin: new FormControl(developerRawValue.linkedin, {
        validators: [Validators.maxLength(500)],
      }),
      motivation: new FormControl(developerRawValue.motivation),
      usuarioCreacion: new FormControl(developerRawValue.usuarioCreacion, {
        validators: [Validators.maxLength(100)],
      }),
      usuarioModificacion: new FormControl(developerRawValue.usuarioModificacion, {
        validators: [Validators.maxLength(100)],
      }),
      fechaCreacion: new FormControl(developerRawValue.fechaCreacion),
      fechaModificacion: new FormControl(developerRawValue.fechaModificacion),
      skills: new FormControl(developerRawValue.skills ?? []),
    });
  }

  getDeveloper(form: DeveloperFormGroup): IDeveloper | NewDeveloper {
    return this.convertDeveloperRawValueToDeveloper(form.getRawValue() as DeveloperFormRawValue | NewDeveloperFormRawValue);
  }

  resetForm(form: DeveloperFormGroup, developer: DeveloperFormGroupInput): void {
    const developerRawValue = this.convertDeveloperToDeveloperRawValue({ ...this.getFormDefaults(), ...developer });
    form.reset(
      {
        ...developerRawValue,
        id: { value: developerRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DeveloperFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      fechaCreacion: currentTime,
      fechaModificacion: currentTime,
      skills: [],
    };
  }

  private convertDeveloperRawValueToDeveloper(rawDeveloper: DeveloperFormRawValue | NewDeveloperFormRawValue): IDeveloper | NewDeveloper {
    return {
      ...rawDeveloper,
      fechaCreacion: dayjs(rawDeveloper.fechaCreacion, DATE_TIME_FORMAT),
      fechaModificacion: dayjs(rawDeveloper.fechaModificacion, DATE_TIME_FORMAT),
    };
  }

  private convertDeveloperToDeveloperRawValue(
    developer: IDeveloper | (Partial<NewDeveloper> & DeveloperFormDefaults),
  ): DeveloperFormRawValue | PartialWithRequiredKeyOf<NewDeveloperFormRawValue> {
    return {
      ...developer,
      fechaCreacion: developer.fechaCreacion ? developer.fechaCreacion.format(DATE_TIME_FORMAT) : undefined,
      fechaModificacion: developer.fechaModificacion ? developer.fechaModificacion.format(DATE_TIME_FORMAT) : undefined,
      skills: developer.skills ?? [],
    };
  }
}
