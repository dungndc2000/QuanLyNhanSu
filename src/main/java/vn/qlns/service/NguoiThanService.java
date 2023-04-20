package vn.qlns.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.qlns.domain.NguoiThan;
import vn.qlns.repository.NguoiThanRepository;

/**
 * Service Implementation for managing {@link NguoiThan}.
 */
@Service
@Transactional
public class NguoiThanService {

    private final Logger log = LoggerFactory.getLogger(NguoiThanService.class);

    private final NguoiThanRepository nguoiThanRepository;

    public NguoiThanService(NguoiThanRepository nguoiThanRepository) {
        this.nguoiThanRepository = nguoiThanRepository;
    }

    /**
     * Save a nguoiThan.
     *
     * @param nguoiThan the entity to save.
     * @return the persisted entity.
     */
    public NguoiThan save(NguoiThan nguoiThan) {
        log.debug("Request to save NguoiThan : {}", nguoiThan);
        return nguoiThanRepository.save(nguoiThan);
    }

    /**
     * Update a nguoiThan.
     *
     * @param nguoiThan the entity to save.
     * @return the persisted entity.
     */
    public NguoiThan update(NguoiThan nguoiThan) {
        log.debug("Request to update NguoiThan : {}", nguoiThan);
        return nguoiThanRepository.save(nguoiThan);
    }

    /**
     * Partially update a nguoiThan.
     *
     * @param nguoiThan the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NguoiThan> partialUpdate(NguoiThan nguoiThan) {
        log.debug("Request to partially update NguoiThan : {}", nguoiThan);

        return nguoiThanRepository
            .findById(nguoiThan.getId())
            .map(existingNguoiThan -> {
                if (nguoiThan.getMaNT() != null) {
                    existingNguoiThan.setMaNT(nguoiThan.getMaNT());
                }
                if (nguoiThan.getTenNT() != null) {
                    existingNguoiThan.setTenNT(nguoiThan.getTenNT());
                }
                if (nguoiThan.getsDT() != null) {
                    existingNguoiThan.setsDT(nguoiThan.getsDT());
                }

                return existingNguoiThan;
            })
            .map(nguoiThanRepository::save);
    }

    /**
     * Get all the nguoiThans.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NguoiThan> findAll(Pageable pageable) {
        log.debug("Request to get all NguoiThans");
        return nguoiThanRepository.findAll(pageable);
    }

    /**
     *  Get all the nguoiThans where NhanVien is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<NguoiThan> findAllWhereNhanVienIsNull() {
        log.debug("Request to get all nguoiThans where NhanVien is null");
        return StreamSupport
            .stream(nguoiThanRepository.findAll().spliterator(), false)
            .filter(nguoiThan -> nguoiThan.getNhanVien() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one nguoiThan by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NguoiThan> findOne(Long id) {
        log.debug("Request to get NguoiThan : {}", id);
        return nguoiThanRepository.findById(id);
    }

    /**
     * Delete the nguoiThan by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete NguoiThan : {}", id);
        nguoiThanRepository.deleteById(id);
    }
}
