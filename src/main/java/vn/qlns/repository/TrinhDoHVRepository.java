package vn.qlns.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import vn.qlns.domain.TrinhDoHV;

/**
 * Spring Data JPA repository for the TrinhDoHV entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrinhDoHVRepository extends JpaRepository<TrinhDoHV, Long>, JpaSpecificationExecutor<TrinhDoHV> {}
