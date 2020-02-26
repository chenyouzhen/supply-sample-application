import { Moment } from 'moment';
import { IObservation } from 'app/shared/model/observation.model';
import { IRecord } from 'app/shared/model/record.model';

export interface IAssemblyLine {
  id?: number;
  name?: string;
  type?: string;
  description?: string;
  capacity?: Moment;
  planCapacity?: Moment;
  reserve?: string;
  linkName?: string;
  linkId?: number;
  observations?: IObservation[];
  records?: IRecord[];
  productName?: string;
  productId?: number;
}

export class AssemblyLine implements IAssemblyLine {
  constructor(
    public id?: number,
    public name?: string,
    public type?: string,
    public description?: string,
    public capacity?: Moment,
    public planCapacity?: Moment,
    public reserve?: string,
    public linkName?: string,
    public linkId?: number,
    public observations?: IObservation[],
    public records?: IRecord[],
    public productName?: string,
    public productId?: number
  ) {}
}
