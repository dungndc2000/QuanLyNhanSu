import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INhanVien, NewNhanVien } from '../nhan-vien.model';

export type PartialUpdateNhanVien = Partial<INhanVien> & Pick<INhanVien, 'id'>;

type RestOf<T extends INhanVien | NewNhanVien> = Omit<T, 'ngaySinh'> & {
  ngaySinh?: string | null;
};

export type RestNhanVien = RestOf<INhanVien>;

export type NewRestNhanVien = RestOf<NewNhanVien>;

export type PartialUpdateRestNhanVien = RestOf<PartialUpdateNhanVien>;

export type EntityResponseType = HttpResponse<INhanVien>;
export type EntityArrayResponseType = HttpResponse<INhanVien[]>;

@Injectable({ providedIn: 'root' })
export class NhanVienService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/nhan-viens');

  protected resourceUrl2 = this.applicationConfigService.getEndpointFor('api/getNvBymaNV');

  findBymaNV(maNV: number): Observable<EntityResponseType> {
    return this.http
      .get<RestNhanVien>(`${this.resourceUrl2}/${maNV}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(nhanVien: NewNhanVien): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(nhanVien);
    return this.http
      .post<RestNhanVien>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(nhanVien: INhanVien): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(nhanVien);
    return this.http
      .put<RestNhanVien>(`${this.resourceUrl}/${this.getNhanVienIdentifier(nhanVien)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(nhanVien: PartialUpdateNhanVien): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(nhanVien);
    return this.http
      .patch<RestNhanVien>(`${this.resourceUrl}/${this.getNhanVienIdentifier(nhanVien)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestNhanVien>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestNhanVien[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getNhanVienIdentifier(nhanVien: Pick<INhanVien, 'id'>): number {
    return nhanVien.id;
  }

  compareNhanVien(o1: Pick<INhanVien, 'id'> | null, o2: Pick<INhanVien, 'id'> | null): boolean {
    return o1 && o2 ? this.getNhanVienIdentifier(o1) === this.getNhanVienIdentifier(o2) : o1 === o2;
  }

  addNhanVienToCollectionIfMissing<Type extends Pick<INhanVien, 'id'>>(
    nhanVienCollection: Type[],
    ...nhanViensToCheck: (Type | null | undefined)[]
  ): Type[] {
    const nhanViens: Type[] = nhanViensToCheck.filter(isPresent);
    if (nhanViens.length > 0) {
      const nhanVienCollectionIdentifiers = nhanVienCollection.map(nhanVienItem => this.getNhanVienIdentifier(nhanVienItem)!);
      const nhanViensToAdd = nhanViens.filter(nhanVienItem => {
        const nhanVienIdentifier = this.getNhanVienIdentifier(nhanVienItem);
        if (nhanVienCollectionIdentifiers.includes(nhanVienIdentifier)) {
          return false;
        }
        nhanVienCollectionIdentifiers.push(nhanVienIdentifier);
        return true;
      });
      return [...nhanViensToAdd, ...nhanVienCollection];
    }
    return nhanVienCollection;
  }

  protected convertDateFromClient<T extends INhanVien | NewNhanVien | PartialUpdateNhanVien>(nhanVien: T): RestOf<T> {
    return {
      ...nhanVien,
      ngaySinh: nhanVien.ngaySinh?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restNhanVien: RestNhanVien): INhanVien {
    return {
      ...restNhanVien,
      ngaySinh: restNhanVien.ngaySinh ? dayjs(restNhanVien.ngaySinh) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestNhanVien>): HttpResponse<INhanVien> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestNhanVien[]>): HttpResponse<INhanVien[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
