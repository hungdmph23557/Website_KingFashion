package com.example.demo.service;

import com.example.demo.entity.Anh;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface AnhService {

    List<Anh> viewAll();

    Anh viewById(UUID id);

    Anh viewAllById(UUID id);

    Page<Anh> phanTrang(Integer pageNum, Integer pageNo);

    void add(Anh anh, MultipartFile[] files) throws SQLException, IOException;

    Anh detail(UUID id);

    void delete(UUID id);

    List<Anh> getAllByChiTietSanPhamId(UUID chiTietSanPhamId);

    Anh getAnhById(UUID id);
}
