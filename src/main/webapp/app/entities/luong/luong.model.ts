import { INhanVien } from 'app/entities/nhan-vien/nhan-vien.model';

export interface ILuong {
  id: number;
  maLuong?: string | null;
  heSoLuong?: string | null;
  luongCb?: string | null;
  heSoPhuCap?: string | null;
  khoanCongThem?: string | null;
  khoanTru?: string | null;
  nhanVien?: Pick<INhanVien, 'id'> | null;
}

export type NewLuong = Omit<ILuong, 'id'> & { id: null };
