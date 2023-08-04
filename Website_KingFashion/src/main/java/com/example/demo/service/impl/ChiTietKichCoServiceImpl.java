package com.example.demo.service.impl;

import com.example.demo.entity.ChiTietKichCo;
import com.example.demo.repository.ChiTietKichCoRepository;
import com.example.demo.service.ChiTietKichCoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ChiTietKichCoServiceImpl implements ChiTietKichCoService {

    @Autowired
    private ChiTietKichCoRepository chiTietKichCoRepository;

    @Override
    public List<ChiTietKichCo> getAllByIdCTSP(UUID ChiTietSanPhamId) {
        return chiTietKichCoRepository.findAllByChiTietSanPhamId(ChiTietSanPhamId);
    }

    @Override
    public void add(ChiTietKichCo chiTietKichCo) {
        chiTietKichCoRepository.save(chiTietKichCo);
    }

    @Override
    public void delete(UUID id) {
        chiTietKichCoRepository.deleteById(id);
    }

    @Override
    public List<ChiTietKichCo> getCTKCByChiTietSanPhamId(UUID chiTietSanPhamId) {
        return chiTietKichCoRepository.findAllByChiTietSanPhamId(chiTietSanPhamId);
    }

    @Override
    public ChiTietKichCo getCTKCById(UUID id) {
        return chiTietKichCoRepository.findById(id).orElse(null);
    }

    @Override
    public ChiTietKichCo getByChiTietSanPhamIdAndKichCoId(UUID chiTietSanPhamId, UUID kichCoId) {
        return chiTietKichCoRepository.getByChiTietSanPhamIdAndKichCoId(chiTietSanPhamId, kichCoId);
    }

}
