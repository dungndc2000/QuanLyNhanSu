export interface INguoiThan {
  id: number;
  maNT?: string | null;
  tenNT?: string | null;
  sDT?: string | null;
}

export type NewNguoiThan = Omit<INguoiThan, 'id'> & { id: null };
