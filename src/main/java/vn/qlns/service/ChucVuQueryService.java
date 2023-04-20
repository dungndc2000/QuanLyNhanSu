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
import vn.qlns.domain.ChucVu;
import vn.qlns.repository.ChucVuRepository;
import vn.qlns.service.criteria.ChucVuCriteria;

/**
 * Service for executing complex queries for {@link ChucVu} entities in the database.
 * The main input is a {@link ChucVuCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ChucVu} or a {@link Page} of {@link ChucVu} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ChucVuQueryService extends QueryService<ChucVu> {

    private final Logger log = LoggerFactory.getLogger(ChucVuQueryService.class);

    private final ChucVuRepository chucVuRepository;

    public ChucVuQueryService(ChucVuRepository chucVuRepository) {
        this.chucVuRepository = chucVuRepository;
    }

    /**
     * Return a {@link List} of {@link ChucVu} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ChucVu> findByCriteria(ChucVuCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ChucVu> specification = createSpecification(criteria);
        return chucVuRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ChucVu} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ChucVu> findByCriteria(ChucVuCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ChucVu> specification = createSpecification(criteria);
        return chucVuRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ChucVuCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ChucVu> specification = createSpecification(criteria);
        return chucVuRepository.count(specification);
    }

    /**
     * Function to convert {@link ChucVuCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ChucVu> createSpecification(ChucVuCriteria criteria) {
        Specification<ChucVu> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ChucVu_.id));
            }
            if (criteria.getMaCV() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMaCV(), ChucVu_.maCV));
            }
            if (criteria.getTenChucVu() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenChucVu(), ChucVu_.tenChucVu));
            }
            if (criteria.getPhuCap() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhuCap(), ChucVu_.phuCap));
            }
            if (criteria.getGhiChu() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGhiChu(), ChucVu_.ghiChu));
            }
            if (criteria.getNhanVienId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getNhanVienId(), root -> root.join(ChucVu_.nhanViens, JoinType.LEFT).get(NhanVien_.id))
                    );
            }
        }
        return specification;
    }
}
