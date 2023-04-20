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
import vn.qlns.domain.KhenThuong;
import vn.qlns.repository.KhenThuongRepository;
import vn.qlns.service.criteria.KhenThuongCriteria;

/**
 * Service for executing complex queries for {@link KhenThuong} entities in the database.
 * The main input is a {@link KhenThuongCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link KhenThuong} or a {@link Page} of {@link KhenThuong} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class KhenThuongQueryService extends QueryService<KhenThuong> {

    private final Logger log = LoggerFactory.getLogger(KhenThuongQueryService.class);

    private final KhenThuongRepository khenThuongRepository;

    public KhenThuongQueryService(KhenThuongRepository khenThuongRepository) {
        this.khenThuongRepository = khenThuongRepository;
    }

    /**
     * Return a {@link List} of {@link KhenThuong} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<KhenThuong> findByCriteria(KhenThuongCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<KhenThuong> specification = createSpecification(criteria);
        return khenThuongRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link KhenThuong} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<KhenThuong> findByCriteria(KhenThuongCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<KhenThuong> specification = createSpecification(criteria);
        return khenThuongRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(KhenThuongCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<KhenThuong> specification = createSpecification(criteria);
        return khenThuongRepository.count(specification);
    }

    /**
     * Function to convert {@link KhenThuongCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<KhenThuong> createSpecification(KhenThuongCriteria criteria) {
        Specification<KhenThuong> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), KhenThuong_.id));
            }
            if (criteria.getSoqd() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSoqd(), KhenThuong_.soqd));
            }
            if (criteria.getNgayQd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNgayQd(), KhenThuong_.ngayQd));
            }
            if (criteria.getTen() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTen(), KhenThuong_.ten));
            }
            if (criteria.getLoai() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLoai(), KhenThuong_.loai));
            }
            if (criteria.getHinhThuc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHinhThuc(), KhenThuong_.hinhThuc));
            }
            if (criteria.getSoTien() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSoTien(), KhenThuong_.soTien));
            }
            if (criteria.getNoiDung() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNoiDung(), KhenThuong_.noiDung));
            }
            if (criteria.getNhanVienId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNhanVienId(),
                            root -> root.join(KhenThuong_.nhanVien, JoinType.LEFT).get(NhanVien_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
