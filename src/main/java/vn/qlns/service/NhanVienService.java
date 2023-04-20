package vn.qlns.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.qlns.domain.NhanVien;
import vn.qlns.repository.NhanVienRepository;

/**
 * Service Implementation for managing {@link NhanVien}.
 */
@Service
@Transactional
public class NhanVienService {

    private final Logger log = LoggerFactory.getLogger(NhanVienService.class);

    private final NhanVienRepository nhanVienRepository;

    public NhanVienService(NhanVienRepository nhanVienRepository) {
        this.nhanVienRepository = nhanVienRepository;
    }

    /**
     * Save a nhanVien.
     *
     * @param nhanVien the entity to save.
     * @return the persisted entity.
     */
    public NhanVien save(NhanVien nhanVien) {
        log.debug("Request to save NhanVien : {}", nhanVien);
        return nhanVienRepository.save(nhanVien);
    }

    /**
     * Update a nhanVien.
     *
     * @param nhanVien the entity to save.
     * @return the persisted entity.
     */
    public NhanVien update(NhanVien nhanVien) {
        log.debug("Request to update NhanVien : {}", nhanVien);
        return nhanVienRepository.save(nhanVien);
    }

    /**
     * Partially update a nhanVien.
     *
     * @param nhanVien the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NhanVien> partialUpdate(NhanVien nhanVien) {
        log.debug("Request to partially update NhanVien : {}", nhanVien);

        return nhanVienRepository
            .findById(nhanVien.getId())
            .map(existingNhanVien -> {
                if (nhanVien.getMaNV() != null) {
                    existingNhanVien.setMaNV(nhanVien.getMaNV());
                }
                if (nhanVien.getTenNV() != null) {
                    existingNhanVien.setTenNV(nhanVien.getTenNV());
                }
                if (nhanVien.getNgaySinh() != null) {
                    existingNhanVien.setNgaySinh(nhanVien.getNgaySinh());
                }
                if (nhanVien.getGioiTinh() != null) {
                    existingNhanVien.setGioiTinh(nhanVien.getGioiTinh());
                }
                if (nhanVien.getDiaChi() != null) {
                    existingNhanVien.setDiaChi(nhanVien.getDiaChi());
                }
                if (nhanVien.getSoCMND() != null) {
                    existingNhanVien.setSoCMND(nhanVien.getSoCMND());
                }
                if (nhanVien.getsDT() != null) {
                    existingNhanVien.setsDT(nhanVien.getsDT());
                }
                if (nhanVien.getEmail() != null) {
                    existingNhanVien.setEmail(nhanVien.getEmail());
                }
                if (nhanVien.getHeSoLuong() != null) {
                    existingNhanVien.setHeSoLuong(nhanVien.getHeSoLuong());
                }

                return existingNhanVien;
            })
            .map(nhanVienRepository::save);
    }

    /**
     * Get all the nhanViens.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NhanVien> findAll(Pageable pageable) {
        log.debug("Request to get all NhanViens");
        return nhanVienRepository.findAll(pageable);
    }

    /**
     * Get one nhanVien by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NhanVien> findOne(Long id) {
        log.debug("Request to get NhanVien : {}", id);
        return nhanVienRepository.findById(id);
    }

    /**
     * Delete the nhanVien by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete NhanVien : {}", id);
        nhanVienRepository.deleteById(id);
    }
}
