import dayjs from 'dayjs/esm';
import { INhanVien } from 'app/entities/nhan-vien/nhan-vien.model';

export interface IKhenThuong {
  id: number;
  soqd?: string | null;
  ngayQd?: dayjs.Dayjs | null;
  ten?: string | null;
  loai?: string | null;
  hinhThuc?: string | null;
  soTien?: string | null;
  noiDung?: string | null;
  nhanVien?: Pick<INhanVien, 'id'> | null;
}

export type NewKhenThuong = Omit<IKhenThuong, 'id'> & { id: null };
