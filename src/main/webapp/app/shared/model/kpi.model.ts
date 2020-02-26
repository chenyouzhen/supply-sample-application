import { Moment } from 'moment';

export interface IKpi {
  id?: number;
  name?: string;
  phenomenonTime?: Moment;
  result?: string;
  resultTime?: Moment;
  description?: string;
  intervalTime?: number;
  type?: string;
  unitOfMeasurement?: string;
  reserve?: string;
  factoryName?: string;
  factoryId?: number;
  productName?: string;
  productId?: number;
}

export class Kpi implements IKpi {
  constructor(
    public id?: number,
    public name?: string,
    public phenomenonTime?: Moment,
    public result?: string,
    public resultTime?: Moment,
    public description?: string,
    public intervalTime?: number,
    public type?: string,
    public unitOfMeasurement?: string,
    public reserve?: string,
    public factoryName?: string,
    public factoryId?: number,
    public productName?: string,
    public productId?: number
  ) {}
}
