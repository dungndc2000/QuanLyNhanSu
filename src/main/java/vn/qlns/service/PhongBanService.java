package vn.qlns.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.qlns.domain.PhongBan;
import vn.qlns.repository.PhongBanRepository;

/**
 * Service Implementation for managing {@link PhongBan}.
 */
@Service
@Transactional
public class PhongBanService {

    private final Logger log = LoggerFactory.getLogger(PhongBanService.class);

    private final PhongBanRepository phongBanRepository;

    public PhongBanService(PhongBanRepository phongBanRepository) {
        this.phongBanRepository = phongBanRepository;
    }

    /**
     * Save a phongBan.
     *
     * @param phongBan the entity to save.
     * @return the persisted entity.
     */
    public PhongBan save(PhongBan phongBan) {
        log.debug("Request to save PhongBan : {}", phongBan);
        return phongBanRepository.save(phongBan);
    }

    /**
     * Update a phongBan.
     *
     * @param phongBan the entity to save.
     * @return the persisted entity.
     */
    public PhongBan update(PhongBan phongBan) {
        log.debug("Request to update PhongBan : {}", phongBan);
        return phongBanRepository.save(phongBan);
    }

    /**
     * Partially update a phongBan.
     *
     * @param phongBan the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PhongBan> partialUpdate(PhongBan phongBan) {
        log.debug("Request to partially update PhongBan : {}", phongBan);

        return phongBanRepository
            .findById(phongBan.getId())
            .map(existingPhongBan -> {
                if (phongBan.getMaPB() != null) {
                    existingPhongBan.setMaPB(phongBan.getMaPB());
                }
                if (phongBan.getTenPB() != null) {
                    existingPhongBan.setTenPB(phongBan.getTenPB());
                }
                if (phongBan.getsDT() != null) {
                    existingPhongBan.setsDT(phongBan.getsDT());
                }

                return existingPhongBan;
            })
            .map(phongBanRepository::save);
    }

    /**
     * Get all the phongBans.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PhongBan> findAll(Pageable pageable) {
        log.debug("Request to get all PhongBans");
        return phongBanRepository.findAll(pageable);
    }

    /**
     * Get one phongBan by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PhongBan> findOne(Long id) {
        log.debug("Request to get PhongBan : {}", id);
        return phongBanRepository.findById(id);
    }

    /**
     * Delete the phongBan by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PhongBan : {}", id);
        phongBanRepository.deleteById(id);
    }
}
