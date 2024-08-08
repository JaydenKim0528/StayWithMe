package kr.co.swm.adminPage.accommodation.model.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccommodationDto {

    private int accommodationNo;
    private String accommodationName;

    // 전번
    private String accommodationPhone;

    // 도로명
    private String accommodationPost;

    // 위도
    private double lat;

    // 경도
    private double lon;

}
