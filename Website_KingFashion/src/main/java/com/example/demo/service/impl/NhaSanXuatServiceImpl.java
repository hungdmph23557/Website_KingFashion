package com.example.demo.service.impl;

import com.example.demo.entity.NhaSanXuat;
import com.example.demo.repository.NhaSanXuatRepository;
import com.example.demo.service.NhaSanXuatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NhaSanXuatServiceImpl implements NhaSanXuatService {

    @Autowired
    private NhaSanXuatRepository nhaSanXuatRepository;

    @Override
    public List<NhaSanXuat> getAll() {
        return nhaSanXuatRepository.findAll();
    }

    @Override
    public void add(NhaSanXuat nhaSanXuat) {
        nhaSanXuatRepository.save(nhaSanXuat);
    }

    @Override
    public NhaSanXuat detail(UUID id) {
        return nhaSanXuatRepository.getNhaSanXuatById(id);
    }

    @Override
    public void delete(UUID id) {
            nhaSanXuatRepository.deleteById(id);
    }

    @Override
    public Page<NhaSanXuat> phanTrangNhaSanXuat(Integer pageNum, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNum,pageNo);
        return  nhaSanXuatRepository.findAll(pageable);
    }
}
