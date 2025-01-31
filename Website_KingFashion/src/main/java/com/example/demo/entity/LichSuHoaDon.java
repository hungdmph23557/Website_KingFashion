package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@Table(name = "LichSuHoaDon")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LichSuHoaDon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "ma")
    private String maLichSuHoaDon;

    @Column(name = "ten")
    private String tenLichSuHoaDon;


    @Column(name = "nguoi_tao")
    private String nguoiTaoLichSuHoaDon;


    @Column(name = "ngay_tao")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngayTao;


}
