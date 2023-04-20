import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, filter, Observable, switchMap, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { INhanVien } from '../nhan-vien.model';

import { ITEMS_PER_PAGE, PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { ASC, DESC, SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { EntityArrayResponseType, NhanVienService } from '../service/nhan-vien.service';
import { NhanVienDeleteDialogComponent } from '../delete/nhan-vien-delete-dialog.component';
import { FilterOptions, IFilterOptions, IFilterOption } from 'app/shared/filter/filter.model';

@Component({
  selector: 'jhi-nhan-vien',
  templateUrl: './nhan-vien.component.html',
})
export class NhanVienComponent implements OnInit {
  nhanViens?: INhanVien[];
  isLoading = false;
  ngbPaginationPage = 1;
  selected!: '';
  name = '';
  predicate = 'id';
  ascending = true;
  filters: IFilterOptions = new FilterOptions();

  itemsPerPage = ITEMS_PER_PAGE;
  totalItems = 0;
  page = 1;

  constructor(
    protected nhanVienService: NhanVienService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected modalService: NgbModal
  ) {}

  trackId = (_index: number, item: INhanVien): number => this.nhanVienService.getNhanVienIdentifier(item);

  ngOnInit(): void {
    this.load();
    this.handleNavigation();
    // this.loadPageDeMaNV();
    this.filters.filterChanges.subscribe(filterOptions => this.handleNavigation(1, this.predicate, this.ascending, filterOptions));
  }

  delete(nhanVien: INhanVien): void {
    const modalRef = this.modalService.open(NhanVienDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.nhanVien = nhanVien;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_DELETED_EVENT),
        switchMap(() => this.loadFromBackendWithRouteInformations())
      )
      .subscribe({
        next: (res: EntityArrayResponseType) => {
          this.onResponseSuccess(res);
        },
      });
  }

  load(): void {
    this.loadFromBackendWithRouteInformations().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  navigateToWithComponentValues(): void {
    this.handleNavigation(this.page, this.predicate, this.ascending, this.filters.filterOptions);
  }

  navigateToPage(page = this.page): void {
    this.handleNavigation(page, this.predicate, this.ascending, this.filters.filterOptions);
  }

  protected loadFromBackendWithRouteInformations(): Observable<EntityArrayResponseType> {
    return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
      tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
      switchMap(() => this.queryBackend(this.page, this.predicate, this.ascending, this.filters.filterOptions))
    );
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const page = params.get(PAGE_HEADER);
    this.page = +(page ?? 1);
    const sort = (params.get(SORT) ?? data[DEFAULT_SORT_DATA]).split(',');
    this.predicate = sort[0];
    this.ascending = sort[1] === ASC;
    this.filters.initializeFromParams(params);
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    this.fillComponentAttributesFromResponseHeader(response.headers);
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.nhanViens = dataFromBody;
  }

  protected fillComponentAttributesFromResponseBody(data: INhanVien[] | null): INhanVien[] {
    return data ?? [];
  }

  protected fillComponentAttributesFromResponseHeader(headers: HttpHeaders): void {
    this.totalItems = Number(headers.get(TOTAL_COUNT_RESPONSE_HEADER));
  }

  protected queryBackend(
    page?: number,
    predicate?: string,
    ascending?: boolean,
    filterOptions?: IFilterOption[]
  ): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const pageToLoad: number = page ?? 1;
    const queryObject: any = {
      page: pageToLoad - 1,
      size: this.itemsPerPage,
      sort: this.getSortQueryParam(predicate, ascending),
    };
    filterOptions?.forEach(filterOption => {
      queryObject[filterOption.name] = filterOption.values;
    });
    return this.nhanVienService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected handleNavigation(page = this.page, predicate?: string, ascending?: boolean, filterOptions?: IFilterOption[]): void {
    const queryParamsObj: any = {
      page,
      size: this.itemsPerPage,
      sort: this.getSortQueryParam(predicate, ascending),
    };

    filterOptions?.forEach(filterOption => {
      queryParamsObj[filterOption.nameAsQueryParam()] = filterOption.values;
    });

    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute,
      queryParams: queryParamsObj,
    });
  }

  protected getSortQueryParam(predicate = this.predicate, ascending = this.ascending): string[] {
    const ascendingQueryParam = ascending ? ASC : DESC;
    if (predicate === '') {
      return [];
    } else {
      return [predicate + ',' + ascendingQueryParam];
    }
  }

  public clickEnterMaNV(event: any): void {
    this.name = event.target.value;
    this.loadPageDeMaNV();
  }

  public clickEnterADD(event: any): void {
    this.name = event.target.value;
    this.loadPageAdd();
  }

  public clickEnterTenNV(event: any): void {
    this.name = event.target.value;
    this.loadPageTenNV();
  }
  protected onSuccess2(data: INhanVien[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/nhanvien'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? ASC : DESC),
        },
      });
    }
    this.name ? (this.nhanViens = data?.filter(item => item.maNV === this.name)) : (this.nhanViens = data ?? []);
    this.ngbPaginationPage = this.page;
  }

  protected onSuccess(data: INhanVien[] | null, headers: HttpHeaders, page: number, navigate?: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/nhanvien'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? ASC : DESC),
        },
      });
    }
    // this.name ? (this.nhanViens = data?.filter(item => item.maNV === this.name)) : (this.nhanViens = data ?? [])
    // this.nhanViens = data?.filter(item => item.maNV  === this.name);
    // this.nhanViens = data ?? [];
    this.nhanViens = data ? data : [];
    this.ngbPaginationPage = this.page;
  }

  loadPageDeMaNV(page?: number, dontNavigate?: boolean): void {
    this.isLoading = true;
    const pageToLoad: number = page ?? this.page ?? 1;

    this.nhanVienService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
        'maNV.contains': this.name,
      })
      .subscribe({
        next: (res: HttpResponse<INhanVien[]>) => {
          this.isLoading = false;
          this.onSuccess(res.body, res.headers, pageToLoad, dontNavigate);
        },
        error: () => {
          this.isLoading = false;
          this.onError();
        },
      });
  }
  loadPageAdd(page?: number, dontNavigate?: boolean): void {
    this.isLoading = true;
    const pageToLoad: number = page ?? this.page ?? 1;

    this.nhanVienService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
        'diaChi.contains': this.name,
      })
      .subscribe({
        next: (res: HttpResponse<INhanVien[]>) => {
          this.isLoading = false;
          this.onSuccess(res.body, res.headers, pageToLoad, dontNavigate);
        },
        error: () => {
          this.isLoading = false;
          this.onError();
        },
      });
  }
  loadPage(page?: number, dontNavigate?: boolean): void {
    this.isLoading = true;
    const pageToLoad: number = page ?? this.page ?? 1;

    this.nhanVienService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe({
        next: (res: HttpResponse<INhanVien[]>) => {
          this.isLoading = false;
          this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate);
        },
        error: () => {
          this.isLoading = false;
          this.onError();
        },
      });
  }

  loadPageTenNV(page?: number, dontNavigate?: boolean): void {
    this.isLoading = true;
    const pageToLoad: number = page ?? this.page ?? 1;

    this.nhanVienService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
        'tenNV.contains': this.name,
      })
      .subscribe({
        next: (res: HttpResponse<INhanVien[]>) => {
          this.isLoading = false;
          this.onSuccess(res.body, res.headers, pageToLoad, dontNavigate);
        },
        error: () => {
          this.isLoading = false;
          this.onError();
        },
      });
  }
  loadPageRF(page?: number, dontNavigate?: boolean): void {
    this.isLoading = true;
    const pageToLoad: number = page ?? this.page ?? 1;

    this.nhanVienService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe({
        next: (res: HttpResponse<INhanVien[]>) => {
          this.isLoading = false;
          this.onSuccess(res.body, res.headers, pageToLoad, dontNavigate);
        },
        error: () => {
          this.isLoading = false;
          this.onError();
        },
      });
    window.location.reload();
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }

  protected sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? ASC : DESC)];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }
}
