package vn.qlns.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.qlns.web.rest.TestUtil;

class TrinhDoHVTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrinhDoHV.class);
        TrinhDoHV trinhDoHV1 = new TrinhDoHV();
        trinhDoHV1.setId(1L);
        TrinhDoHV trinhDoHV2 = new TrinhDoHV();
        trinhDoHV2.setId(trinhDoHV1.getId());
        assertThat(trinhDoHV1).isEqualTo(trinhDoHV2);
        trinhDoHV2.setId(2L);
        assertThat(trinhDoHV1).isNotEqualTo(trinhDoHV2);
        trinhDoHV1.setId(null);
        assertThat(trinhDoHV1).isNotEqualTo(trinhDoHV2);
    }
}
