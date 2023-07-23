package com.example.demo.repository;

import com.example.demo.entity.ChiTietSanPham;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChiTietSanPhamRepository extends JpaRepository<ChiTietSanPham, UUID> {
    ChiTietSanPham getChiTietSanPhamById(UUID id);
}
