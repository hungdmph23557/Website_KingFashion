package com.example.demo.service;

import com.example.demo.entity.ChiTietKichCo;
import com.example.demo.entity.KichCo;

import java.util.List;
import java.util.UUID;

public interface ChiTietKichCoService {

    List<ChiTietKichCo> getAllByIdCTSP(UUID ChiTietSanPhamId);

    void add(ChiTietKichCo chiTietKichCo);

    void delete(UUID id);

    List<ChiTietKichCo> getCTKCByChiTietSanPhamId(UUID chiTietSanPhamId);

    ChiTietKichCo getCTKCById(UUID id);

    ChiTietKichCo getByChiTietSanPhamIdAndKichCoId(UUID chiTietSanPhamId, UUID kichCoId);
}
