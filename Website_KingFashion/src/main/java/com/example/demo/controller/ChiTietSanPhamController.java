package com.example.demo.controller;

import com.example.demo.entity.AddForm;
import com.example.demo.entity.Anh;
import com.example.demo.entity.ChiTietSanPham;
import com.example.demo.entity.SanPham;
import com.example.demo.service.AnhService;
import com.example.demo.service.ChatLieuService;
import com.example.demo.service.ChiTietSanPhamService;
import com.example.demo.service.CoAoService;
import com.example.demo.service.KichCoService;
import com.example.demo.service.LoaiSanPhamService;
import com.example.demo.service.MauSacService;
import com.example.demo.service.NhaSanXuatService;
import com.example.demo.service.SanPhamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("/chi-tiet-san-pham/")
public class ChiTietSanPhamController {

    private static int currentNumber = 1;

    @Autowired
    private ChiTietSanPhamService chiTietSanPhamService;

    @Autowired
    private AnhService anhService;

    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private KichCoService kichCoService;

    @Autowired
    private CoAoService coAoService;

    @Autowired
    private LoaiSanPhamService loaiSanPhamService;

    @Autowired
    private MauSacService mauSacService;

    @Autowired
    private NhaSanXuatService nhaSanXuatService;

    @Autowired
    private ChatLieuService chatLieuService;

    @GetMapping("hien-thi")
    public String hienThi(@RequestParam(defaultValue = "0", name = "page") Integer pageNum, Model model) {
        Page<ChiTietSanPham> page = chiTietSanPhamService.PhanTrang(pageNum, 5);
        model.addAttribute("list", page);
        model.addAttribute("listKichCo", kichCoService.getAll());
        model.addAttribute("listCoAo", coAoService.getAll());
        model.addAttribute("listLoaiSanPham", loaiSanPhamService.getAll());
        model.addAttribute("listMauSac", mauSacService.getAll());
        model.addAttribute("listNhaSanXuat", nhaSanXuatService.getAll());
        model.addAttribute("listChatLieu", chatLieuService.getAll());
        model.addAttribute("att", new ChiTietSanPham());
        return "chitietsanpham/chi-tiet-san-pham";
    }

    @PostMapping("add")
    public String add(@Valid @ModelAttribute("att") AddForm addForm, BindingResult result, @RequestParam MultipartFile img, Model model) {

        if (result.hasErrors()) {
            return "chitietsanpham/chi-tiet-san-pham";
        }



        String maSanPham = "SP" + currentNumber;
        currentNumber++;
        SanPham sanPham = addForm.getSanPham();
        sanPham.setMa(maSanPham);
        sanPham.setNgayTao(new Date());
        sanPham.setTrangThai(1);

        String maAnh = "Anh" + currentNumber;
        currentNumber++;
        Anh anh = addForm.getAnh();
        anh.setMa(maAnh);
        anh.setTen(img.getOriginalFilename());
        anh.setNgayTao(new Date());
        anh.setTrangThai(1);

        String maChiTietSanPham = "CTSP" + currentNumber;
        currentNumber++;
        ChiTietSanPham chiTietSanPham = addForm.getChiTietSanPham();
        chiTietSanPham.setMa(maChiTietSanPham);
        chiTietSanPham.setNgayTao(new Date());
        chiTietSanPham.setTrangThai(1);

        model.addAttribute("att", chiTietSanPham);
        model.addAttribute("att", sanPham);
        model.addAttribute("att", anh);

        chiTietSanPhamService.add(chiTietSanPham);
        sanPhamService.add(sanPham);
//        Anh uploadAnh = anhService.add(anh);

//        if (uploadAnh != null) {
//            try {
//                File saveFile = new ClassPathResource("static/image").getFile();
//                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + img.getOriginalFilename());
//                Files.copy(img.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }


        return "redirect:/chi-tiet-san-pham/hien-thi";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable UUID id, Model model) {
        chiTietSanPhamService.delete(id);
        return "redirect:/chi-tiet-san-pham/hien-thi";
    }

    @GetMapping("view-update/{id}")
    public String updateMauSac(@PathVariable UUID id, Model model) {
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.detail(id);
        model.addAttribute("att", chiTietSanPham);
        return "chitietsanpham/update-chi-tiet-san-pham";
    }

    @PostMapping("update")
    public String updateMauSac(@Valid @ModelAttribute("att") ChiTietSanPham chiTietSanPham, BindingResult result, Model model, @RequestParam("ngayTao") String ngayTao) {
        if (result.hasErrors()) {
            return "chitietsanpham/update-chi-tiet-san-pham";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date ngayTaoDate;
        try {
            ngayTaoDate = dateFormat.parse(ngayTao);
        } catch (ParseException e) {
            e.printStackTrace();
            return "redirect:/error";
        }

        chiTietSanPham.setNgayTao(ngayTaoDate);
        chiTietSanPham.setNgaySua(new Date());
        model.addAttribute("att", chiTietSanPham);
        chiTietSanPhamService.add(chiTietSanPham);
        return "redirect:/chi-tiet-san-pham/hien-thi";
    }
}
