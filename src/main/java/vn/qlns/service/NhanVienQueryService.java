package vn.qlns.service;

import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;
import vn.qlns.domain.*; // for static metamodels
import vn.qlns.domain.NhanVien;
import vn.qlns.repository.NhanVienRepository;
import vn.qlns.service.criteria.NhanVienCriteria;

/**
 * Service for executing complex queries for {@link NhanVien} entities in the database.
 * The main input is a {@link NhanVienCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NhanVien} or a {@link Page} of {@link NhanVien} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NhanVienQueryService extends QueryService<NhanVien> {

    private final Logger log = LoggerFactory.getLogger(NhanVienQueryService.class);

    private final NhanVienRepository nhanVienRepository;

    public NhanVienQueryService(NhanVienRepository nhanVienRepository) {
        this.nhanVienRepository = nhanVienRepository;
    }

    /**
     * Return a {@link List} of {@link NhanVien} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NhanVien> findByCriteria(NhanVienCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NhanVien> specification = createSpecification(criteria);
        return nhanVienRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link NhanVien} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NhanVien> findByCriteria(NhanVienCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NhanVien> specification = createSpecification(criteria);
        return nhanVienRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NhanVienCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NhanVien> specification = createSpecification(criteria);
        return nhanVienRepository.count(specification);
    }

    /**
     * Function to convert {@link NhanVienCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NhanVien> createSpecification(NhanVienCriteria criteria) {
        Specification<NhanVien> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NhanVien_.id));
            }
            if (criteria.getMaNV() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMaNV(), NhanVien_.maNV));
            }
            if (criteria.getTenNV() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenNV(), NhanVien_.tenNV));
            }
            if (criteria.getNgaySinh() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNgaySinh(), NhanVien_.ngaySinh));
            }
            if (criteria.getGioiTinh() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGioiTinh(), NhanVien_.gioiTinh));
            }
            if (criteria.getDiaChi() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDiaChi(), NhanVien_.diaChi));
            }
            if (criteria.getSoCMND() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSoCMND(), NhanVien_.soCMND));
            }
            if (criteria.getsDT() != null) {
                specification = specification.and(buildStringSpecification(criteria.getsDT(), NhanVien_.sDT));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), NhanVien_.email));
            }
            if (criteria.getHeSoLuong() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHeSoLuong(), NhanVien_.heSoLuong));
            }
            if (criteria.getNguoiThanId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNguoiThanId(),
                            root -> root.join(NhanVien_.nguoiThan, JoinType.LEFT).get(NguoiThan_.id)
                        )
                    );
            }
            if (criteria.getLuongId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getLuongId(), root -> root.join(NhanVien_.luongs, JoinType.LEFT).get(Luong_.id))
                    );
            }
            if (criteria.getKhenThuongId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getKhenThuongId(),
                            root -> root.join(NhanVien_.khenThuongs, JoinType.LEFT).get(KhenThuong_.id)
                        )
                    );
            }
            if (criteria.getChucVuId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getChucVuId(), root -> root.join(NhanVien_.chucVu, JoinType.LEFT).get(ChucVu_.id))
                    );
            }
            if (criteria.getPhongBanId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getPhongBanId(), root -> root.join(NhanVien_.phongBan, JoinType.LEFT).get(PhongBan_.id))
                    );
            }
            if (criteria.getChuyenMonId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getChuyenMonId(),
                            root -> root.join(NhanVien_.chuyenMon, JoinType.LEFT).get(ChuyenMon_.id)
                        )
                    );
            }
            if (criteria.getTrinhDoHVId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTrinhDoHVId(),
                            root -> root.join(NhanVien_.trinhDoHV, JoinType.LEFT).get(TrinhDoHV_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
