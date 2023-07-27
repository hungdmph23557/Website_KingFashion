package com.example.demo.repository;

import com.example.demo.entity.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.UUID;

@Repository
public interface HoaDonRepository  extends JpaRepository<HoaDon, UUID> {
    @Query("SELECT h from HoaDon  h " +
            " where (:maHoaDon is null  or  h.maHoaDon LIKE  lower(concat('%', :maHoaDon, '%') ) )" +
            "AND(:tenNguoiNhan is null  or  h.tenNguoiNhan LIKE  lower(concat('%', :tenNguoiNhan , '%') ) ) "+
            "AND (:trangThai is null OR h.trangThai = :trangThai)")
    Page<HoaDon> search(@Param("maHoaDon") String maHoaDon, @Param("ngayThanhToan") Date ngayThanhToan, @Param("tongTienSauKhiGiam") Double tongTienSauKhiGiam, @Param("trangThai") Boolean trangThai,
                        String tenNguoiNhan, @Param("ngayDuKienNhan") Date ngayDuKienNhan, @Param("ngayShip") Date ngayShip,
                        Pageable pageable);
}
