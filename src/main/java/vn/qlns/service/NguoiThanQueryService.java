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
import vn.qlns.domain.NguoiThan;
import vn.qlns.repository.NguoiThanRepository;
import vn.qlns.service.criteria.NguoiThanCriteria;

/**
 * Service for executing complex queries for {@link NguoiThan} entities in the database.
 * The main input is a {@link NguoiThanCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NguoiThan} or a {@link Page} of {@link NguoiThan} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NguoiThanQueryService extends QueryService<NguoiThan> {

    private final Logger log = LoggerFactory.getLogger(NguoiThanQueryService.class);

    private final NguoiThanRepository nguoiThanRepository;

    public NguoiThanQueryService(NguoiThanRepository nguoiThanRepository) {
        this.nguoiThanRepository = nguoiThanRepository;
    }

    /**
     * Return a {@link List} of {@link NguoiThan} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NguoiThan> findByCriteria(NguoiThanCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NguoiThan> specification = createSpecification(criteria);
        return nguoiThanRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link NguoiThan} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NguoiThan> findByCriteria(NguoiThanCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NguoiThan> specification = createSpecification(criteria);
        return nguoiThanRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NguoiThanCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NguoiThan> specification = createSpecification(criteria);
        return nguoiThanRepository.count(specification);
    }

    /**
     * Function to convert {@link NguoiThanCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NguoiThan> createSpecification(NguoiThanCriteria criteria) {
        Specification<NguoiThan> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NguoiThan_.id));
            }
            if (criteria.getMaNT() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMaNT(), NguoiThan_.maNT));
            }
            if (criteria.getTenNT() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenNT(), NguoiThan_.tenNT));
            }
            if (criteria.getsDT() != null) {
                specification = specification.and(buildStringSpecification(criteria.getsDT(), NguoiThan_.sDT));
            }
            if (criteria.getNhanVienId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNhanVienId(),
                            root -> root.join(NguoiThan_.nhanVien, JoinType.LEFT).get(NhanVien_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
