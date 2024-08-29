package kr.co.swm.reservation.model.service;

import kr.co.swm.coupon.model.dto.CouponListDto;
import kr.co.swm.member.model.dto.UserDTO;
import kr.co.swm.model.dto.SellerDto;
import kr.co.swm.model.dto.WebDto;
import kr.co.swm.reservation.mapper.ReservationMapper;
import kr.co.swm.reservation.model.dto.PaymentDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationMapper reservationMapper;

    @Autowired
    public ReservationServiceImpl(ReservationMapper reservationMapper) {
        this.reservationMapper = reservationMapper;
    }

    @Override
    public List<WebDto> couponList(Long userNo) {
        return reservationMapper.couponList(userNo);
    }

    @Override
    public SellerDto reserveList(Long userNo) {
        return reservationMapper.reserveList(userNo);
    }

    @Override
    public UserDTO userInfo(Long userNo) {
        return reservationMapper.userInfo(userNo);
    }

    @Override
    public WebDto getDiscount(int couponId) {
        return reservationMapper.getDiscount(couponId);
    }

    @Override
    public int reserveSave(SellerDto sellerDto, Integer couponId, Long userNo){
        return reservationMapper.reserveSave(sellerDto, couponId, userNo);
    }

    @Override
    public int paymentSave(PaymentDto paymentDto, int reservationNo) {

        int result = reservationMapper.paymentSave(paymentDto, reservationNo);
        return result;

    }

    public int paymentDetail(PaymentDto paymentDto, int paymentNo) {
        return reservationMapper.paymentDetail(paymentDto, paymentNo);
    }

    // 예약상태 업데이트 ( 예약안료/실패 )
//        reservationService.reserveUpdate();

//        // 쿠폰 사용 시 쿠폰 use 컬럼 업데이트
//        reservationService.couponUsedUpdate();

    public int refund(String cancelBy, int bookingNo,int  cancelAmount) {
        int result = reservationMapper.refund(cancelBy, bookingNo, cancelAmount);
        if (result == 1) {
            // 예약 취소 업데이트
            return 1;
        } else {
            return 0;
        }
    }
}
