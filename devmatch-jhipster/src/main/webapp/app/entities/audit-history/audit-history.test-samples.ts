import dayjs from 'dayjs/esm';

import { IAuditHistory, NewAuditHistory } from './audit-history.model';

export const sampleWithRequiredData: IAuditHistory = {
  id: 2882,
  entityType: 'boo abaft yippee',
  entityId: 5320,
  fieldName: 'aside underneath',
  usuario: 'voluntarily',
  fechaModificacion: dayjs('2025-12-05T04:22'),
};

export const sampleWithPartialData: IAuditHistory = {
  id: 18221,
  entityType: 'tenderly whether',
  entityId: 19842,
  fieldName: 'yeast whoever past',
  newValue: '../fake-data/blob/hipster.txt',
  usuario: 'passionate',
  fechaModificacion: dayjs('2025-12-05T11:45'),
};

export const sampleWithFullData: IAuditHistory = {
  id: 5100,
  entityType: 'seemingly cope',
  entityId: 8646,
  fieldName: 'yowza drat',
  oldValue: '../fake-data/blob/hipster.txt',
  newValue: '../fake-data/blob/hipster.txt',
  usuario: 'onto yahoo',
  fechaModificacion: dayjs('2025-12-05T00:29'),
};

export const sampleWithNewData: NewAuditHistory = {
  entityType: 'pfft',
  entityId: 19751,
  fieldName: 'squiggly',
  usuario: 'hello',
  fechaModificacion: dayjs('2025-12-04T22:14'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
