package com.example.demo.schedule;

import com.example.demo.entity.Voucher;
import com.example.demo.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ScheduleVoucher {

    @Autowired
    private VoucherRepository voucherRepository;

    @Scheduled(fixedRate = 600000)
    public void updateVoucherStatus() {
        List<Voucher> expiredVouchers = voucherRepository.findByThoiGianKetThucBeforeAndTrangThaiNot(new Date(),0);
        for (Voucher voucher : expiredVouchers) {
            voucher.setTrangThai(0);
            voucherRepository.save(voucher);
        }
    }

}
