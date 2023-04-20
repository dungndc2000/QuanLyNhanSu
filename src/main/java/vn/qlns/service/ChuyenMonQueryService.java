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
import vn.qlns.domain.ChuyenMon;
import vn.qlns.repository.ChuyenMonRepository;
import vn.qlns.service.criteria.ChuyenMonCriteria;

/**
 * Service for executing complex queries for {@link ChuyenMon} entities in the database.
 * The main input is a {@link ChuyenMonCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ChuyenMon} or a {@link Page} of {@link ChuyenMon} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ChuyenMonQueryService extends QueryService<ChuyenMon> {

    private final Logger log = LoggerFactory.getLogger(ChuyenMonQueryService.class);

    private final ChuyenMonRepository chuyenMonRepository;

    public ChuyenMonQueryService(ChuyenMonRepository chuyenMonRepository) {
        this.chuyenMonRepository = chuyenMonRepository;
    }

    /**
     * Return a {@link List} of {@link ChuyenMon} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ChuyenMon> findByCriteria(ChuyenMonCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ChuyenMon> specification = createSpecification(criteria);
        return chuyenMonRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ChuyenMon} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ChuyenMon> findByCriteria(ChuyenMonCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ChuyenMon> specification = createSpecification(criteria);
        return chuyenMonRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ChuyenMonCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ChuyenMon> specification = createSpecification(criteria);
        return chuyenMonRepository.count(specification);
    }

    /**
     * Function to convert {@link ChuyenMonCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ChuyenMon> createSpecification(ChuyenMonCriteria criteria) {
        Specification<ChuyenMon> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ChuyenMon_.id));
            }
            if (criteria.getMaChuyenMon() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMaChuyenMon(), ChuyenMon_.maChuyenMon));
            }
            if (criteria.getTenChuyenMon() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenChuyenMon(), ChuyenMon_.tenChuyenMon));
            }
            if (criteria.getNhanVienId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNhanVienId(),
                            root -> root.join(ChuyenMon_.nhanViens, JoinType.LEFT).get(NhanVien_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
