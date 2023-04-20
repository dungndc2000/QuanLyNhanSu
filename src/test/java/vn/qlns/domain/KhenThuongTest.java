package vn.qlns.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.qlns.web.rest.TestUtil;

class KhenThuongTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KhenThuong.class);
        KhenThuong khenThuong1 = new KhenThuong();
        khenThuong1.setId(1L);
        KhenThuong khenThuong2 = new KhenThuong();
        khenThuong2.setId(khenThuong1.getId());
        assertThat(khenThuong1).isEqualTo(khenThuong2);
        khenThuong2.setId(2L);
        assertThat(khenThuong1).isNotEqualTo(khenThuong2);
        khenThuong1.setId(null);
        assertThat(khenThuong1).isNotEqualTo(khenThuong2);
    }
}
