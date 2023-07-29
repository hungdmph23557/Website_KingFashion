package com.example.demo.controller;

import com.example.demo.entity.NhaSanXuat;
import com.example.demo.service.NhaSanXuatService;
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
@RequestMapping("/nha-san-xuat/")
public class NhaSanXuatController {
    @Autowired
    private NhaSanXuatService nhaSanXuatService;

    @GetMapping("hien-thi")
    public String hienThi(@RequestParam(defaultValue = "0", name = "page") Integer pageNum, Model model) {
        Page<NhaSanXuat> page = nhaSanXuatService.phanTrangNhaSanXuat(pageNum, 5);
        model.addAttribute("listnsx", page);
        model.addAttribute("nsx1", new NhaSanXuat());
        return "nhasanxuat/nha-san-xuat";
    }

    @PostMapping("add")
    public String addNhaSanXuat(@Valid @ModelAttribute("nsx1") NhaSanXuat nhaSanXuat, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "nhasanxuat/nha-san-xuat";
        }
        String ma = "NSX" + new Random().nextInt(100000);
        nhaSanXuat.setMaNhaSanXuat(ma);
        nhaSanXuat.setTrangThai(1);
        nhaSanXuat.setNgayTao(new Date());
        model.addAttribute("nsx1", nhaSanXuat);

        nhaSanXuatService.add(nhaSanXuat);
        return "redirect:/nha-san-xuat/hien-thi";
    }

    @GetMapping("delete/{id}")
    public String deleteNhaSanXuat(@PathVariable UUID id, Model model) {
        nhaSanXuatService.delete(id);
        return "redirect:/nha-san-xuat/hien-thi";
    }

    @GetMapping("view-update/{id}")
    public String updateNhaSanXuat(@PathVariable UUID id, Model model) {
        NhaSanXuat nhaSanXuat = nhaSanXuatService.detail(id);
        model.addAttribute("nsx1", nhaSanXuat);
        return "nhasanxuat/update-nha-san-xuat";
    }

    @PostMapping("update")
    public String updateNhaSanXuat(@Valid @ModelAttribute("nsx1") NhaSanXuat nhaSanXuat, BindingResult result, Model model, @RequestParam("ngayTao") String ngayTao) {
        if (result.hasErrors()) {
            return "nhasanxuat/nha-san-xuat";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date ngayTaoDate;
        try {
            ngayTaoDate = dateFormat.parse(ngayTao);
        } catch (ParseException e) {
            e.printStackTrace();
            return "redirect:/error";
        }

        nhaSanXuat.setNgayTao(ngayTaoDate);
        nhaSanXuat.setNgaySua(new Date());
        model.addAttribute("nsx1", nhaSanXuat);
        nhaSanXuatService.add(nhaSanXuat);
        return "redirect:/nha-san-xuat/hien-thi";
    }
}
