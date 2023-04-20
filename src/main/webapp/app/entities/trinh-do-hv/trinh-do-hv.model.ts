export interface ITrinhDoHV {
  id: number;
  maTDHV?: string | null;
  tenTDHV?: string | null;
}

export type NewTrinhDoHV = Omit<ITrinhDoHV, 'id'> & { id: null };
