import { IPhongBan, NewPhongBan } from './phong-ban.model';

export const sampleWithRequiredData: IPhongBan = {
  id: 13395,
  maPB: 'relationsh',
};

export const sampleWithPartialData: IPhongBan = {
  id: 95741,
  maPB: 'New',
  sDT: 'matrix deposit Incre',
};

export const sampleWithFullData: IPhongBan = {
  id: 63933,
  maPB: 'Mobility',
  tenPB: 'payment orange Oman',
  sDT: 'Games Dong Computer',
};

export const sampleWithNewData: NewPhongBan = {
  maPB: 'Ergonomic',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
