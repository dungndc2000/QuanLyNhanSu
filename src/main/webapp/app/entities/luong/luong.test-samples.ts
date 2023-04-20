import { ILuong, NewLuong } from './luong.model';

export const sampleWithRequiredData: ILuong = {
  id: 3300,
  maLuong: 'Tasty Cott',
};

export const sampleWithPartialData: ILuong = {
  id: 73572,
  maLuong: 'connect',
  heSoLuong: 'Developer ',
  luongCb: 'maroon Bedfordshire ',
  khoanTru: 'Jordan',
};

export const sampleWithFullData: ILuong = {
  id: 57193,
  maLuong: 'overriding',
  heSoLuong: 'Hercegovin',
  luongCb: 'South',
  heSoPhuCap: 'Mouse',
  khoanCongThem: 'Plastic Keyboard ove',
  khoanTru: 'primary Pants Kuwait',
};

export const sampleWithNewData: NewLuong = {
  maLuong: 'markets ma',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
