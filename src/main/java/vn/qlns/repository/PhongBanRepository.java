package vn.qlns.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import vn.qlns.domain.PhongBan;

/**
 * Spring Data JPA repository for the PhongBan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhongBanRepository extends JpaRepository<PhongBan, Long>, JpaSpecificationExecutor<PhongBan> {}
