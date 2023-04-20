import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IChucVu, NewChucVu } from '../chuc-vu.model';

export type PartialUpdateChucVu = Partial<IChucVu> & Pick<IChucVu, 'id'>;

export type EntityResponseType = HttpResponse<IChucVu>;
export type EntityArrayResponseType = HttpResponse<IChucVu[]>;

@Injectable({ providedIn: 'root' })
export class ChucVuService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/chuc-vus');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(chucVu: NewChucVu): Observable<EntityResponseType> {
    return this.http.post<IChucVu>(this.resourceUrl, chucVu, { observe: 'response' });
  }

  update(chucVu: IChucVu): Observable<EntityResponseType> {
    return this.http.put<IChucVu>(`${this.resourceUrl}/${this.getChucVuIdentifier(chucVu)}`, chucVu, { observe: 'response' });
  }

  partialUpdate(chucVu: PartialUpdateChucVu): Observable<EntityResponseType> {
    return this.http.patch<IChucVu>(`${this.resourceUrl}/${this.getChucVuIdentifier(chucVu)}`, chucVu, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IChucVu>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IChucVu[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getChucVuIdentifier(chucVu: Pick<IChucVu, 'id'>): number {
    return chucVu.id;
  }

  compareChucVu(o1: Pick<IChucVu, 'id'> | null, o2: Pick<IChucVu, 'id'> | null): boolean {
    return o1 && o2 ? this.getChucVuIdentifier(o1) === this.getChucVuIdentifier(o2) : o1 === o2;
  }

  addChucVuToCollectionIfMissing<Type extends Pick<IChucVu, 'id'>>(
    chucVuCollection: Type[],
    ...chucVusToCheck: (Type | null | undefined)[]
  ): Type[] {
    const chucVus: Type[] = chucVusToCheck.filter(isPresent);
    if (chucVus.length > 0) {
      const chucVuCollectionIdentifiers = chucVuCollection.map(chucVuItem => this.getChucVuIdentifier(chucVuItem)!);
      const chucVusToAdd = chucVus.filter(chucVuItem => {
        const chucVuIdentifier = this.getChucVuIdentifier(chucVuItem);
        if (chucVuCollectionIdentifiers.includes(chucVuIdentifier)) {
          return false;
        }
        chucVuCollectionIdentifiers.push(chucVuIdentifier);
        return true;
      });
      return [...chucVusToAdd, ...chucVuCollection];
    }
    return chucVuCollection;
  }
}
