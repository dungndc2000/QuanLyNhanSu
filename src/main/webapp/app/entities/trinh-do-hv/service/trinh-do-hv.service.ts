import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITrinhDoHV, NewTrinhDoHV } from '../trinh-do-hv.model';

export type PartialUpdateTrinhDoHV = Partial<ITrinhDoHV> & Pick<ITrinhDoHV, 'id'>;

export type EntityResponseType = HttpResponse<ITrinhDoHV>;
export type EntityArrayResponseType = HttpResponse<ITrinhDoHV[]>;

@Injectable({ providedIn: 'root' })
export class TrinhDoHVService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/trinh-do-hvs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(trinhDoHV: NewTrinhDoHV): Observable<EntityResponseType> {
    return this.http.post<ITrinhDoHV>(this.resourceUrl, trinhDoHV, { observe: 'response' });
  }

  update(trinhDoHV: ITrinhDoHV): Observable<EntityResponseType> {
    return this.http.put<ITrinhDoHV>(`${this.resourceUrl}/${this.getTrinhDoHVIdentifier(trinhDoHV)}`, trinhDoHV, { observe: 'response' });
  }

  partialUpdate(trinhDoHV: PartialUpdateTrinhDoHV): Observable<EntityResponseType> {
    return this.http.patch<ITrinhDoHV>(`${this.resourceUrl}/${this.getTrinhDoHVIdentifier(trinhDoHV)}`, trinhDoHV, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITrinhDoHV>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITrinhDoHV[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTrinhDoHVIdentifier(trinhDoHV: Pick<ITrinhDoHV, 'id'>): number {
    return trinhDoHV.id;
  }

  compareTrinhDoHV(o1: Pick<ITrinhDoHV, 'id'> | null, o2: Pick<ITrinhDoHV, 'id'> | null): boolean {
    return o1 && o2 ? this.getTrinhDoHVIdentifier(o1) === this.getTrinhDoHVIdentifier(o2) : o1 === o2;
  }

  addTrinhDoHVToCollectionIfMissing<Type extends Pick<ITrinhDoHV, 'id'>>(
    trinhDoHVCollection: Type[],
    ...trinhDoHVSToCheck: (Type | null | undefined)[]
  ): Type[] {
    const trinhDoHVS: Type[] = trinhDoHVSToCheck.filter(isPresent);
    if (trinhDoHVS.length > 0) {
      const trinhDoHVCollectionIdentifiers = trinhDoHVCollection.map(trinhDoHVItem => this.getTrinhDoHVIdentifier(trinhDoHVItem)!);
      const trinhDoHVSToAdd = trinhDoHVS.filter(trinhDoHVItem => {
        const trinhDoHVIdentifier = this.getTrinhDoHVIdentifier(trinhDoHVItem);
        if (trinhDoHVCollectionIdentifiers.includes(trinhDoHVIdentifier)) {
          return false;
        }
        trinhDoHVCollectionIdentifiers.push(trinhDoHVIdentifier);
        return true;
      });
      return [...trinhDoHVSToAdd, ...trinhDoHVCollection];
    }
    return trinhDoHVCollection;
  }
}
