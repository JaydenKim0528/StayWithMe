package kr.co.swm.reservation.mapper;

import kr.co.swm.member.model.dto.UserDTO;
import kr.co.swm.model.dto.SellerDto;
import kr.co.swm.model.dto.WebDto;
import kr.co.swm.reservation.model.dto.PaymentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReservationMapper {

    List<WebDto> couponList(@Param("userNo")Long userNo);

    SellerDto reserveList(@Param("userNo")Long userNo);

    UserDTO userInfo(@Param("userNo")Long userNo);

    WebDto getDiscount(@Param("couponId")int couponId);

    int reserveSave(@Param("sellerDto")SellerDto sellerDto, @Param("couponId")Integer couponId, @Param("userNo")Long userNo);

    int paymentSave(@Param("payment")PaymentDto paymentDto, @Param("reservationNo")int reservationNo);

    int paymentDetail(@Param("payment")PaymentDto paymentDto, @Param("paymentNo")int paymentNo);

    int refund(@Param("cancelBy")String cancelBy, @Param("bookingNo")int bookingNo, @Param("cancelAmount")int cancelAmount);
}
