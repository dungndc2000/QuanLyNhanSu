package vn.qlns.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.qlns.domain.KhenThuong;
import vn.qlns.repository.KhenThuongRepository;

/**
 * Service Implementation for managing {@link KhenThuong}.
 */
@Service
@Transactional
public class KhenThuongService {

    private final Logger log = LoggerFactory.getLogger(KhenThuongService.class);

    private final KhenThuongRepository khenThuongRepository;

    public KhenThuongService(KhenThuongRepository khenThuongRepository) {
        this.khenThuongRepository = khenThuongRepository;
    }

    /**
     * Save a khenThuong.
     *
     * @param khenThuong the entity to save.
     * @return the persisted entity.
     */
    public KhenThuong save(KhenThuong khenThuong) {
        log.debug("Request to save KhenThuong : {}", khenThuong);
        return khenThuongRepository.save(khenThuong);
    }

    /**
     * Update a khenThuong.
     *
     * @param khenThuong the entity to save.
     * @return the persisted entity.
     */
    public KhenThuong update(KhenThuong khenThuong) {
        log.debug("Request to update KhenThuong : {}", khenThuong);
        return khenThuongRepository.save(khenThuong);
    }

    /**
     * Partially update a khenThuong.
     *
     * @param khenThuong the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<KhenThuong> partialUpdate(KhenThuong khenThuong) {
        log.debug("Request to partially update KhenThuong : {}", khenThuong);

        return khenThuongRepository
            .findById(khenThuong.getId())
            .map(existingKhenThuong -> {
                if (khenThuong.getSoqd() != null) {
                    existingKhenThuong.setSoqd(khenThuong.getSoqd());
                }
                if (khenThuong.getNgayQd() != null) {
                    existingKhenThuong.setNgayQd(khenThuong.getNgayQd());
                }
                if (khenThuong.getTen() != null) {
                    existingKhenThuong.setTen(khenThuong.getTen());
                }
                if (khenThuong.getLoai() != null) {
                    existingKhenThuong.setLoai(khenThuong.getLoai());
                }
                if (khenThuong.getHinhThuc() != null) {
                    existingKhenThuong.setHinhThuc(khenThuong.getHinhThuc());
                }
                if (khenThuong.getSoTien() != null) {
                    existingKhenThuong.setSoTien(khenThuong.getSoTien());
                }
                if (khenThuong.getNoiDung() != null) {
                    existingKhenThuong.setNoiDung(khenThuong.getNoiDung());
                }

                return existingKhenThuong;
            })
            .map(khenThuongRepository::save);
    }

    /**
     * Get all the khenThuongs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<KhenThuong> findAll(Pageable pageable) {
        log.debug("Request to get all KhenThuongs");
        return khenThuongRepository.findAll(pageable);
    }

    /**
     * Get one khenThuong by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<KhenThuong> findOne(Long id) {
        log.debug("Request to get KhenThuong : {}", id);
        return khenThuongRepository.findById(id);
    }

    /**
     * Delete the khenThuong by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete KhenThuong : {}", id);
        khenThuongRepository.deleteById(id);
    }
}
