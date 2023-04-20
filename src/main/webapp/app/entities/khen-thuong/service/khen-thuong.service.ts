import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IKhenThuong, NewKhenThuong } from '../khen-thuong.model';

export type PartialUpdateKhenThuong = Partial<IKhenThuong> & Pick<IKhenThuong, 'id'>;

type RestOf<T extends IKhenThuong | NewKhenThuong> = Omit<T, 'ngayQd'> & {
  ngayQd?: string | null;
};

export type RestKhenThuong = RestOf<IKhenThuong>;

export type NewRestKhenThuong = RestOf<NewKhenThuong>;

export type PartialUpdateRestKhenThuong = RestOf<PartialUpdateKhenThuong>;

export type EntityResponseType = HttpResponse<IKhenThuong>;
export type EntityArrayResponseType = HttpResponse<IKhenThuong[]>;

@Injectable({ providedIn: 'root' })
export class KhenThuongService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/khen-thuongs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(khenThuong: NewKhenThuong): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(khenThuong);
    return this.http
      .post<RestKhenThuong>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(khenThuong: IKhenThuong): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(khenThuong);
    return this.http
      .put<RestKhenThuong>(`${this.resourceUrl}/${this.getKhenThuongIdentifier(khenThuong)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(khenThuong: PartialUpdateKhenThuong): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(khenThuong);
    return this.http
      .patch<RestKhenThuong>(`${this.resourceUrl}/${this.getKhenThuongIdentifier(khenThuong)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestKhenThuong>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestKhenThuong[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getKhenThuongIdentifier(khenThuong: Pick<IKhenThuong, 'id'>): number {
    return khenThuong.id;
  }

  compareKhenThuong(o1: Pick<IKhenThuong, 'id'> | null, o2: Pick<IKhenThuong, 'id'> | null): boolean {
    return o1 && o2 ? this.getKhenThuongIdentifier(o1) === this.getKhenThuongIdentifier(o2) : o1 === o2;
  }

  addKhenThuongToCollectionIfMissing<Type extends Pick<IKhenThuong, 'id'>>(
    khenThuongCollection: Type[],
    ...khenThuongsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const khenThuongs: Type[] = khenThuongsToCheck.filter(isPresent);
    if (khenThuongs.length > 0) {
      const khenThuongCollectionIdentifiers = khenThuongCollection.map(khenThuongItem => this.getKhenThuongIdentifier(khenThuongItem)!);
      const khenThuongsToAdd = khenThuongs.filter(khenThuongItem => {
        const khenThuongIdentifier = this.getKhenThuongIdentifier(khenThuongItem);
        if (khenThuongCollectionIdentifiers.includes(khenThuongIdentifier)) {
          return false;
        }
        khenThuongCollectionIdentifiers.push(khenThuongIdentifier);
        return true;
      });
      return [...khenThuongsToAdd, ...khenThuongCollection];
    }
    return khenThuongCollection;
  }

  protected convertDateFromClient<T extends IKhenThuong | NewKhenThuong | PartialUpdateKhenThuong>(khenThuong: T): RestOf<T> {
    return {
      ...khenThuong,
      ngayQd: khenThuong.ngayQd?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restKhenThuong: RestKhenThuong): IKhenThuong {
    return {
      ...restKhenThuong,
      ngayQd: restKhenThuong.ngayQd ? dayjs(restKhenThuong.ngayQd) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestKhenThuong>): HttpResponse<IKhenThuong> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestKhenThuong[]>): HttpResponse<IKhenThuong[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
