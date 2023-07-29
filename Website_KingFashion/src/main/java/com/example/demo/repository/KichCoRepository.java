package com.example.demo.repository;

import com.example.demo.entity.KichCo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface KichCoRepository extends JpaRepository<KichCo, UUID> {
        List<KichCo> findAllByChiTietSanPhamId(UUID chiTietSanPhamId);

        KichCo getKichCoById(UUID id);
}
