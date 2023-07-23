package com.example.demo.controller;

import com.example.demo.entity.SanPham;
import com.example.demo.service.SanPhamService;
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
import java.util.UUID;

@Controller
@RequestMapping("/san-pham/")
public class SanPhamController {
    @Autowired
    private SanPhamService sanPhamService;

    @GetMapping("hien-thi")
    public String hienThi(@RequestParam(defaultValue = "0", name = "page") Integer pageNum, Model model) {
        Page<SanPham> page = sanPhamService.phanTrang(pageNum, 5);
        model.addAttribute("list", page);
        model.addAttribute("att", new SanPham());
        return "sanpham/san-pham";
    }

    @PostMapping("add")
    public String add(@Valid @ModelAttribute("att") SanPham sanPham, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "sanpham/san-pham";
        }
        sanPham.setNgayTao(new Date());
        model.addAttribute("att", sanPham);
        sanPhamService.add(sanPham);
        return "redirect:/san-pham/hien-thi";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable UUID id, Model model) {
        sanPhamService.delete(id);
        return "redirect:/san-pham/hien-thi";
    }

    @GetMapping("view-update/{id}")
    public String updateMauSac(@PathVariable UUID id, Model model) {
        SanPham sanPham = sanPhamService.detail(id);
        model.addAttribute("att", sanPham);
        return "sanpham/update-san-pham";
    }

    @PostMapping("update")
    public String updateMauSac(@Valid @ModelAttribute("lsp1") SanPham sanPham, BindingResult result, Model model, @RequestParam("ngayTao") String ngayTao) {
        if (result.hasErrors()) {
            return "sanpham/update-san-pham";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date ngayTaoDate;
        try {
            ngayTaoDate = dateFormat.parse(ngayTao);
        } catch (ParseException e) {
            e.printStackTrace();
            return "redirect:/error";
        }

        sanPham.setNgayTao(ngayTaoDate);
        sanPham.setNgaySua(new Date());
        model.addAttribute("att", sanPham);
        sanPhamService.add(sanPham);
        return "redirect:/san-pham/hien-thi";
    }
}
