import { Moment } from 'moment';

export interface IAlarm {
  id?: number;
  name?: string;
  phenomenonTime?: Moment;
  result?: string;
  resultTime?: Moment;
  description?: string;
  level?: string;
  status?: string;
  resolveTime?: Moment;
  details?: string;
  factoryName?: string;
  factoryId?: number;
}

export class Alarm implements IAlarm {
  constructor(
    public id?: number,
    public name?: string,
    public phenomenonTime?: Moment,
    public result?: string,
    public resultTime?: Moment,
    public description?: string,
    public level?: string,
    public status?: string,
    public resolveTime?: Moment,
    public details?: string,
    public factoryName?: string,
    public factoryId?: number
  ) {}
}
