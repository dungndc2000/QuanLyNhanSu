package vn.qlns.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import vn.qlns.domain.Luong;

/**
 * Spring Data JPA repository for the Luong entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LuongRepository extends JpaRepository<Luong, Long>, JpaSpecificationExecutor<Luong> {}
