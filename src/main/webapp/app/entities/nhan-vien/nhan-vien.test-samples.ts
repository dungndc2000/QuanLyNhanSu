import dayjs from 'dayjs/esm';

import { INhanVien, NewNhanVien } from './nhan-vien.model';

export const sampleWithRequiredData: INhanVien = {
  id: 24256,
  maNV: 'Bedfordshire Integration Chief',
  tenNV: 'Home',
  ngaySinh: dayjs('2023-04-18T06:29'),
  gioiTinh: 'structure wireless',
  soCMND: 'Metrics',
};

export const sampleWithPartialData: INhanVien = {
  id: 33123,
  maNV: 'deposit Manor Điển',
  tenNV: 'clicks-and-mortar synergies',
  ngaySinh: dayjs('2023-04-18T23:50'),
  gioiTinh: 'Mali generating',
  diaChi: 'withdrawal Pizza Automotive',
  soCMND: 'Metrics XSS coherent',
};

export const sampleWithFullData: INhanVien = {
  id: 43301,
  maNV: 'Soap Checking',
  tenNV: 'Macedonia real-time array',
  ngaySinh: dayjs('2023-04-19T02:50'),
  gioiTinh: 'management Virginia',
  diaChi: 'Borders',
  soCMND: 'Mouse',
  sDT: 'wireless C',
  email: 'HngNh74@yahoo.com',
  heSoLuong: 'Kansas',
};

export const sampleWithNewData: NewNhanVien = {
  maNV: 'schemas Botswana Incredible',
  tenNV: 'Account Handmade',
  ngaySinh: dayjs('2023-04-18T22:55'),
  gioiTinh: 'circuit',
  soCMND: 'Clothing optimal hack',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
