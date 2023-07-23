package com.example.demo.service;

import com.example.demo.entity.VaiTro;

import java.util.List;

public interface VaiTroService {
    List<VaiTro> getAll();

    void add(VaiTro vaiTro);
}
