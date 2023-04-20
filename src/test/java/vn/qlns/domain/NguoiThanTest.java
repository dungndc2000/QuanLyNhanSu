package vn.qlns.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.qlns.web.rest.TestUtil;

class NguoiThanTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NguoiThan.class);
        NguoiThan nguoiThan1 = new NguoiThan();
        nguoiThan1.setId(1L);
        NguoiThan nguoiThan2 = new NguoiThan();
        nguoiThan2.setId(nguoiThan1.getId());
        assertThat(nguoiThan1).isEqualTo(nguoiThan2);
        nguoiThan2.setId(2L);
        assertThat(nguoiThan1).isNotEqualTo(nguoiThan2);
        nguoiThan1.setId(null);
        assertThat(nguoiThan1).isNotEqualTo(nguoiThan2);
    }
}
