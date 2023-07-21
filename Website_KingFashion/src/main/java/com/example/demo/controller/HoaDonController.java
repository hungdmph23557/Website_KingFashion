package com.example.demo.controller;

import com.example.demo.entity.HoaDon;
import com.example.demo.entity.LichSuHoaDon;
import com.example.demo.entity.TaiKhoan;
import com.example.demo.service.HoaDonService;
import com.example.demo.service.LichSuHoaDonService;
import com.example.demo.service.TaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/hoa-don")
public class HoaDonController {


    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private LichSuHoaDonService lichSuHoaDonService;

    @Autowired
    private TaiKhoanService taiKhoanService;



    @GetMapping("hien-thi")
    public String hienThiHoaDon(Model model, @RequestParam(name ="page", defaultValue = "0") Integer pageNum){
        Page<HoaDon> hoaDonPage = hoaDonService.phanTrangHoaDon(pageNum, 5);
        model.addAttribute("listHD", hoaDonPage);
        List<LichSuHoaDon> lichSuHoaDons = lichSuHoaDonService.getAll();
        model.addAttribute("listLSHD", lichSuHoaDons);
        List<TaiKhoan> taiKhoans = taiKhoanService.getAll();
        model.addAttribute("listTK", taiKhoans);
        return "hoadon/hoadon";
    }
}
