package vn.qlns.repository;

import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.qlns.domain.NhanVien;

/**
 * Spring Data JPA repository for the NhanVien entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, Long>, JpaSpecificationExecutor<NhanVien> {
    @Query(value = "select * from nhan_vien a where lower(a.ma_nv) LIKE CONCAT('%' ,lower(:maNV), '%')", nativeQuery = true)
    List<NhanVien> getNhanViensByMaNV(@Param("maNV") String maNV);
}
