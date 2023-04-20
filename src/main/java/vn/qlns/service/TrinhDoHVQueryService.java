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
import vn.qlns.domain.TrinhDoHV;
import vn.qlns.repository.TrinhDoHVRepository;
import vn.qlns.service.criteria.TrinhDoHVCriteria;

/**
 * Service for executing complex queries for {@link TrinhDoHV} entities in the database.
 * The main input is a {@link TrinhDoHVCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TrinhDoHV} or a {@link Page} of {@link TrinhDoHV} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TrinhDoHVQueryService extends QueryService<TrinhDoHV> {

    private final Logger log = LoggerFactory.getLogger(TrinhDoHVQueryService.class);

    private final TrinhDoHVRepository trinhDoHVRepository;

    public TrinhDoHVQueryService(TrinhDoHVRepository trinhDoHVRepository) {
        this.trinhDoHVRepository = trinhDoHVRepository;
    }

    /**
     * Return a {@link List} of {@link TrinhDoHV} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TrinhDoHV> findByCriteria(TrinhDoHVCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TrinhDoHV> specification = createSpecification(criteria);
        return trinhDoHVRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TrinhDoHV} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TrinhDoHV> findByCriteria(TrinhDoHVCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TrinhDoHV> specification = createSpecification(criteria);
        return trinhDoHVRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TrinhDoHVCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TrinhDoHV> specification = createSpecification(criteria);
        return trinhDoHVRepository.count(specification);
    }

    /**
     * Function to convert {@link TrinhDoHVCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TrinhDoHV> createSpecification(TrinhDoHVCriteria criteria) {
        Specification<TrinhDoHV> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TrinhDoHV_.id));
            }
            if (criteria.getMaTDHV() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMaTDHV(), TrinhDoHV_.maTDHV));
            }
            if (criteria.getTenTDHV() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenTDHV(), TrinhDoHV_.tenTDHV));
            }
            if (criteria.getNhanVienId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNhanVienId(),
                            root -> root.join(TrinhDoHV_.nhanViens, JoinType.LEFT).get(NhanVien_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
