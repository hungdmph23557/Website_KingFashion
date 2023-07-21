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

    @Scheduled(cron = "0 0 0 * * *")
    public void updateVoucherStatus() {
        List<Voucher> expiredVouchers = voucherRepository.findByThoiGianKetThucBeforeAndTrangThaiEquals(new Date(),1);
        for (Voucher voucher : expiredVouchers) {
            voucher.setTrangThai(0);
            voucherRepository.save(voucher);
        }
    }

}
