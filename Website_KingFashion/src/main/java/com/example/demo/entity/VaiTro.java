package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table(name = "VaiTro")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VaiTro {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "ma")
    private String maVaiTro;

    @Column(name = "ten")
    private String tenVaiTro;

    @Column(name = "trang_thai")
    private Integer trangThai;

}
