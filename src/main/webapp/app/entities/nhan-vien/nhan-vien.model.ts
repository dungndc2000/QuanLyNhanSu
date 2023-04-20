import dayjs from 'dayjs/esm';
import { INguoiThan } from 'app/entities/nguoi-than/nguoi-than.model';
import { IChucVu } from 'app/entities/chuc-vu/chuc-vu.model';
import { IPhongBan } from 'app/entities/phong-ban/phong-ban.model';
import { IChuyenMon } from 'app/entities/chuyen-mon/chuyen-mon.model';
import { ITrinhDoHV } from 'app/entities/trinh-do-hv/trinh-do-hv.model';

export interface INhanVien {
  id: number;
  maNV?: string | null;
  tenNV?: string | null;
  ngaySinh?: dayjs.Dayjs | null;
  gioiTinh?: string | null;
  diaChi?: string | null;
  soCMND?: string | null;
  sDT?: string | null;
  email?: string | null;
  heSoLuong?: string | null;
  nguoiThan?: Pick<INguoiThan, 'id'> | null;
  chucVu?: Pick<IChucVu, 'id'> | null;
  phongBan?: Pick<IPhongBan, 'id'> | null;
  chuyenMon?: Pick<IChuyenMon, 'id'> | null;
  trinhDoHV?: Pick<ITrinhDoHV, 'id'> | null;
}

export type NewNhanVien = Omit<INhanVien, 'id'> & { id: null };
