package vn.qlns.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.qlns.domain.Luong;
import vn.qlns.repository.LuongRepository;

/**
 * Service Implementation for managing {@link Luong}.
 */
@Service
@Transactional
public class LuongService {

    private final Logger log = LoggerFactory.getLogger(LuongService.class);

    private final LuongRepository luongRepository;

    public LuongService(LuongRepository luongRepository) {
        this.luongRepository = luongRepository;
    }

    /**
     * Save a luong.
     *
     * @param luong the entity to save.
     * @return the persisted entity.
     */
    public Luong save(Luong luong) {
        log.debug("Request to save Luong : {}", luong);
        return luongRepository.save(luong);
    }

    /**
     * Update a luong.
     *
     * @param luong the entity to save.
     * @return the persisted entity.
     */
    public Luong update(Luong luong) {
        log.debug("Request to update Luong : {}", luong);
        return luongRepository.save(luong);
    }

    /**
     * Partially update a luong.
     *
     * @param luong the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Luong> partialUpdate(Luong luong) {
        log.debug("Request to partially update Luong : {}", luong);

        return luongRepository
            .findById(luong.getId())
            .map(existingLuong -> {
                if (luong.getMaLuong() != null) {
                    existingLuong.setMaLuong(luong.getMaLuong());
                }
                if (luong.getHeSoLuong() != null) {
                    existingLuong.setHeSoLuong(luong.getHeSoLuong());
                }
                if (luong.getLuongCb() != null) {
                    existingLuong.setLuongCb(luong.getLuongCb());
                }
                if (luong.getHeSoPhuCap() != null) {
                    existingLuong.setHeSoPhuCap(luong.getHeSoPhuCap());
                }
                if (luong.getKhoanCongThem() != null) {
                    existingLuong.setKhoanCongThem(luong.getKhoanCongThem());
                }
                if (luong.getKhoanTru() != null) {
                    existingLuong.setKhoanTru(luong.getKhoanTru());
                }

                return existingLuong;
            })
            .map(luongRepository::save);
    }

    /**
     * Get all the luongs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Luong> findAll(Pageable pageable) {
        log.debug("Request to get all Luongs");
        return luongRepository.findAll(pageable);
    }

    /**
     * Get one luong by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Luong> findOne(Long id) {
        log.debug("Request to get Luong : {}", id);
        return luongRepository.findById(id);
    }

    /**
     * Delete the luong by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Luong : {}", id);
        luongRepository.deleteById(id);
    }
}
