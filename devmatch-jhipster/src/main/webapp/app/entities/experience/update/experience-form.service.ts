import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IExperience, NewExperience } from '../experience.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IExperience for edit and NewExperienceFormGroupInput for create.
 */
type ExperienceFormGroupInput = IExperience | PartialWithRequiredKeyOf<NewExperience>;

type ExperienceFormDefaults = Pick<NewExperience, 'id'>;

type ExperienceFormGroupContent = {
  id: FormControl<IExperience['id'] | NewExperience['id']>;
  description: FormControl<IExperience['description']>;
  category: FormControl<IExperience['category']>;
  developer: FormControl<IExperience['developer']>;
};

export type ExperienceFormGroup = FormGroup<ExperienceFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ExperienceFormService {
  createExperienceFormGroup(experience: ExperienceFormGroupInput = { id: null }): ExperienceFormGroup {
    const experienceRawValue = {
      ...this.getFormDefaults(),
      ...experience,
    };
    return new FormGroup<ExperienceFormGroupContent>({
      id: new FormControl(
        { value: experienceRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      description: new FormControl(experienceRawValue.description, {
        validators: [Validators.required],
      }),
      category: new FormControl(experienceRawValue.category, {
        validators: [Validators.maxLength(100)],
      }),
      developer: new FormControl(experienceRawValue.developer, {
        validators: [Validators.required],
      }),
    });
  }

  getExperience(form: ExperienceFormGroup): IExperience | NewExperience {
    return form.getRawValue() as IExperience | NewExperience;
  }

  resetForm(form: ExperienceFormGroup, experience: ExperienceFormGroupInput): void {
    const experienceRawValue = { ...this.getFormDefaults(), ...experience };
    form.reset(
      {
        ...experienceRawValue,
        id: { value: experienceRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ExperienceFormDefaults {
    return {
      id: null,
    };
  }
}
