package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@Table(name = "HoaDonTraHang")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HoaDonTraHang {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name ="id_hd", referencedColumnName = "id")
    private HoaDon hoaDon;


    @ManyToOne
    @JoinColumn(name = "id_tk", referencedColumnName = "id")
    private TaiKhoan taiKhoan;

    @Column(name = "ngay_tra")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngayTra;

    @Column(name = "tien_hoan")
    private String tienHoan;


    @Column(name = "ghi_chu")
    private String ghiChu;


    @Column(name = "ngay_tao")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngayTao;

    @Column(name = "ngay_sua")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngaySua;


    @Column(name = "trang_thai")
    private Boolean trangThai;

}
