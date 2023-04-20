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
import vn.qlns.domain.Luong;
import vn.qlns.repository.LuongRepository;
import vn.qlns.service.criteria.LuongCriteria;

/**
 * Service for executing complex queries for {@link Luong} entities in the database.
 * The main input is a {@link LuongCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Luong} or a {@link Page} of {@link Luong} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LuongQueryService extends QueryService<Luong> {

    private final Logger log = LoggerFactory.getLogger(LuongQueryService.class);

    private final LuongRepository luongRepository;

    public LuongQueryService(LuongRepository luongRepository) {
        this.luongRepository = luongRepository;
    }

    /**
     * Return a {@link List} of {@link Luong} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Luong> findByCriteria(LuongCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Luong> specification = createSpecification(criteria);
        return luongRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Luong} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Luong> findByCriteria(LuongCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Luong> specification = createSpecification(criteria);
        return luongRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LuongCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Luong> specification = createSpecification(criteria);
        return luongRepository.count(specification);
    }

    /**
     * Function to convert {@link LuongCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Luong> createSpecification(LuongCriteria criteria) {
        Specification<Luong> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Luong_.id));
            }
            if (criteria.getMaLuong() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMaLuong(), Luong_.maLuong));
            }
            if (criteria.getHeSoLuong() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHeSoLuong(), Luong_.heSoLuong));
            }
            if (criteria.getLuongCb() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLuongCb(), Luong_.luongCb));
            }
            if (criteria.getHeSoPhuCap() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHeSoPhuCap(), Luong_.heSoPhuCap));
            }
            if (criteria.getKhoanCongThem() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKhoanCongThem(), Luong_.khoanCongThem));
            }
            if (criteria.getKhoanTru() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKhoanTru(), Luong_.khoanTru));
            }
            if (criteria.getNhanVienId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getNhanVienId(), root -> root.join(Luong_.nhanVien, JoinType.LEFT).get(NhanVien_.id))
                    );
            }
        }
        return specification;
    }
}
