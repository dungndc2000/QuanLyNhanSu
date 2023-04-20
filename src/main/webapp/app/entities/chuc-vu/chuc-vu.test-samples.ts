import { IChucVu, NewChucVu } from './chuc-vu.model';

export const sampleWithRequiredData: IChucVu = {
  id: 47666,
  maCV: 'Interactio',
};

export const sampleWithPartialData: IChucVu = {
  id: 45052,
  maCV: 'AGP Sleek',
  tenChucVu: 'COM Account Wooden',
};

export const sampleWithFullData: IChucVu = {
  id: 44210,
  maCV: 'Beauty Che',
  tenChucVu: 'compressing Mews quantify',
  phuCap: 'Principal',
  ghiChu: 'transmitting',
};

export const sampleWithNewData: NewChucVu = {
  maCV: 'Guatemala',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
