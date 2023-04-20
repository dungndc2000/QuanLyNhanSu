export interface IPhongBan {
  id: number;
  maPB?: string | null;
  tenPB?: string | null;
  sDT?: string | null;
}

export type NewPhongBan = Omit<IPhongBan, 'id'> & { id: null };
