package vn.qlns.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import vn.qlns.domain.ChucVu;

/**
 * Spring Data JPA repository for the ChucVu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChucVuRepository extends JpaRepository<ChucVu, Long>, JpaSpecificationExecutor<ChucVu> {}
