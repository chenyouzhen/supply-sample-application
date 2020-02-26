import { IObservation } from 'app/shared/model/observation.model';

export interface ILink {
  id?: number;
  name?: string;
  deviceId?: string;
  description?: string;
  observations?: IObservation[];
  assemblylineId?: number;
}

export class Link implements ILink {
  constructor(
    public id?: number,
    public name?: string,
    public deviceId?: string,
    public description?: string,
    public observations?: IObservation[],
    public assemblylineId?: number
  ) {}
}
