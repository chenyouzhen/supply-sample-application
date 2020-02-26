import { IProduct } from 'app/shared/model/product.model';
import { IKpi } from 'app/shared/model/kpi.model';
import { IAlarm } from 'app/shared/model/alarm.model';

export interface IFactory {
  id?: number;
  name?: string;
  description?: string;
  scale?: string;
  location?: string;
  status?: string;
  products?: IProduct[];
  kpis?: IKpi[];
  alarms?: IAlarm[];
}

export class Factory implements IFactory {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public scale?: string,
    public location?: string,
    public status?: string,
    public products?: IProduct[],
    public kpis?: IKpi[],
    public alarms?: IAlarm[]
  ) {}
}
