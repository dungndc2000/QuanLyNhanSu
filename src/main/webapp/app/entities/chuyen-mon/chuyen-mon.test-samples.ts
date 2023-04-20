import { IChuyenMon, NewChuyenMon } from './chuyen-mon.model';

export const sampleWithRequiredData: IChuyenMon = {
  id: 16960,
  maChuyenMon: 'multi-task',
};

export const sampleWithPartialData: IChuyenMon = {
  id: 55498,
  maChuyenMon: 'Pizza',
};

export const sampleWithFullData: IChuyenMon = {
  id: 99660,
  maChuyenMon: 'mobile Đôn',
  tenChuyenMon: 'bluetooth Seychelles',
};

export const sampleWithNewData: NewChuyenMon = {
  maChuyenMon: 'magnetic',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
