package vn.qlns.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.qlns.domain.TrinhDoHV;
import vn.qlns.repository.TrinhDoHVRepository;

/**
 * Service Implementation for managing {@link TrinhDoHV}.
 */
@Service
@Transactional
public class TrinhDoHVService {

    private final Logger log = LoggerFactory.getLogger(TrinhDoHVService.class);

    private final TrinhDoHVRepository trinhDoHVRepository;

    public TrinhDoHVService(TrinhDoHVRepository trinhDoHVRepository) {
        this.trinhDoHVRepository = trinhDoHVRepository;
    }

    /**
     * Save a trinhDoHV.
     *
     * @param trinhDoHV the entity to save.
     * @return the persisted entity.
     */
    public TrinhDoHV save(TrinhDoHV trinhDoHV) {
        log.debug("Request to save TrinhDoHV : {}", trinhDoHV);
        return trinhDoHVRepository.save(trinhDoHV);
    }

    /**
     * Update a trinhDoHV.
     *
     * @param trinhDoHV the entity to save.
     * @return the persisted entity.
     */
    public TrinhDoHV update(TrinhDoHV trinhDoHV) {
        log.debug("Request to update TrinhDoHV : {}", trinhDoHV);
        return trinhDoHVRepository.save(trinhDoHV);
    }

    /**
     * Partially update a trinhDoHV.
     *
     * @param trinhDoHV the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TrinhDoHV> partialUpdate(TrinhDoHV trinhDoHV) {
        log.debug("Request to partially update TrinhDoHV : {}", trinhDoHV);

        return trinhDoHVRepository
            .findById(trinhDoHV.getId())
            .map(existingTrinhDoHV -> {
                if (trinhDoHV.getMaTDHV() != null) {
                    existingTrinhDoHV.setMaTDHV(trinhDoHV.getMaTDHV());
                }
                if (trinhDoHV.getTenTDHV() != null) {
                    existingTrinhDoHV.setTenTDHV(trinhDoHV.getTenTDHV());
                }

                return existingTrinhDoHV;
            })
            .map(trinhDoHVRepository::save);
    }

    /**
     * Get all the trinhDoHVS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TrinhDoHV> findAll(Pageable pageable) {
        log.debug("Request to get all TrinhDoHVS");
        return trinhDoHVRepository.findAll(pageable);
    }

    /**
     * Get one trinhDoHV by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TrinhDoHV> findOne(Long id) {
        log.debug("Request to get TrinhDoHV : {}", id);
        return trinhDoHVRepository.findById(id);
    }

    /**
     * Delete the trinhDoHV by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TrinhDoHV : {}", id);
        trinhDoHVRepository.deleteById(id);
    }
}
