package vn.qlns.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.qlns.domain.ChucVu;
import vn.qlns.repository.ChucVuRepository;

/**
 * Service Implementation for managing {@link ChucVu}.
 */
@Service
@Transactional
public class ChucVuService {

    private final Logger log = LoggerFactory.getLogger(ChucVuService.class);

    private final ChucVuRepository chucVuRepository;

    public ChucVuService(ChucVuRepository chucVuRepository) {
        this.chucVuRepository = chucVuRepository;
    }

    /**
     * Save a chucVu.
     *
     * @param chucVu the entity to save.
     * @return the persisted entity.
     */
    public ChucVu save(ChucVu chucVu) {
        log.debug("Request to save ChucVu : {}", chucVu);
        return chucVuRepository.save(chucVu);
    }

    /**
     * Update a chucVu.
     *
     * @param chucVu the entity to save.
     * @return the persisted entity.
     */
    public ChucVu update(ChucVu chucVu) {
        log.debug("Request to update ChucVu : {}", chucVu);
        return chucVuRepository.save(chucVu);
    }

    /**
     * Partially update a chucVu.
     *
     * @param chucVu the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ChucVu> partialUpdate(ChucVu chucVu) {
        log.debug("Request to partially update ChucVu : {}", chucVu);

        return chucVuRepository
            .findById(chucVu.getId())
            .map(existingChucVu -> {
                if (chucVu.getMaCV() != null) {
                    existingChucVu.setMaCV(chucVu.getMaCV());
                }
                if (chucVu.getTenChucVu() != null) {
                    existingChucVu.setTenChucVu(chucVu.getTenChucVu());
                }
                if (chucVu.getPhuCap() != null) {
                    existingChucVu.setPhuCap(chucVu.getPhuCap());
                }
                if (chucVu.getGhiChu() != null) {
                    existingChucVu.setGhiChu(chucVu.getGhiChu());
                }

                return existingChucVu;
            })
            .map(chucVuRepository::save);
    }

    /**
     * Get all the chucVus.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ChucVu> findAll(Pageable pageable) {
        log.debug("Request to get all ChucVus");
        return chucVuRepository.findAll(pageable);
    }

    /**
     * Get one chucVu by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ChucVu> findOne(Long id) {
        log.debug("Request to get ChucVu : {}", id);
        return chucVuRepository.findById(id);
    }

    /**
     * Delete the chucVu by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ChucVu : {}", id);
        chucVuRepository.deleteById(id);
    }
}
