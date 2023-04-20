export interface IChucVu {
  id: number;
  maCV?: string | null;
  tenChucVu?: string | null;
  phuCap?: string | null;
  ghiChu?: string | null;
}

export type NewChucVu = Omit<IChucVu, 'id'> & { id: null };
