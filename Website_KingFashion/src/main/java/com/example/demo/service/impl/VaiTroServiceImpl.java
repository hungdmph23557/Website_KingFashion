package com.example.demo.service.impl;

import com.example.demo.entity.VaiTro;
import com.example.demo.repository.VaiTroRepository;
import com.example.demo.service.VaiTroService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class VaiTroServiceImpl implements VaiTroService {

    @Autowired
    private VaiTroRepository vaiTroRepository;

    @Override
    public List<VaiTro> getAll() {
        return vaiTroRepository.findAll();
    }

    @Override
    public void add(VaiTro vaiTro) {
        vaiTroRepository.save(vaiTro);
    }
}
