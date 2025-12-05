import { IDeveloper } from 'app/entities/developer/developer.model';

export interface IExperience {
  id: number;
  description?: string | null;
  category?: string | null;
  developer?: Pick<IDeveloper, 'id'> | null;
}

export type NewExperience = Omit<IExperience, 'id'> & { id: null };
