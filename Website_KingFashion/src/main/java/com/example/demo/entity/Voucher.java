package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@Table(name = "KhuyenMai")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "ma")
//    @NotBlank(message = "Không được để trống")
    private String ma;

    @Column(name = "ten")
    @NotBlank(message = "Không được để trống")
    private String ten;

    @Column(name = "muc_giam")
    @NotBlank(message = "Không được để trống")
    @Min(value = 0, message = "Không được nhỏ hơn 0")
    private String mucGiam;

    @Column(name = "so_tien_toi_thieu_giam_gia")
    @NotNull(message = "Không được để trống")
    @Min(value = 0)
    private Double tien;

    @Column(name = "thoi_gian_bat_dau")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm")
    @NotNull(message = "Không được để trống")
    @Past(message = "Không được đi đến tương lai")
    private Date thoiGianBatDau;

    @Column(name = "thoi_gian_ket_thuc")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm")
    @NotNull(message = "Không được để trống")
    private Date thoiGianKetThuc;

    @Column(name = "mo_ta")
    @NotBlank(message = "Không được để trống")
    private String moTa;

    @Column(name = "trang_thai")
    @NotNull(message = "Không được để trống")
    private Integer trangThai;

    @Column(name = "ngay_tao")
    private String ngayTao;

    @Column(name = "ngay_sua")
    private String ngaySua;

    @Column(name = "nguoi_tao")
    private String nguoiTao;

    @Column(name = "nguoi_sua")
    private String nguoiSua;

}
