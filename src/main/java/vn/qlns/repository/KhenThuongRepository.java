package vn.qlns.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import vn.qlns.domain.KhenThuong;

/**
 * Spring Data JPA repository for the KhenThuong entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KhenThuongRepository extends JpaRepository<KhenThuong, Long>, JpaSpecificationExecutor<KhenThuong> {}
