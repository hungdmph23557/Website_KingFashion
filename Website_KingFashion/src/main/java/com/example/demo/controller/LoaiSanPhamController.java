package com.example.demo.controller;

import com.example.demo.entity.LoaiSanPham;
import com.example.demo.service.LoaiSanPhamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Controller
@RequestMapping("/loai-san-pham/")
public class LoaiSanPhamController {
    @Autowired
    private LoaiSanPhamService loaiSanPhamService;

    @GetMapping("hien-thi")
    public String hienThi(@RequestParam(defaultValue = "0", name = "page") Integer pageNum, Model model) {
        Page<LoaiSanPham> page = loaiSanPhamService.phanTrang(pageNum, 5);
        model.addAttribute("list", page);
        model.addAttribute("lsp1", new LoaiSanPham());
        return "loaisanpham/loai-san-pham";
    }

    @PostMapping("add")
    public String add(@Valid @ModelAttribute("lsp1") LoaiSanPham loaiSanPham, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "loaisanpham/loai-san-pham";
        }
        String ma = "LSP" + new Random().nextInt(100000);
        loaiSanPham.setMa(ma);
        loaiSanPham.setTrangThai(1);
        loaiSanPham.setNgayTao(new Date());
        model.addAttribute("lsp1", loaiSanPham);
        loaiSanPhamService.add(loaiSanPham);
        return "redirect:/loai-san-pham/hien-thi";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable UUID id, Model model) {
        loaiSanPhamService.delete(id);
        return "redirect:/loai-san-pham/hien-thi";
    }

    @GetMapping("view-update/{id}")
    public String updateMauSac(@PathVariable UUID id, Model model) {
        LoaiSanPham loaiSanPham = loaiSanPhamService.detail(id);
        model.addAttribute("lsp1", loaiSanPham);
        return "loaisanpham/update-loai-san-pham";
    }

    @PostMapping("update")
    public String updateMauSac(@Valid @ModelAttribute("lsp1") LoaiSanPham loaiSanPham, BindingResult result, Model model, @RequestParam("ngayTao") String ngayTao) {
        if (result.hasErrors()) {
            return "loaisanpham/update-loai-san-pham";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date ngayTaoDate;
        try {
            ngayTaoDate = dateFormat.parse(ngayTao);
        } catch (ParseException e) {
            e.printStackTrace();
            return "redirect:/error";
        }

        loaiSanPham.setNgayTao(ngayTaoDate);
        loaiSanPham.setNgaySua(new Date());
        model.addAttribute("lsp1", loaiSanPham);
        loaiSanPhamService.add(loaiSanPham);
        return "redirect:/loai-san-pham/hien-thi";
    }
}
