package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@Table(name = "TaiKhoan")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaiKhoan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_vt", referencedColumnName = "id")
    private VaiTro vaiTro;


    @Column(name = "ma")
    private String maTaiKhoan;

    @Column(name = "ten")
    private String tenTaiKhoan;


    @Column(name = "sdt")
    private String sdt;

    @Column(name = "email")
    private String email;

    @Column(name = "dia_chi")
    private String diaChi;


    @Column(name = "ngay_sinh")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngaySinh;

    @Column(name = "ngay_tao")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngayTao;

    @Column(name = "ngay_sua")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngaySua;


    @Column(name = "nguoi_tao")
    private String nguoiTao;

    @Column(name = "nguoi_sua")
    private String nguoiSua;

    @Column(name = "mat_khau")
    private String matKhau;


    @Column(name = "trang_thai")
    private Integer trangThai;


}
