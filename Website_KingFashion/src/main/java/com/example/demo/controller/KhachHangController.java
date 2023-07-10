package com.example.demo.controller;

import com.example.demo.entity.KhachHang;
import com.example.demo.entity.Voucher;
import com.example.demo.service.KhachHangService;
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

import java.util.UUID;

@Controller
@RequestMapping("/khach-hang")
public class KhachHangController {
    @Autowired
    private KhachHangService khachHangService;

    @GetMapping("/hien-thi")
    public String hienThi (Model model, @RequestParam(name = "page",defaultValue = "0") Integer p){
        Page<KhachHang> page = khachHangService.page(p,5);
        model.addAttribute("list", page);
        return "khach-hang/home";
    }

    @GetMapping("/view-add")
    public String viewAdd(Model model) {
        model.addAttribute("khachhang", new KhachHang());
        return "khach-hang/add";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable UUID id, Model model) {
        khachHangService.delete(id);
        return "redirect:/khach-hang/hien-thi";
    }

    @GetMapping("/view-update/{id}")
    public String viewUpdate(@PathVariable UUID id, Model model) {
        KhachHang khachHang = khachHangService.detail(id);
        model.addAttribute("khachhang", khachHang);
        return "khach-hang/update";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("khachhang") KhachHang khachHang, BindingResult result, Model model) {
        if(result.hasErrors()){
            return "khach-hang/add";
        }
        khachHangService.add(khachHang);
        return "redirect:/khach-hang/hien-thi";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("khachhang") KhachHang khachHang, BindingResult result, Model model) {
        if(result.hasErrors()){
            return "khach-hang/update";
        }
        khachHangService.add(khachHang);
        return "redirect:/khach-hang/hien-thi";
    }
}
