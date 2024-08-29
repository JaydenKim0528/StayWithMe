package kr.co.swm.reservation.controller;

import kr.co.swm.coupon.model.dto.CouponListDto;
import kr.co.swm.jwt.util.JWTUtil;
import kr.co.swm.member.model.dto.UserDTO;
import kr.co.swm.model.dto.SellerDto;
import kr.co.swm.model.dto.WebDto;
import kr.co.swm.reservation.model.dto.PaymentDto;
import kr.co.swm.reservation.model.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ReservationController {

    private final ReservationService reservationService;
    private final JWTUtil jwtUtil;

    @Autowired
    public ReservationController(ReservationService reservationService, JWTUtil jwtUtil) {
        this.reservationService = reservationService;
        this.jwtUtil = jwtUtil;
    }


    @GetMapping("/reservation0")
    public String reservation(@CookieValue(name = "Authorization", required = false)String userKey,
                              Model model) {

        Long userNo = jwtUtil.getUserNoFromToken(userKey);

        System.out.println("userNo : " + userNo);

        // 사용안한 쿠폰 리스트 전체조회
        // 최종 가격 변수 넘어오고, AND final_price < minPrice
        List<WebDto> coupons = reservationService.couponList(userNo);
        SellerDto list = reservationService.reserveList(userNo);
        UserDTO user = reservationService.userInfo(userNo);


        // 하드코딩
        int price = 200;
        String checkIn = "2024-08-24";
        String checkOut = "2024-08-26";


        model.addAttribute("coupons", coupons);
        model.addAttribute("list", list);
        model.addAttribute("user", user);
        model.addAttribute("price", price);
        model.addAttribute("checkIn", checkIn);
        model.addAttribute("checkOut", checkOut);

        return "reservation";
    }

    @PostMapping("/apply-coupon")
    @ResponseBody
    public WebDto applyCoupon(@RequestBody WebDto request) {
        int couponId = request.getCouponId();

        // 서비스에서 쿠폰 ID에 따라 할인 값과 할인 타입 가져오기
        WebDto discount = reservationService.getDiscount(couponId);
        return discount;
    }

    @PostMapping("/reserve-save")
    @ResponseBody  // 이 메서드에서 반환된 객체를 JSON으로 변환하여 클라이언트에게 전달
    public ResponseEntity<Map<String, Object>> processReservation(@RequestBody Map<String, Object> reservationData,
                                                                  @CookieValue(name = "Authorization", required = false)String userKey
                                                                    ) {
        Long userNo = jwtUtil.getUserNoFromToken(userKey);

        // JSON으로 넘어온 데이터를 받아옵니다.
        String reserveCheckIn = (String) reservationData.get("checkInDate");
        String reserveCheckOut = (String) reservationData.get("checkOutDate");

        Integer couponId = null;
        if (reservationData.get("selectedCouponId") != null) {
            couponId = Integer.parseInt((String) reservationData.get("selectedCouponId"));
        }

        System.out.println(couponId);
        Integer reserveAmount = null;

        if (couponId != null) {
            if (reservationData.get("finalPrice") != null) {
                reserveAmount = (Integer) reservationData.get("finalPrice");
            }
        } else  {
            reserveAmount = Integer.parseInt((String) reservationData.get("finalPrice"));
        }

        Integer reserveRoomNo = null;
        if (reservationData.get("roomNo") != null) {
            reserveRoomNo = Integer.parseInt((String) reservationData.get("roomNo"));
        }

        SellerDto sellerDto = new SellerDto(reserveCheckIn, reserveCheckOut, reserveRoomNo, reserveAmount);

        int save = reservationService.reserveSave(sellerDto, couponId, userNo);
        int reservationNo = sellerDto.getBookingNo();

        // 예약 처리 결과를 반환합니다.
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");  // 예약 처리 성공 여부를 반환
        response.put("saveNo", reservationNo);
        response.put("reserveAmount", sellerDto.getReserveAmount());

        return ResponseEntity.ok(response);  // 성공적으로 처리했음을 클라이언트에 응답
    }


    @PostMapping("/payment")
    public String payment(@RequestParam("basic-price")String basicPriceStr,
                                     @RequestParam("discount-price")String discountPriceStr,
                                     @RequestParam("reserveAmount")String finalPriceStr,
                                     @RequestParam("reNo") String reservationNoStr,
                                     @RequestParam("uid")String uid,
                                     @RequestParam("method")String method
    ) {

        int basicPrice = Integer.parseInt(basicPriceStr);
        int discountPrice = Integer.parseInt(discountPriceStr);
        int finalPrice = Integer.parseInt(finalPriceStr);
        int reservationNo = Integer.parseInt(reservationNoStr);

        PaymentDto price = new PaymentDto(basicPrice, discountPrice, finalPrice);

        // 결제 테이블 인입
        int reservationSave = reservationService.paymentSave(price , reservationNo);
        int paymentNo = price.getPaymentNo();

        // 결제 승인 테이블 인입
        PaymentDto paymentDto = new PaymentDto(finalPrice, uid, method);
        if (reservationSave > 0) {
            reservationService.paymentDetail(paymentDto, paymentNo);
        }

        return "redirect:/complete";
    }

    @PostMapping("/refund")
    public ResponseEntity<String> refund(@RequestBody Map<String, String> refundData) {
        try {
            String cancelBy = refundData.get("cancel_by");
            int bookingNo = Integer.parseInt(refundData.get("booking_no"));
            int cancelAmount = Integer.parseInt(refundData.get("cancel_amount"));

            int refundResult = reservationService.refund(cancelBy, bookingNo, cancelAmount);

            if (refundResult > 0) {
                // DB 업데이트 성공 시 OK 반환
                return ResponseEntity.ok("OK");
            } else {
                // DB 업데이트 실패 시 실패 메시지 반환
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("DB 업데이트 실패");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("오류 발생: " + e.getMessage());
        }
    }






    @GetMapping("/complete")
    public String complete() {
        return "complete";
    }

}
