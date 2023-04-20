import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INguoiThan, NewNguoiThan } from '../nguoi-than.model';

export type PartialUpdateNguoiThan = Partial<INguoiThan> & Pick<INguoiThan, 'id'>;

export type EntityResponseType = HttpResponse<INguoiThan>;
export type EntityArrayResponseType = HttpResponse<INguoiThan[]>;

@Injectable({ providedIn: 'root' })
export class NguoiThanService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/nguoi-thans');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(nguoiThan: NewNguoiThan): Observable<EntityResponseType> {
    return this.http.post<INguoiThan>(this.resourceUrl, nguoiThan, { observe: 'response' });
  }

  update(nguoiThan: INguoiThan): Observable<EntityResponseType> {
    return this.http.put<INguoiThan>(`${this.resourceUrl}/${this.getNguoiThanIdentifier(nguoiThan)}`, nguoiThan, { observe: 'response' });
  }

  partialUpdate(nguoiThan: PartialUpdateNguoiThan): Observable<EntityResponseType> {
    return this.http.patch<INguoiThan>(`${this.resourceUrl}/${this.getNguoiThanIdentifier(nguoiThan)}`, nguoiThan, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INguoiThan>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INguoiThan[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getNguoiThanIdentifier(nguoiThan: Pick<INguoiThan, 'id'>): number {
    return nguoiThan.id;
  }

  compareNguoiThan(o1: Pick<INguoiThan, 'id'> | null, o2: Pick<INguoiThan, 'id'> | null): boolean {
    return o1 && o2 ? this.getNguoiThanIdentifier(o1) === this.getNguoiThanIdentifier(o2) : o1 === o2;
  }

  addNguoiThanToCollectionIfMissing<Type extends Pick<INguoiThan, 'id'>>(
    nguoiThanCollection: Type[],
    ...nguoiThansToCheck: (Type | null | undefined)[]
  ): Type[] {
    const nguoiThans: Type[] = nguoiThansToCheck.filter(isPresent);
    if (nguoiThans.length > 0) {
      const nguoiThanCollectionIdentifiers = nguoiThanCollection.map(nguoiThanItem => this.getNguoiThanIdentifier(nguoiThanItem)!);
      const nguoiThansToAdd = nguoiThans.filter(nguoiThanItem => {
        const nguoiThanIdentifier = this.getNguoiThanIdentifier(nguoiThanItem);
        if (nguoiThanCollectionIdentifiers.includes(nguoiThanIdentifier)) {
          return false;
        }
        nguoiThanCollectionIdentifiers.push(nguoiThanIdentifier);
        return true;
      });
      return [...nguoiThansToAdd, ...nguoiThanCollection];
    }
    return nguoiThanCollection;
  }
}
