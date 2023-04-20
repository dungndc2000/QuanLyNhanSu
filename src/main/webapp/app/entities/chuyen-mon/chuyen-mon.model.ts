export interface IChuyenMon {
  id: number;
  maChuyenMon?: string | null;
  tenChuyenMon?: string | null;
}

export type NewChuyenMon = Omit<IChuyenMon, 'id'> & { id: null };
