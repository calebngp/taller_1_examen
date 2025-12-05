import dayjs from 'dayjs/esm';

import { IDeveloper, NewDeveloper } from './developer.model';

export const sampleWithRequiredData: IDeveloper = {
  id: 19917,
  name: 'disapprove',
};

export const sampleWithPartialData: IDeveloper = {
  id: 20819,
  name: 'frank dental',
  email: 'Gonzalo_TamayoTamayo29@gmail.com',
  bio: '../fake-data/blob/hipster.txt',
  linkedin: 'nor filter',
  motivation: '../fake-data/blob/hipster.txt',
  usuarioModificacion: 'next instantly',
};

export const sampleWithFullData: IDeveloper = {
  id: 27663,
  name: 'anenst zowie',
  email: 'Elsa36@hotmail.com',
  experienceLevel: 'reboot',
  bio: '../fake-data/blob/hipster.txt',
  location: 'decongestant as although',
  githubProfile: 'burgeon afore masquerade',
  linkedin: 'gift oof jacket',
  motivation: '../fake-data/blob/hipster.txt',
  usuarioCreacion: 'inject stuff versus',
  usuarioModificacion: 'westernize failing ill',
  fechaCreacion: dayjs('2025-12-04T21:21'),
  fechaModificacion: dayjs('2025-12-04T23:51'),
};

export const sampleWithNewData: NewDeveloper = {
  name: 'kindheartedly optimal',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
