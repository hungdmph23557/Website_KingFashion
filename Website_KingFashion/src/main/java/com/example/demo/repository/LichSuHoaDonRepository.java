package com.example.demo.repository;

import com.example.demo.entity.LichSuHoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LichSuHoaDonRepository extends JpaRepository<LichSuHoaDon, UUID> {
    List<LichSuHoaDon> findLichSuHoaDonById(UUID id);
}
