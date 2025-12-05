import dayjs from 'dayjs/esm';
import { ITechnology } from 'app/entities/technology/technology.model';

export interface IDeveloper {
  id: number;
  name?: string | null;
  email?: string | null;
  experienceLevel?: string | null;
  bio?: string | null;
  location?: string | null;
  githubProfile?: string | null;
  linkedin?: string | null;
  motivation?: string | null;
  usuarioCreacion?: string | null;
  usuarioModificacion?: string | null;
  fechaCreacion?: dayjs.Dayjs | null;
  fechaModificacion?: dayjs.Dayjs | null;
  skills?: Pick<ITechnology, 'id'>[] | null;
}

export type NewDeveloper = Omit<IDeveloper, 'id'> & { id: null };
