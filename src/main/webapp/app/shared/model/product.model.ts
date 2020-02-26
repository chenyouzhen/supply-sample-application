import { Moment } from 'moment';
import { IAssemblyLine } from 'app/shared/model/assembly-line.model';
import { IKpi } from 'app/shared/model/kpi.model';

export interface IProduct {
  id?: number;
  name?: string;
  type?: string;
  description?: string;
  capacity?: Moment;
  planCapacity?: Moment;
  reserve?: string;
  assemblylines?: IAssemblyLine[];
  kpis?: IKpi[];
  factoryName?: string;
  factoryId?: number;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public name?: string,
    public type?: string,
    public description?: string,
    public capacity?: Moment,
    public planCapacity?: Moment,
    public reserve?: string,
    public assemblylines?: IAssemblyLine[],
    public kpis?: IKpi[],
    public factoryName?: string,
    public factoryId?: number
  ) {}
}
