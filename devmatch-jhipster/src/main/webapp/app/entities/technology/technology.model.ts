import { IDeveloper } from 'app/entities/developer/developer.model';
import { IProject } from 'app/entities/project/project.model';

export interface ITechnology {
  id: number;
  name?: string | null;
  category?: string | null;
  developers?: Pick<IDeveloper, 'id'>[] | null;
  projects?: Pick<IProject, 'id'>[] | null;
}

export type NewTechnology = Omit<ITechnology, 'id'> & { id: null };
