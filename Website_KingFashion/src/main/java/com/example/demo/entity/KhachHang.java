package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class KhachHang {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vt", referencedColumnName = "id")
    private VaiTro vaiTro;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "ma")
    @NotBlank(message = "Không được để trống")
    private String ma;

    @Column(name = "ten")
    @NotBlank(message = "Không được để trống")
    private String ten;

    @Column(name = "sdt")
    @NotNull(message = "Không được để trống")
    private String sdt;

    @Column(name = "email")
    @NotBlank(message = "Không được để trống")
    private String email;

    @Column(name = "dia_chi")
    @NotBlank(message = "Không được để trống")
    private String diaChi;

    @Column(name = "ngay_sinh")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Không được để trống")
    private Date ngaySinh;

    @Column(name = "mat_khau")
    @NotBlank(message = "Không được để trống")
    private String matKhau;

    @Column(name = "trang_thai")
    @NotNull(message = "Không được để trống")
    private Integer trangThai;

//    @ManyToOne
//    @JoinColumn(name = "id_vt", referencedColumnName = "id")
//    private

}
