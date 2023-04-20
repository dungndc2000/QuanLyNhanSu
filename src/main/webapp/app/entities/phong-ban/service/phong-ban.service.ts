import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPhongBan, NewPhongBan } from '../phong-ban.model';

export type PartialUpdatePhongBan = Partial<IPhongBan> & Pick<IPhongBan, 'id'>;

export type EntityResponseType = HttpResponse<IPhongBan>;
export type EntityArrayResponseType = HttpResponse<IPhongBan[]>;

@Injectable({ providedIn: 'root' })
export class PhongBanService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/phong-bans');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(phongBan: NewPhongBan): Observable<EntityResponseType> {
    return this.http.post<IPhongBan>(this.resourceUrl, phongBan, { observe: 'response' });
  }

  update(phongBan: IPhongBan): Observable<EntityResponseType> {
    return this.http.put<IPhongBan>(`${this.resourceUrl}/${this.getPhongBanIdentifier(phongBan)}`, phongBan, { observe: 'response' });
  }

  partialUpdate(phongBan: PartialUpdatePhongBan): Observable<EntityResponseType> {
    return this.http.patch<IPhongBan>(`${this.resourceUrl}/${this.getPhongBanIdentifier(phongBan)}`, phongBan, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPhongBan>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPhongBan[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPhongBanIdentifier(phongBan: Pick<IPhongBan, 'id'>): number {
    return phongBan.id;
  }

  comparePhongBan(o1: Pick<IPhongBan, 'id'> | null, o2: Pick<IPhongBan, 'id'> | null): boolean {
    return o1 && o2 ? this.getPhongBanIdentifier(o1) === this.getPhongBanIdentifier(o2) : o1 === o2;
  }

  addPhongBanToCollectionIfMissing<Type extends Pick<IPhongBan, 'id'>>(
    phongBanCollection: Type[],
    ...phongBansToCheck: (Type | null | undefined)[]
  ): Type[] {
    const phongBans: Type[] = phongBansToCheck.filter(isPresent);
    if (phongBans.length > 0) {
      const phongBanCollectionIdentifiers = phongBanCollection.map(phongBanItem => this.getPhongBanIdentifier(phongBanItem)!);
      const phongBansToAdd = phongBans.filter(phongBanItem => {
        const phongBanIdentifier = this.getPhongBanIdentifier(phongBanItem);
        if (phongBanCollectionIdentifiers.includes(phongBanIdentifier)) {
          return false;
        }
        phongBanCollectionIdentifiers.push(phongBanIdentifier);
        return true;
      });
      return [...phongBansToAdd, ...phongBanCollection];
    }
    return phongBanCollection;
  }
}
