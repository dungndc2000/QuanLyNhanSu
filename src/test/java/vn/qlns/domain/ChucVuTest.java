package vn.qlns.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.qlns.web.rest.TestUtil;

class ChucVuTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChucVu.class);
        ChucVu chucVu1 = new ChucVu();
        chucVu1.setId(1L);
        ChucVu chucVu2 = new ChucVu();
        chucVu2.setId(chucVu1.getId());
        assertThat(chucVu1).isEqualTo(chucVu2);
        chucVu2.setId(2L);
        assertThat(chucVu1).isNotEqualTo(chucVu2);
        chucVu1.setId(null);
        assertThat(chucVu1).isNotEqualTo(chucVu2);
    }
}
