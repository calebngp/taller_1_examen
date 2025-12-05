import dayjs from 'dayjs/esm';

import { IProject, NewProject } from './project.model';

export const sampleWithRequiredData: IProject = {
  id: 22823,
  name: 'anxiously',
  description: '../fake-data/blob/hipster.txt',
  experienceLevel: 'despite step quash',
  projectType: 'ouch forager',
};

export const sampleWithPartialData: IProject = {
  id: 12852,
  name: 'likewise',
  description: '../fake-data/blob/hipster.txt',
  experienceLevel: 'wherever',
  projectType: 'wherever',
  usuarioCreacion: 'shush',
};

export const sampleWithFullData: IProject = {
  id: 1375,
  name: 'tray pack hydrolyze',
  description: '../fake-data/blob/hipster.txt',
  experienceLevel: 'as',
  projectType: 'about',
  status: 'painfully supposing',
  usuarioCreacion: 'yellow whose delicious',
  usuarioModificacion: 'gah part railway',
  fechaCreacion: dayjs('2025-12-05T15:59'),
  fechaModificacion: dayjs('2025-12-04T21:27'),
};

export const sampleWithNewData: NewProject = {
  name: 'zowie vice',
  description: '../fake-data/blob/hipster.txt',
  experienceLevel: 'ew to',
  projectType: 'how when hepatitis',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
