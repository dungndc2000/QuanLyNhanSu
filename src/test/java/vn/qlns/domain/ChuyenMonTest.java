package vn.qlns.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.qlns.web.rest.TestUtil;

class ChuyenMonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChuyenMon.class);
        ChuyenMon chuyenMon1 = new ChuyenMon();
        chuyenMon1.setId(1L);
        ChuyenMon chuyenMon2 = new ChuyenMon();
        chuyenMon2.setId(chuyenMon1.getId());
        assertThat(chuyenMon1).isEqualTo(chuyenMon2);
        chuyenMon2.setId(2L);
        assertThat(chuyenMon1).isNotEqualTo(chuyenMon2);
        chuyenMon1.setId(null);
        assertThat(chuyenMon1).isNotEqualTo(chuyenMon2);
    }
}
