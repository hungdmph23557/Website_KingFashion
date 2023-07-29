package com.example.demo.controller;

import com.example.demo.entity.HoaDon;
import com.example.demo.entity.LichSuHoaDon;
import com.example.demo.service.LichSuHoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/lich-su-hoa-don/")
public class LichSuHoaDonController {

    @Autowired
    private LichSuHoaDonService lichSuHoaDonService;



    @GetMapping("hien-thi")
    public String hienThiLichSuHoaDon(Model model){
        List<LichSuHoaDon> listLSHD = lichSuHoaDonService.getAll();
        model.addAttribute("listLSHD", listLSHD);
        return "hoadon/hoa-don-chi-tiet";
    }

    @GetMapping("view-lich-su-hoa-don")
    public String viewLichSuHoaDon(@ModelAttribute("hd1") HoaDon hoaDon, @RequestParam("idlichsuhoadon") UUID id, Model model){
        List<LichSuHoaDon> lichSuHoaDon = lichSuHoaDonService.detail(hoaDon.getLichSuHoaDon().getId());
        model.addAttribute("lshd1", lichSuHoaDon);
        System.out.println(hoaDon.getLichSuHoaDon().getId());
        return "hoadon/hoa-don-chi-tiet";
    }


}
