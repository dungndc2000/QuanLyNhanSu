import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'nhan-vien',
        data: { pageTitle: 'quanlynhansuApp.nhanVien.home.title' },
        loadChildren: () => import('./nhan-vien/nhan-vien.module').then(m => m.NhanVienModule),
      },
      {
        path: 'luong',
        data: { pageTitle: 'quanlynhansuApp.luong.home.title' },
        loadChildren: () => import('./luong/luong.module').then(m => m.LuongModule),
      },
      {
        path: 'khen-thuong',
        data: { pageTitle: 'quanlynhansuApp.khenThuong.home.title' },
        loadChildren: () => import('./khen-thuong/khen-thuong.module').then(m => m.KhenThuongModule),
      },
      {
        path: 'chuc-vu',
        data: { pageTitle: 'quanlynhansuApp.chucVu.home.title' },
        loadChildren: () => import('./chuc-vu/chuc-vu.module').then(m => m.ChucVuModule),
      },
      {
        path: 'phong-ban',
        data: { pageTitle: 'quanlynhansuApp.phongBan.home.title' },
        loadChildren: () => import('./phong-ban/phong-ban.module').then(m => m.PhongBanModule),
      },
      {
        path: 'chuyen-mon',
        data: { pageTitle: 'quanlynhansuApp.chuyenMon.home.title' },
        loadChildren: () => import('./chuyen-mon/chuyen-mon.module').then(m => m.ChuyenMonModule),
      },
      {
        path: 'trinh-do-hv',
        data: { pageTitle: 'quanlynhansuApp.trinhDoHV.home.title' },
        loadChildren: () => import('./trinh-do-hv/trinh-do-hv.module').then(m => m.TrinhDoHVModule),
      },
      {
        path: 'nguoi-than',
        data: { pageTitle: 'quanlynhansuApp.nguoiThan.home.title' },
        loadChildren: () => import('./nguoi-than/nguoi-than.module').then(m => m.NguoiThanModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
