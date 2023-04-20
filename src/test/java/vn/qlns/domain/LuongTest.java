package vn.qlns.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.qlns.web.rest.TestUtil;

class LuongTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Luong.class);
        Luong luong1 = new Luong();
        luong1.setId(1L);
        Luong luong2 = new Luong();
        luong2.setId(luong1.getId());
        assertThat(luong1).isEqualTo(luong2);
        luong2.setId(2L);
        assertThat(luong1).isNotEqualTo(luong2);
        luong1.setId(null);
        assertThat(luong1).isNotEqualTo(luong2);
    }
}
