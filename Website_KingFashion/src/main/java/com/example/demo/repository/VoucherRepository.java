package com.example.demo.repository;

import com.example.demo.entity.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, UUID> {
    @Query("SELECT v FROM Voucher v " +
            "WHERE (:ma is null OR v.ma = :ma)\n" +
            "  AND (:ten is null OR v.ten LIKE CONCAT('%', :ten, '%'))\n" +
            "  AND (:mucGiam is null OR v.mucGiam < :mucGiam)\n" +
            "  AND (:tien is null OR v.tien <= :tien)\n" +
            "  AND (:ngayBatDau is null OR v.thoiGianBatDau <= :ngayBatDau)\n" +
            "  AND (:ngayKetThuc is null OR v.thoiGianKetThuc >= :ngayKetThuc)\n" +
            "  AND (:trangThai is null OR v.trangThai = :trangThai)")
    Page<Voucher> search(String ma, String ten, String mucGiam, Double tien, Date ngayBatDau, Date ngayKetThuc, Integer trangThai, Pageable pageable);

    List<Voucher> findByThoiGianKetThucBeforeAndTrangThaiEquals(Date endDate, Integer status);
}
