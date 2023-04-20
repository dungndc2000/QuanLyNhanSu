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
import vn.qlns.domain.PhongBan;
import vn.qlns.repository.PhongBanRepository;
import vn.qlns.service.criteria.PhongBanCriteria;

/**
 * Service for executing complex queries for {@link PhongBan} entities in the database.
 * The main input is a {@link PhongBanCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PhongBan} or a {@link Page} of {@link PhongBan} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PhongBanQueryService extends QueryService<PhongBan> {

    private final Logger log = LoggerFactory.getLogger(PhongBanQueryService.class);

    private final PhongBanRepository phongBanRepository;

    public PhongBanQueryService(PhongBanRepository phongBanRepository) {
        this.phongBanRepository = phongBanRepository;
    }

    /**
     * Return a {@link List} of {@link PhongBan} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PhongBan> findByCriteria(PhongBanCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PhongBan> specification = createSpecification(criteria);
        return phongBanRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PhongBan} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PhongBan> findByCriteria(PhongBanCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PhongBan> specification = createSpecification(criteria);
        return phongBanRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PhongBanCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PhongBan> specification = createSpecification(criteria);
        return phongBanRepository.count(specification);
    }

    /**
     * Function to convert {@link PhongBanCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PhongBan> createSpecification(PhongBanCriteria criteria) {
        Specification<PhongBan> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PhongBan_.id));
            }
            if (criteria.getMaPB() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMaPB(), PhongBan_.maPB));
            }
            if (criteria.getTenPB() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenPB(), PhongBan_.tenPB));
            }
            if (criteria.getsDT() != null) {
                specification = specification.and(buildStringSpecification(criteria.getsDT(), PhongBan_.sDT));
            }
            if (criteria.getNhanVienId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNhanVienId(),
                            root -> root.join(PhongBan_.nhanViens, JoinType.LEFT).get(NhanVien_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
