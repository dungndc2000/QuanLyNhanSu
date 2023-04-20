import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IChuyenMon, NewChuyenMon } from '../chuyen-mon.model';

export type PartialUpdateChuyenMon = Partial<IChuyenMon> & Pick<IChuyenMon, 'id'>;

export type EntityResponseType = HttpResponse<IChuyenMon>;
export type EntityArrayResponseType = HttpResponse<IChuyenMon[]>;

@Injectable({ providedIn: 'root' })
export class ChuyenMonService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/chuyen-mons');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(chuyenMon: NewChuyenMon): Observable<EntityResponseType> {
    return this.http.post<IChuyenMon>(this.resourceUrl, chuyenMon, { observe: 'response' });
  }

  update(chuyenMon: IChuyenMon): Observable<EntityResponseType> {
    return this.http.put<IChuyenMon>(`${this.resourceUrl}/${this.getChuyenMonIdentifier(chuyenMon)}`, chuyenMon, { observe: 'response' });
  }

  partialUpdate(chuyenMon: PartialUpdateChuyenMon): Observable<EntityResponseType> {
    return this.http.patch<IChuyenMon>(`${this.resourceUrl}/${this.getChuyenMonIdentifier(chuyenMon)}`, chuyenMon, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IChuyenMon>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IChuyenMon[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getChuyenMonIdentifier(chuyenMon: Pick<IChuyenMon, 'id'>): number {
    return chuyenMon.id;
  }

  compareChuyenMon(o1: Pick<IChuyenMon, 'id'> | null, o2: Pick<IChuyenMon, 'id'> | null): boolean {
    return o1 && o2 ? this.getChuyenMonIdentifier(o1) === this.getChuyenMonIdentifier(o2) : o1 === o2;
  }

  addChuyenMonToCollectionIfMissing<Type extends Pick<IChuyenMon, 'id'>>(
    chuyenMonCollection: Type[],
    ...chuyenMonsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const chuyenMons: Type[] = chuyenMonsToCheck.filter(isPresent);
    if (chuyenMons.length > 0) {
      const chuyenMonCollectionIdentifiers = chuyenMonCollection.map(chuyenMonItem => this.getChuyenMonIdentifier(chuyenMonItem)!);
      const chuyenMonsToAdd = chuyenMons.filter(chuyenMonItem => {
        const chuyenMonIdentifier = this.getChuyenMonIdentifier(chuyenMonItem);
        if (chuyenMonCollectionIdentifiers.includes(chuyenMonIdentifier)) {
          return false;
        }
        chuyenMonCollectionIdentifiers.push(chuyenMonIdentifier);
        return true;
      });
      return [...chuyenMonsToAdd, ...chuyenMonCollection];
    }
    return chuyenMonCollection;
  }
}
