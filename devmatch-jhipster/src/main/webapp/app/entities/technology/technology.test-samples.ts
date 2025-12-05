import { ITechnology, NewTechnology } from './technology.model';

export const sampleWithRequiredData: ITechnology = {
  id: 14116,
  name: 'arrange before',
};

export const sampleWithPartialData: ITechnology = {
  id: 26477,
  name: 'hmph gadzooks',
};

export const sampleWithFullData: ITechnology = {
  id: 21387,
  name: 'hydrolyze',
  category: 'er',
};

export const sampleWithNewData: NewTechnology = {
  name: 'fruitful from refine',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
