import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IProject, NewProject } from '../project.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProject for edit and NewProjectFormGroupInput for create.
 */
type ProjectFormGroupInput = IProject | PartialWithRequiredKeyOf<NewProject>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IProject | NewProject> = Omit<T, 'fechaCreacion' | 'fechaModificacion'> & {
  fechaCreacion?: string | null;
  fechaModificacion?: string | null;
};

type ProjectFormRawValue = FormValueOf<IProject>;

type NewProjectFormRawValue = FormValueOf<NewProject>;

type ProjectFormDefaults = Pick<NewProject, 'id' | 'fechaCreacion' | 'fechaModificacion' | 'requiredTechnologies'>;

type ProjectFormGroupContent = {
  id: FormControl<ProjectFormRawValue['id'] | NewProject['id']>;
  name: FormControl<ProjectFormRawValue['name']>;
  description: FormControl<ProjectFormRawValue['description']>;
  experienceLevel: FormControl<ProjectFormRawValue['experienceLevel']>;
  projectType: FormControl<ProjectFormRawValue['projectType']>;
  status: FormControl<ProjectFormRawValue['status']>;
  usuarioCreacion: FormControl<ProjectFormRawValue['usuarioCreacion']>;
  usuarioModificacion: FormControl<ProjectFormRawValue['usuarioModificacion']>;
  fechaCreacion: FormControl<ProjectFormRawValue['fechaCreacion']>;
  fechaModificacion: FormControl<ProjectFormRawValue['fechaModificacion']>;
  requiredTechnologies: FormControl<ProjectFormRawValue['requiredTechnologies']>;
};

export type ProjectFormGroup = FormGroup<ProjectFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProjectFormService {
  createProjectFormGroup(project: ProjectFormGroupInput = { id: null }): ProjectFormGroup {
    const projectRawValue = this.convertProjectToProjectRawValue({
      ...this.getFormDefaults(),
      ...project,
    });
    return new FormGroup<ProjectFormGroupContent>({
      id: new FormControl(
        { value: projectRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(projectRawValue.name, {
        validators: [Validators.required, Validators.maxLength(200)],
      }),
      description: new FormControl(projectRawValue.description, {
        validators: [Validators.required],
      }),
      experienceLevel: new FormControl(projectRawValue.experienceLevel, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      projectType: new FormControl(projectRawValue.projectType, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      status: new FormControl(projectRawValue.status, {
        validators: [Validators.maxLength(50)],
      }),
      usuarioCreacion: new FormControl(projectRawValue.usuarioCreacion, {
        validators: [Validators.maxLength(100)],
      }),
      usuarioModificacion: new FormControl(projectRawValue.usuarioModificacion, {
        validators: [Validators.maxLength(100)],
      }),
      fechaCreacion: new FormControl(projectRawValue.fechaCreacion),
      fechaModificacion: new FormControl(projectRawValue.fechaModificacion),
      requiredTechnologies: new FormControl(projectRawValue.requiredTechnologies ?? []),
    });
  }

  getProject(form: ProjectFormGroup): IProject | NewProject {
    return this.convertProjectRawValueToProject(form.getRawValue() as ProjectFormRawValue | NewProjectFormRawValue);
  }

  resetForm(form: ProjectFormGroup, project: ProjectFormGroupInput): void {
    const projectRawValue = this.convertProjectToProjectRawValue({ ...this.getFormDefaults(), ...project });
    form.reset(
      {
        ...projectRawValue,
        id: { value: projectRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ProjectFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      fechaCreacion: currentTime,
      fechaModificacion: currentTime,
      requiredTechnologies: [],
    };
  }

  private convertProjectRawValueToProject(rawProject: ProjectFormRawValue | NewProjectFormRawValue): IProject | NewProject {
    return {
      ...rawProject,
      fechaCreacion: dayjs(rawProject.fechaCreacion, DATE_TIME_FORMAT),
      fechaModificacion: dayjs(rawProject.fechaModificacion, DATE_TIME_FORMAT),
    };
  }

  private convertProjectToProjectRawValue(
    project: IProject | (Partial<NewProject> & ProjectFormDefaults),
  ): ProjectFormRawValue | PartialWithRequiredKeyOf<NewProjectFormRawValue> {
    return {
      ...project,
      fechaCreacion: project.fechaCreacion ? project.fechaCreacion.format(DATE_TIME_FORMAT) : undefined,
      fechaModificacion: project.fechaModificacion ? project.fechaModificacion.format(DATE_TIME_FORMAT) : undefined,
      requiredTechnologies: project.requiredTechnologies ?? [],
    };
  }
}
