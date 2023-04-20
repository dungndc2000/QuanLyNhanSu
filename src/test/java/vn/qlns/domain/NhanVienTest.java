package vn.qlns.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.qlns.web.rest.TestUtil;

class NhanVienTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NhanVien.class);
        NhanVien nhanVien1 = new NhanVien();
        nhanVien1.setId(1L);
        NhanVien nhanVien2 = new NhanVien();
        nhanVien2.setId(nhanVien1.getId());
        assertThat(nhanVien1).isEqualTo(nhanVien2);
        nhanVien2.setId(2L);
        assertThat(nhanVien1).isNotEqualTo(nhanVien2);
        nhanVien1.setId(null);
        assertThat(nhanVien1).isNotEqualTo(nhanVien2);
    }
}
