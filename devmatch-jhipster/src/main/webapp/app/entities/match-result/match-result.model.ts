import { IProject } from 'app/entities/project/project.model';
import { IDeveloper } from 'app/entities/developer/developer.model';

export interface IMatchResult {
  id: number;
  technicalMatch?: number | null;
  aiTechnicalAffinity?: number | null;
  aiMotivationalAffinity?: number | null;
  aiExperienceRelevance?: number | null;
  aiComment?: string | null;
  createdAt?: string | null;
  project?: Pick<IProject, 'id'> | null;
  developer?: Pick<IDeveloper, 'id'> | null;
}

export type NewMatchResult = Omit<IMatchResult, 'id'> & { id: null };
