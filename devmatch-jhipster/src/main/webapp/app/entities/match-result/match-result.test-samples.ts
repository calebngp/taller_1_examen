import { IMatchResult, NewMatchResult } from './match-result.model';

export const sampleWithRequiredData: IMatchResult = {
  id: 32602,
  technicalMatch: 7210.75,
  createdAt: 'jaggedly atop usefully',
};

export const sampleWithPartialData: IMatchResult = {
  id: 28078,
  technicalMatch: 25273.3,
  aiTechnicalAffinity: 9294,
  aiMotivationalAffinity: 30288,
  aiComment: '../fake-data/blob/hipster.txt',
  createdAt: 'before whoa frequent',
};

export const sampleWithFullData: IMatchResult = {
  id: 2839,
  technicalMatch: 2503.94,
  aiTechnicalAffinity: 21801,
  aiMotivationalAffinity: 39,
  aiExperienceRelevance: 9837,
  aiComment: '../fake-data/blob/hipster.txt',
  createdAt: 'whenever who',
};

export const sampleWithNewData: NewMatchResult = {
  technicalMatch: 2941.1,
  createdAt: 'hence zowie',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
