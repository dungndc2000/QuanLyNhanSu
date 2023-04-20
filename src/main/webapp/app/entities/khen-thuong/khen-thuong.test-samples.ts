import dayjs from 'dayjs/esm';

import { IKhenThuong, NewKhenThuong } from './khen-thuong.model';

export const sampleWithRequiredData: IKhenThuong = {
  id: 2737,
  soqd: 'customer I',
  ngayQd: dayjs('2023-04-18T23:47'),
};

export const sampleWithPartialData: IKhenThuong = {
  id: 48220,
  soqd: 'Sudan Vanu',
  ngayQd: dayjs('2023-04-18T07:32'),
  loai: 'Devolved scalable te',
  hinhThuc: 'Shoes Checking seize',
};

export const sampleWithFullData: IKhenThuong = {
  id: 74661,
  soqd: 'Electronic',
  ngayQd: dayjs('2023-04-18T21:00'),
  ten: 'input connect',
  loai: 'input',
  hinhThuc: 'Burundi Kazakhstan E',
  soTien: 'hack ivory Seamless',
  noiDung: 'Auto Internal teal',
};

export const sampleWithNewData: NewKhenThuong = {
  soqd: 'Lead',
  ngayQd: dayjs('2023-04-19T02:05'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
