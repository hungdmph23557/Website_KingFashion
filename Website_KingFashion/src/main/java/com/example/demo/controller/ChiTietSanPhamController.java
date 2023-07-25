package com.example.demo.controller;

import com.example.demo.entity.Anh;
import com.example.demo.entity.ChatLieu;
import com.example.demo.entity.ChiTietSanPham;
import com.example.demo.entity.CoAo;
import com.example.demo.entity.KichCo;
import com.example.demo.entity.LoaiSanPham;
import com.example.demo.entity.MauSac;
import com.example.demo.entity.NhaSanXuat;
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
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    private List<KichCo> listKichCo = new ArrayList<>();

    @GetMapping("hien-thi")
    public String hienThi(@RequestParam(defaultValue = "0", name = "page") Integer pageNum, Model model, HttpSession session) {
        if (session.getAttribute("successMessage") != null) {
            String successMessage = (String) session.getAttribute("successMessage");
            model.addAttribute("successMessage", successMessage);
            session.removeAttribute("successMessage");
        }
        Page<ChiTietSanPham> page = chiTietSanPhamService.PhanTrang(pageNum, 5);
        model.addAttribute("list", page);
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
        model.addAttribute("listCoAo", coAoService.getAll());
        model.addAttribute("listLoaiSanPham", loaiSanPhamService.getAll());
        model.addAttribute("listMauSac", mauSacService.getAll());
        model.addAttribute("listNhaSanXuat", nhaSanXuatService.getAll());
        model.addAttribute("listChatLieu", chatLieuService.getAll());
        model.addAttribute("att", new ChiTietSanPham());
        model.addAttribute("lsp1", new LoaiSanPham());
        model.addAttribute("ms1", new MauSac());
        model.addAttribute("nsx1", new NhaSanXuat());
        model.addAttribute("ca1", new CoAo());
        model.addAttribute("cl1", new ChatLieu());
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
            model.addAttribute("listCoAo", coAoService.getAll());
            model.addAttribute("listLoaiSanPham", loaiSanPhamService.getAll());
            model.addAttribute("listMauSac", mauSacService.getAll());
            model.addAttribute("listNhaSanXuat", nhaSanXuatService.getAll());
            model.addAttribute("listChatLieu", chatLieuService.getAll());
            return "chitietsanpham/chi-tiet-san-pham";
        }
        String tenSanPham = chiTietSanPham.getSanPham().getTen();
        String moTaSanPham = chiTietSanPham.getSanPham().getMoTa();

        String maSanPham = "SP" + currentNumber;
        String maChiTietSanPham = "CTSP" + currentNumber;

        currentNumber++;

        SanPham sanPham = new SanPham();
        sanPham.setMa(maSanPham);
        sanPham.setMoTa(moTaSanPham);
        sanPham.setTen(tenSanPham);
        sanPham.setNgayTao(new Date());
        sanPham.setTrangThai(1);
        sanPhamService.add(sanPham);


        chiTietSanPham.setMa(maChiTietSanPham);
        chiTietSanPham.setNgayTao(new Date());
        chiTietSanPham.setTrangThai(1);

        chiTietSanPham.setSanPham(sanPham);

        model.addAttribute("att", sanPham);

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
    public String updateCTSP(@PathVariable UUID id, Model model) {
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.detail(id);

        listKichCo = kichCoService.getKichCoByChiTietSanPhamId(id);
        model.addAttribute("att", chiTietSanPham);
        model.addAttribute("listKc", listKichCo);
        model.addAttribute("listCoAo", coAoService.getAll());
        model.addAttribute("listLoaiSanPham", loaiSanPhamService.getAll());
        model.addAttribute("listMauSac", mauSacService.getAll());
        model.addAttribute("listNhaSanXuat", nhaSanXuatService.getAll());
        model.addAttribute("listChatLieu", chatLieuService.getAll());
        model.addAttribute("kc1", new KichCo());
        model.addAttribute("a1", new Anh());
        model.addAttribute("lsp1", new LoaiSanPham());
        model.addAttribute("ms1", new MauSac());
        model.addAttribute("nsx1", new NhaSanXuat());
        model.addAttribute("ca1", new CoAo());
        model.addAttribute("cl1", new ChatLieu());

        List<Anh> listAnh = anhService.getAllByChiTietSanPhamId(id);
        model.addAttribute("listAnh", listAnh);
        return "chitietsanpham/add-kich-co-anh-chi-tiet-san-pham";
    }


    @PostMapping("update")
    public String update(@Valid @ModelAttribute("att") ChiTietSanPham chiTietSanPham, BindingResult result,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (result.hasErrors()) {
            return "chitietsanpham/update-chi-tiet-san-pham";
        }

        chiTietSanPham.setNgaySua(new Date());
        model.addAttribute("att", chiTietSanPham);
        chiTietSanPhamService.add(chiTietSanPham);
        redirectAttributes.addAttribute("id", chiTietSanPham.getId());
        return "redirect:/chi-tiet-san-pham/view-update/{id}";
    }

    @PostMapping("addLoaiSanPham")
    public String addLoaiSanPham(@Valid @ModelAttribute("lsp1") LoaiSanPham loaiSanPham, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "chitietsanpham/add-chi-tiet-san-pham";
        }
        loaiSanPham.setNgayTao(new Date());
        loaiSanPham.setMa("LSP01");
        loaiSanPham.setTrangThai(1);
        model.addAttribute("lsp1", loaiSanPham);
        loaiSanPhamService.add(loaiSanPham);
        return viewAdd(model);
    }

    @PostMapping("addMauSac")
    public String addMauSac(@Valid @ModelAttribute("ms1") MauSac mauSac, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "chitietsanpham/add-chi-tiet-san-pham";
        }
        mauSac.setNgayTao(new Date());
        mauSac.setTenMauSac("LSP01");
        mauSac.setTrangThai(1);
        model.addAttribute("ms1", mauSac);
        mauSacService.add(mauSac);
        return viewAdd(model);
    }

    @PostMapping("addNhaSanXuat")
    public String addMauSac(@Valid @ModelAttribute("nsx1") NhaSanXuat nhaSanXuat, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "chitietsanpham/add-chi-tiet-san-pham";
        }
        nhaSanXuat.setNgayTao(new Date());
        nhaSanXuat.setMaNhaSanXuat("LSP01");
        nhaSanXuat.setTrangThai(1);
        model.addAttribute("ms1", nhaSanXuat);
        nhaSanXuatService.add(nhaSanXuat);
        return viewAdd(model);
    }

    @PostMapping("addCoAo")
    public String addMauSac(@Valid @ModelAttribute("ca1") CoAo coAo, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "chitietsanpham/add-chi-tiet-san-pham";
        }
        coAo.setNgayTao(new Date());
        coAo.setMa("LSP01");
        coAo.setTrangThai(1);
        model.addAttribute("ms1", coAo);
        coAoService.add(coAo);
        return viewAdd(model);
    }

    @PostMapping("addChatLieu")
    public String addMauSac(@Valid @ModelAttribute("cl1") ChatLieu chatLieu, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "chitietsanpham/add-chi-tiet-san-pham";
        }
        chatLieu.setNgayTao(new Date());
        chatLieu.setMa("LSP01");
        chatLieu.setTrangThai(1);
        model.addAttribute("ms1", chatLieu);
        chatLieuService.add(chatLieu);
        return viewAdd(model);
    }

    @PostMapping("add-kich-co")
    public String addkc(@Valid @ModelAttribute("kc1") KichCo kichCo, BindingResult result,
                        @RequestParam("id") UUID id,
                        RedirectAttributes redirectAttributes,
                        Model model) {

        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.detail(id);
        kichCo.setChiTietSanPham(chiTietSanPham);
        kichCo.setNgayTao(new Date());
        kichCo.setMa("KC01");
        kichCo.setTrangThai(1);
        model.addAttribute("kc1", kichCo);
        redirectAttributes.addAttribute("id", kichCo.getId());
        model.addAttribute("id", id);
        kichCoService.add(kichCo);
        List<KichCo> kichCoList = kichCoService.getKichCoByChiTietSanPhamId(chiTietSanPham.getId());
        // Tính tổng số lượng kích cỡ
        int totalKichCoQuantity = kichCoList.stream().mapToInt(KichCo::getSoLuong).sum();
        // Set tổng số lượng kích cỡ cho sản phẩm chi tiết
        chiTietSanPham.setSoLuong(totalKichCoQuantity);
        chiTietSanPhamService.add(chiTietSanPham);
        return "redirect:/chi-tiet-san-pham/view-update/{id}";
    }

    @GetMapping("delete1/{id}")
    public String deleteKc(@ModelAttribute("att") ChiTietSanPham chiTietSanPham,@PathVariable UUID id, Model model,
                           RedirectAttributes redirectAttributes,
                           HttpSession session) {
        session.setAttribute("successMessage", "Xóa thành công!");
        kichCoService.delete(id);
        redirectAttributes.addAttribute("ids", chiTietSanPham.getId());
        return "redirect:/chi-tiet-san-pham/view-update/{ids}";
    }


    @GetMapping("anh-anh")
    public ModelAndView addImage() {
        return new ModelAndView("addimage");
    }

    @PostMapping("add-anh")
    public String addImagePost(@ModelAttribute("a1") Anh anh,
                               @RequestParam("image") MultipartFile[] files,
                               @RequestParam("id") UUID id,
                               RedirectAttributes redirectAttributes,
                               Model model
    ) throws IOException, SQLException {
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.detail(id);
        anh.setChiTietSanPham(chiTietSanPham);
        model.addAttribute("a1", anh);
        model.addAttribute("id", id);
        redirectAttributes.addAttribute("id", anh.getId());
        anhService.add(anh, files);
        return "redirect:/chi-tiet-san-pham/view-update/{id}";
    }

    @GetMapping("display")
    public ResponseEntity<byte[]> displayImage(@RequestParam("id") UUID id) throws IOException, SQLException {
        Anh image = anhService.viewById(id);
        byte[] imageBytes = image.getTen().getBytes(1, (int) image.getTen().length());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }
    @GetMapping("display1")
    public ResponseEntity<List<byte[]>> displayImages(@RequestParam("id") UUID id) throws IOException, SQLException {
        List<Anh> images = (List<Anh>) anhService.viewAllById(id);
        List<byte[]> imageBytesList = new ArrayList<>();

        for (Anh image : images) {
            byte[] imageBytes = image.getTen().getBytes(1, (int) image.getTen().length());
            imageBytesList.add(imageBytes);
        }

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytesList);
    }
}
