package com.example.demo.controller;

import com.example.demo.entity.Anh;
import com.example.demo.entity.ChiTietSanPham;
import com.example.demo.entity.KichCo;
import com.example.demo.entity.SanPham;
import com.example.demo.repository.AnhRepository;
import com.example.demo.service.AnhService;
import com.example.demo.service.ChatLieuService;
import com.example.demo.service.ChiTietSanPhamService;
import com.example.demo.service.CoAoService;
import com.example.demo.service.KichCoService;
import com.example.demo.service.LoaiSanPhamService;
import com.example.demo.service.MauSacService;
import com.example.demo.service.NhaSanXuatService;
import com.example.demo.service.SanPhamService;
import jakarta.servlet.http.HttpSession;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private AnhRepository anhRepository;

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
    public String hienThi(@RequestParam(defaultValue = "0", name = "page") Integer pageNum, Model model, HttpSession session) {
        if (session.getAttribute("successMessage") != null) {
            String successMessage = (String) session.getAttribute("successMessage");
            model.addAttribute("successMessage", successMessage);
            session.removeAttribute("successMessage");
        }
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

    @GetMapping("view-add")
    public String viewAdd(Model model) {
        model.addAttribute("listKichCo", kichCoService.getAll());
        model.addAttribute("listCoAo", coAoService.getAll());
        model.addAttribute("listLoaiSanPham", loaiSanPhamService.getAll());
        model.addAttribute("listMauSac", mauSacService.getAll());
        model.addAttribute("listNhaSanXuat", nhaSanXuatService.getAll());
        model.addAttribute("listChatLieu", chatLieuService.getAll());
        model.addAttribute("att", new ChiTietSanPham());
        return "chitietsanpham/add-chi-tiet-san-pham";
    }

    @PostMapping("add")
    public String add(@Valid @ModelAttribute("att") ChiTietSanPham chiTietSanPham,
                      BindingResult result,
                      RedirectAttributes redirectAttributes,
                      @RequestParam(defaultValue = "0", name = "page") Integer pageNum,
                      Model model, HttpSession session) {

        if (result.hasErrors()) {
            Page<ChiTietSanPham> page = chiTietSanPhamService.PhanTrang(pageNum, 5);
            model.addAttribute("list", page);
            model.addAttribute("listKichCo", kichCoService.getAll());
            model.addAttribute("listCoAo", coAoService.getAll());
            model.addAttribute("listLoaiSanPham", loaiSanPhamService.getAll());
            model.addAttribute("listMauSac", mauSacService.getAll());
            model.addAttribute("listNhaSanXuat", nhaSanXuatService.getAll());
            model.addAttribute("listChatLieu", chatLieuService.getAll());
            return "chitietsanpham/chi-tiet-san-pham";
        }
        String tenSanPham = chiTietSanPham.getSanPham().getTen();
        String moTaSanPham = chiTietSanPham.getSanPham().getMoTa();
//        String tenkichCo = chiTietSanPham.getKichCo().getTen();
//        Integer soLuongkichCo = chiTietSanPham.getKichCo().getSoLuong();

        String maSanPham = "SP" + currentNumber;
        String maChiTietSanPham = "CTSP" + currentNumber;
//        String makichCo = "KC" + currentNumber;
        currentNumber++;

        SanPham sanPham = new SanPham();
        sanPham.setMa(maSanPham);
        sanPham.setMoTa(moTaSanPham);
        sanPham.setTen(tenSanPham);
        sanPham.setNgayTao(new Date());
        sanPham.setTrangThai(1);
        sanPhamService.add(sanPham);

//        KichCo kichCo = new KichCo();
//        kichCo.setMa(makichCo);
//        kichCo.setTen(tenkichCo);
//        kichCo.setSoLuong(soLuongkichCo);
//        kichCo.setNgayTao(new Date());
//        kichCo.setTrangThai(1);
//        kichCoService.add(kichCo);


        chiTietSanPham.setMa(maChiTietSanPham);
        chiTietSanPham.setNgayTao(new Date());
        chiTietSanPham.setTrangThai(1);

        chiTietSanPham.setSanPham(sanPham);
//        chiTietSanPham.setKichCo(kichCo);
        model.addAttribute("att", sanPham);
//        model.addAttribute("att", kichCo);
        model.addAttribute("att", chiTietSanPham);
        session.setAttribute("successMessage", "Thêm thành công!");
        chiTietSanPhamService.add(chiTietSanPham);
        redirectAttributes.addAttribute("id", chiTietSanPham.getId());
        return "redirect:/chi-tiet-san-pham/view-update/{id}";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable UUID id, Model model, HttpSession session) {
        session.setAttribute("successMessage", "Xóa thành công!");
        chiTietSanPhamService.delete(id);
        return "redirect:/chi-tiet-san-pham/hien-thi";
    }

    @GetMapping("view-update/{id}")
    public String updateMauSac(@PathVariable UUID id, Model model) {
        model.addAttribute("list", chiTietSanPhamService.getAll());
        model.addAttribute("listKichCo", kichCoService.getAll());
        model.addAttribute("listCoAo", coAoService.getAll());
        model.addAttribute("listLoaiSanPham", loaiSanPhamService.getAll());
        model.addAttribute("listMauSac", mauSacService.getAll());
        model.addAttribute("listNhaSanXuat", nhaSanXuatService.getAll());
        model.addAttribute("listChatLieu", chatLieuService.getAll());
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.detail(id);
        model.addAttribute("att", chiTietSanPham);
        return "chitietsanpham/adds-chi-tiet-san-pham";
    }

    @PostMapping("update")
    public String update(@Valid @ModelAttribute("att") ChiTietSanPham chiTietSanPham, BindingResult result,
                         RedirectAttributes redirectAttributes,
                         Model model, @RequestParam("ngayTao") String ngayTao) {
        if (result.hasErrors()) {
            return "chitietsanpham/update-chi-tiet-san-pham";
        }
//        String tenkichCo = chiTietSanPham.getKichCo().getTen();
//        Integer soLuongkichCo = chiTietSanPham.getKichCo().getSoLuong();

        chiTietSanPham.setNgaySua(new Date());
        model.addAttribute("att", chiTietSanPham);
        chiTietSanPhamService.add(chiTietSanPham);
        redirectAttributes.addAttribute("id", chiTietSanPham.getId());
        return "redirect:/chi-tiet-san-pham/view-update/{id}";
    }
}
