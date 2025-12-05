import { IExperience, NewExperience } from './experience.model';

export const sampleWithRequiredData: IExperience = {
  id: 30136,
  description: '../fake-data/blob/hipster.txt',
};

export const sampleWithPartialData: IExperience = {
  id: 9800,
  description: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IExperience = {
  id: 19981,
  description: '../fake-data/blob/hipster.txt',
  category: 'for shrilly',
};

export const sampleWithNewData: NewExperience = {
  description: '../fake-data/blob/hipster.txt',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
