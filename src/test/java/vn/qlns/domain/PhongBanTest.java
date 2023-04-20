package vn.qlns.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.qlns.web.rest.TestUtil;

class PhongBanTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhongBan.class);
        PhongBan phongBan1 = new PhongBan();
        phongBan1.setId(1L);
        PhongBan phongBan2 = new PhongBan();
        phongBan2.setId(phongBan1.getId());
        assertThat(phongBan1).isEqualTo(phongBan2);
        phongBan2.setId(2L);
        assertThat(phongBan1).isNotEqualTo(phongBan2);
        phongBan1.setId(null);
        assertThat(phongBan1).isNotEqualTo(phongBan2);
    }
}
