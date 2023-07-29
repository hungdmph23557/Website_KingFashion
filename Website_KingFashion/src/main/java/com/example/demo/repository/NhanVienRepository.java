package com.example.demo.repository;

import com.example.demo.entity.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.UUID;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, UUID> {
    NhanVien findNhanVienById(UUID id);
    @Query("SELECT v FROM NhanVien v " +
            "WHERE (:tenNhanVien is null or v.tenNhanVien LIKE lower(CONCAT('%', :tenNhanVien, '%')))\n"+
            "AND (:sdt is null or v.sdt LIKE lower(CONCAT('%', :sdt, '%')))\n"+
            "AND (:email is null or v.email LIKE lower(CONCAT('%', :email, '%')))"+
            "AND (:diaChi is null or v.diaChi LIKE lower(CONCAT('%', :diaChi, '%')))"+
            "AND (:ngaySinh IS NULL OR v.ngaySinh = :ngaySinh)")
    Page<NhanVien> search(@Param("tenNhanVien") String tenNhanVien, @Param("sdt") String sdt, @Param("email") String email,
                          @Param("diaChi") String diaChi, @Param("ngaySinh") Date ngaySinh, Pageable pageable);
}
