package vn.qlns.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import vn.qlns.domain.NguoiThan;

/**
 * Spring Data JPA repository for the NguoiThan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NguoiThanRepository extends JpaRepository<NguoiThan, Long>, JpaSpecificationExecutor<NguoiThan> {}
