import dayjs from 'dayjs/esm';

export interface IAuditHistory {
  id: number;
  entityType?: string | null;
  entityId?: number | null;
  fieldName?: string | null;
  oldValue?: string | null;
  newValue?: string | null;
  usuario?: string | null;
  fechaModificacion?: dayjs.Dayjs | null;
}

export type NewAuditHistory = Omit<IAuditHistory, 'id'> & { id: null };
