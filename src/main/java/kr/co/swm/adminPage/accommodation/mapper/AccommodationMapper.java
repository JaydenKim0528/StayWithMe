package kr.co.swm.adminPage.accommodation.mapper;

import kr.co.swm.adminPage.accommodation.model.dto.AccommodationDto;
import kr.co.swm.adminPage.accommodation.model.dto.AccommodationImageDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AccommodationMapper {

    int enrollAccommodation(@Param("accommodation")AccommodationDto accommodationDto, @Param("no")int no);

    int enrollFacilities(@Param("facility")String facility,@Param("no")int no);

    int enrollMainImage(@Param("mainImage")AccommodationImageDto mainImage,@Param("no")int no);

    int enrollRoom(@Param("accommodation")AccommodationDto accommodationDto,@Param("no")int no, @Param("categoryNo")int categoryNo, @Param("roomName")String roomName, @Param("checkIn")String checkIn, @Param("checkOut")String checkOut, @Param("roomValue")int roomValue);

    int enrollRoomImages(@Param("subImage")AccommodationImageDto subImage, @Param("roomNo")int roomNo);
}
