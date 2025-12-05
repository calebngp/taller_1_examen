import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAuditHistory, NewAuditHistory } from '../audit-history.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAuditHistory for edit and NewAuditHistoryFormGroupInput for create.
 */
type AuditHistoryFormGroupInput = IAuditHistory | PartialWithRequiredKeyOf<NewAuditHistory>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAuditHistory | NewAuditHistory> = Omit<T, 'fechaModificacion'> & {
  fechaModificacion?: string | null;
};

type AuditHistoryFormRawValue = FormValueOf<IAuditHistory>;

type NewAuditHistoryFormRawValue = FormValueOf<NewAuditHistory>;

type AuditHistoryFormDefaults = Pick<NewAuditHistory, 'id' | 'fechaModificacion'>;

type AuditHistoryFormGroupContent = {
  id: FormControl<AuditHistoryFormRawValue['id'] | NewAuditHistory['id']>;
  entityType: FormControl<AuditHistoryFormRawValue['entityType']>;
  entityId: FormControl<AuditHistoryFormRawValue['entityId']>;
  fieldName: FormControl<AuditHistoryFormRawValue['fieldName']>;
  oldValue: FormControl<AuditHistoryFormRawValue['oldValue']>;
  newValue: FormControl<AuditHistoryFormRawValue['newValue']>;
  usuario: FormControl<AuditHistoryFormRawValue['usuario']>;
  fechaModificacion: FormControl<AuditHistoryFormRawValue['fechaModificacion']>;
};

export type AuditHistoryFormGroup = FormGroup<AuditHistoryFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AuditHistoryFormService {
  createAuditHistoryFormGroup(auditHistory: AuditHistoryFormGroupInput = { id: null }): AuditHistoryFormGroup {
    const auditHistoryRawValue = this.convertAuditHistoryToAuditHistoryRawValue({
      ...this.getFormDefaults(),
      ...auditHistory,
    });
    return new FormGroup<AuditHistoryFormGroupContent>({
      id: new FormControl(
        { value: auditHistoryRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      entityType: new FormControl(auditHistoryRawValue.entityType, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      entityId: new FormControl(auditHistoryRawValue.entityId, {
        validators: [Validators.required],
      }),
      fieldName: new FormControl(auditHistoryRawValue.fieldName, {
        validators: [Validators.required, Validators.maxLength(100)],
      }),
      oldValue: new FormControl(auditHistoryRawValue.oldValue),
      newValue: new FormControl(auditHistoryRawValue.newValue),
      usuario: new FormControl(auditHistoryRawValue.usuario, {
        validators: [Validators.required, Validators.maxLength(100)],
      }),
      fechaModificacion: new FormControl(auditHistoryRawValue.fechaModificacion, {
        validators: [Validators.required],
      }),
    });
  }

  getAuditHistory(form: AuditHistoryFormGroup): IAuditHistory | NewAuditHistory {
    return this.convertAuditHistoryRawValueToAuditHistory(form.getRawValue() as AuditHistoryFormRawValue | NewAuditHistoryFormRawValue);
  }

  resetForm(form: AuditHistoryFormGroup, auditHistory: AuditHistoryFormGroupInput): void {
    const auditHistoryRawValue = this.convertAuditHistoryToAuditHistoryRawValue({ ...this.getFormDefaults(), ...auditHistory });
    form.reset(
      {
        ...auditHistoryRawValue,
        id: { value: auditHistoryRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AuditHistoryFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      fechaModificacion: currentTime,
    };
  }

  private convertAuditHistoryRawValueToAuditHistory(
    rawAuditHistory: AuditHistoryFormRawValue | NewAuditHistoryFormRawValue,
  ): IAuditHistory | NewAuditHistory {
    return {
      ...rawAuditHistory,
      fechaModificacion: dayjs(rawAuditHistory.fechaModificacion, DATE_TIME_FORMAT),
    };
  }

  private convertAuditHistoryToAuditHistoryRawValue(
    auditHistory: IAuditHistory | (Partial<NewAuditHistory> & AuditHistoryFormDefaults),
  ): AuditHistoryFormRawValue | PartialWithRequiredKeyOf<NewAuditHistoryFormRawValue> {
    return {
      ...auditHistory,
      fechaModificacion: auditHistory.fechaModificacion ? auditHistory.fechaModificacion.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
