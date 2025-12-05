import dayjs from 'dayjs/esm';
import { ITechnology } from 'app/entities/technology/technology.model';

export interface IProject {
  id: number;
  name?: string | null;
  description?: string | null;
  experienceLevel?: string | null;
  projectType?: string | null;
  status?: string | null;
  usuarioCreacion?: string | null;
  usuarioModificacion?: string | null;
  fechaCreacion?: dayjs.Dayjs | null;
  fechaModificacion?: dayjs.Dayjs | null;
  requiredTechnologies?: Pick<ITechnology, 'id'>[] | null;
}

export type NewProject = Omit<IProject, 'id'> & { id: null };
