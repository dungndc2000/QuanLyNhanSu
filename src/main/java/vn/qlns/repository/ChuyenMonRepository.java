package vn.qlns.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import vn.qlns.domain.ChuyenMon;

/**
 * Spring Data JPA repository for the ChuyenMon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChuyenMonRepository extends JpaRepository<ChuyenMon, Long>, JpaSpecificationExecutor<ChuyenMon> {}
