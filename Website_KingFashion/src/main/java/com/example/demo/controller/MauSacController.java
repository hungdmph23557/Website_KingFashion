package com.example.demo.controller;

import com.example.demo.entity.MauSac;
import com.example.demo.service.MauSacService;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Controller
@RequestMapping("/mau-sac/")
public class MauSacController {

    @Autowired
    private MauSacService mauSacService;

    private List<MauSac> listMauSac = new ArrayList<>();


    @GetMapping("hien-thi")
    public String hienThi(@ModelAttribute("message")String message,@RequestParam(defaultValue = "0", name = "page") Integer pageNum, Model model) {
//        listMauSac = mauSacService.getAll();

        Page<MauSac> page = mauSacService.phanTrangMauSac(pageNum, 5);
        model.addAttribute("listms", page);
        model.addAttribute("ms1", new MauSac());
        model.addAttribute("msg", message);
        return "mausac/mausac";
    }

    @PostMapping("add")
    public String addMauSac(@Valid @ModelAttribute("ms1") MauSac mauSac, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "mausac/mausac";
        }
        String ma = "MS" + new Random().nextInt(100000);
        mauSac.setMaMauSac(ma);
        mauSac.setNgayTao(new Date());
        mauSac.setTrangThai(1);
        model.addAttribute("ms1", mauSac);
        mauSacService.add(mauSac);
        return "redirect:/mau-sac/hien-thi";
    }

    @GetMapping("delete/{id}")
    public String deleteMauSac(@PathVariable UUID id,RedirectAttributes redirectAttributes ,Model model) {
        mauSacService.delete(id);
        redirectAttributes.addFlashAttribute("message","Delete Success");
        return "redirect:/mau-sac/hien-thi";
    }

    @GetMapping("view-update/{id}")
    public String updateMauSac(@PathVariable UUID id, Model model) {
        MauSac mauSac = mauSacService.detail(id);
        model.addAttribute("ms1", mauSac);
        return "mausac/update-mausac";
    }

    @PostMapping("update")
    public String updateMauSac(@Valid @ModelAttribute("ms1") MauSac mauSac, BindingResult result, Model model, @RequestParam("ngayTao") String ngayTao) {
        if (result.hasErrors()) {
            return "mausac/mausac";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date ngayTaoDate;
        try {
            ngayTaoDate = dateFormat.parse(ngayTao);
        } catch (ParseException e) {
            e.printStackTrace();
            return "redirect:/error";
        }

        mauSac.setNgayTao(ngayTaoDate);
        mauSac.setNgaySua(new Date());
        model.addAttribute("ms1", mauSac);
        mauSacService.add(mauSac);
        return "redirect:/mau-sac/hien-thi";
    }

}
