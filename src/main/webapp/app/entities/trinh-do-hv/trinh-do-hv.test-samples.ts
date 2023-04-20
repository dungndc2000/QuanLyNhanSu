import { ITrinhDoHV, NewTrinhDoHV } from './trinh-do-hv.model';

export const sampleWithRequiredData: ITrinhDoHV = {
  id: 5845,
  maTDHV: 'Shoes Chips',
};

export const sampleWithPartialData: ITrinhDoHV = {
  id: 80633,
  maTDHV: 'hub',
};

export const sampleWithFullData: ITrinhDoHV = {
  id: 26637,
  maTDHV: 'Metal',
  tenTDHV: 'monitor',
};

export const sampleWithNewData: NewTrinhDoHV = {
  maTDHV: 'deposit explicit',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
