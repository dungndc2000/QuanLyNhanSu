package vn.qlns.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.qlns.domain.ChuyenMon;
import vn.qlns.repository.ChuyenMonRepository;

/**
 * Service Implementation for managing {@link ChuyenMon}.
 */
@Service
@Transactional
public class ChuyenMonService {

    private final Logger log = LoggerFactory.getLogger(ChuyenMonService.class);

    private final ChuyenMonRepository chuyenMonRepository;

    public ChuyenMonService(ChuyenMonRepository chuyenMonRepository) {
        this.chuyenMonRepository = chuyenMonRepository;
    }

    /**
     * Save a chuyenMon.
     *
     * @param chuyenMon the entity to save.
     * @return the persisted entity.
     */
    public ChuyenMon save(ChuyenMon chuyenMon) {
        log.debug("Request to save ChuyenMon : {}", chuyenMon);
        return chuyenMonRepository.save(chuyenMon);
    }

    /**
     * Update a chuyenMon.
     *
     * @param chuyenMon the entity to save.
     * @return the persisted entity.
     */
    public ChuyenMon update(ChuyenMon chuyenMon) {
        log.debug("Request to update ChuyenMon : {}", chuyenMon);
        return chuyenMonRepository.save(chuyenMon);
    }

    /**
     * Partially update a chuyenMon.
     *
     * @param chuyenMon the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ChuyenMon> partialUpdate(ChuyenMon chuyenMon) {
        log.debug("Request to partially update ChuyenMon : {}", chuyenMon);

        return chuyenMonRepository
            .findById(chuyenMon.getId())
            .map(existingChuyenMon -> {
                if (chuyenMon.getMaChuyenMon() != null) {
                    existingChuyenMon.setMaChuyenMon(chuyenMon.getMaChuyenMon());
                }
                if (chuyenMon.getTenChuyenMon() != null) {
                    existingChuyenMon.setTenChuyenMon(chuyenMon.getTenChuyenMon());
                }

                return existingChuyenMon;
            })
            .map(chuyenMonRepository::save);
    }

    /**
     * Get all the chuyenMons.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ChuyenMon> findAll(Pageable pageable) {
        log.debug("Request to get all ChuyenMons");
        return chuyenMonRepository.findAll(pageable);
    }

    /**
     * Get one chuyenMon by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ChuyenMon> findOne(Long id) {
        log.debug("Request to get ChuyenMon : {}", id);
        return chuyenMonRepository.findById(id);
    }

    /**
     * Delete the chuyenMon by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ChuyenMon : {}", id);
        chuyenMonRepository.deleteById(id);
    }
}
