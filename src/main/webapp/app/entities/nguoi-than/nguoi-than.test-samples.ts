import { INguoiThan, NewNguoiThan } from './nguoi-than.model';

export const sampleWithRequiredData: INguoiThan = {
  id: 85243,
  maNT: 'deposit',
};

export const sampleWithPartialData: INguoiThan = {
  id: 94102,
  maNT: 'PNG Missouri',
  tenNT: 'ivory uniform',
};

export const sampleWithFullData: INguoiThan = {
  id: 30548,
  maNT: 'Account',
  tenNT: 'Human',
  sDT: 'Fantastic Administra',
};

export const sampleWithNewData: NewNguoiThan = {
  maNT: 'web-readiness',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
