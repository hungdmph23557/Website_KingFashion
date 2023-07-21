package com.example.demo.controller;

import com.example.demo.entity.Voucher;
import com.example.demo.service.impl.VoucherServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/voucher")
public class VoucherController {

    public static int entity = 1;

    @Autowired
    private VoucherServiceImpl voucherService;

    @GetMapping("/hien-thi")
    public String hienThi(Model model, @RequestParam(name = "page", defaultValue = "0") Integer p, HttpSession session) {
        if (session.getAttribute("successMessage") != null) {
            String successMessage = (String) session.getAttribute("successMessage");
            model.addAttribute("successMessage", successMessage);
            session.removeAttribute("successMessage");
        }
        Page<Voucher> page = voucherService.page(p, 5);
        model.addAttribute("list", page);
        model.addAttribute("search", new Voucher());
        return "voucher/home";
    }

    @GetMapping("/search")
    public String search(Model model, @ModelAttribute("search") Voucher voucher, @RequestParam(name = "page", defaultValue = "0") Integer p) {
        Page<Voucher> list = voucherService.search(voucher.getMa(), voucher.getTen(), voucher.getMucGiam(), voucher.getTien(),
                voucher.getThoiGianBatDau(), voucher.getThoiGianKetThuc(), voucher.getTrangThai(), 5, p);
        model.addAttribute("list", list);
        return "voucher/home";
    }

    @GetMapping("/view-add")
    public String viewAdd(Model model) {
        model.addAttribute("voucher", new Voucher());
        return "voucher/add";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable UUID id, Model model, HttpSession session) {
        voucherService.delete(id);
        session.setAttribute("successMessage", "Xoá thành công");
        return "redirect:/voucher/hien-thi";
    }

    @GetMapping("/view-update/{id}")
    public String viewUpdate(@PathVariable UUID id, Model model) {
        Voucher voucher = voucherService.detail(id);
        model.addAttribute("voucher", voucher);

        return "voucher/update";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("voucher") Voucher voucher, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors() || voucher.getThoiGianBatDau().after(voucher.getThoiGianKetThuc())) {
            result.rejectValue("thoiGianKetThuc", null, "Ngày bắt đầu không được lớn hơn ngày kết thúc");
            return "voucher/add";
        }
        String ma = "VOC" + entity++;
        voucher.setMa(ma);
        voucherService.add(voucher);
        session.setAttribute("successMessage", "Thêm thành công");
        return "redirect:/voucher/hien-thi";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("voucher") Voucher voucher, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            return "voucher/update";
        }
        voucherService.add(voucher);
        session.setAttribute("successMessage", "Update thành công");
        return "redirect:/voucher/hien-thi";
    }
}
