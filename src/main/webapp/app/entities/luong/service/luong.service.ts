import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILuong, NewLuong } from '../luong.model';

export type PartialUpdateLuong = Partial<ILuong> & Pick<ILuong, 'id'>;

export type EntityResponseType = HttpResponse<ILuong>;
export type EntityArrayResponseType = HttpResponse<ILuong[]>;

@Injectable({ providedIn: 'root' })
export class LuongService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/luongs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(luong: NewLuong): Observable<EntityResponseType> {
    return this.http.post<ILuong>(this.resourceUrl, luong, { observe: 'response' });
  }

  update(luong: ILuong): Observable<EntityResponseType> {
    return this.http.put<ILuong>(`${this.resourceUrl}/${this.getLuongIdentifier(luong)}`, luong, { observe: 'response' });
  }

  partialUpdate(luong: PartialUpdateLuong): Observable<EntityResponseType> {
    return this.http.patch<ILuong>(`${this.resourceUrl}/${this.getLuongIdentifier(luong)}`, luong, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILuong>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILuong[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLuongIdentifier(luong: Pick<ILuong, 'id'>): number {
    return luong.id;
  }

  compareLuong(o1: Pick<ILuong, 'id'> | null, o2: Pick<ILuong, 'id'> | null): boolean {
    return o1 && o2 ? this.getLuongIdentifier(o1) === this.getLuongIdentifier(o2) : o1 === o2;
  }

  addLuongToCollectionIfMissing<Type extends Pick<ILuong, 'id'>>(
    luongCollection: Type[],
    ...luongsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const luongs: Type[] = luongsToCheck.filter(isPresent);
    if (luongs.length > 0) {
      const luongCollectionIdentifiers = luongCollection.map(luongItem => this.getLuongIdentifier(luongItem)!);
      const luongsToAdd = luongs.filter(luongItem => {
        const luongIdentifier = this.getLuongIdentifier(luongItem);
        if (luongCollectionIdentifiers.includes(luongIdentifier)) {
          return false;
        }
        luongCollectionIdentifiers.push(luongIdentifier);
        return true;
      });
      return [...luongsToAdd, ...luongCollection];
    }
    return luongCollection;
  }
}
