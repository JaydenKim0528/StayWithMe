package kr.co.swm.board.mapper;

import kr.co.swm.board.list.model.DTO.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ListMapper {
  //  게시글 불러오기
  List<ListDTO> getPlace(@Param("pi") PageInfoDTO pi, @Param("searchDTO") SearchDTO searchDTO);
  // 총 게시글 개수
  int getTotalCount(@Param("searchDTO") SearchDTO searchDTO);

  // tour페이지에서 부대시설 항목 불러오기
  List<String> getUniqueFacilities();

  int getListCount(@Param("mainSearchDTO") MainSearchDTO mainSearchDTO);

  List<ListDTO> getList(@Param("mainSearchDTO") MainSearchDTO mainSearchDTO);

  List<String> getFacilities(@Param("mainSearchDTO") MainSearchDTO mainSearchDTO);

  List<RoomDTO> getRooms(@Param("accommodationNo") int accommodationNo);

  Integer getMinBasicRate(@Param("roomNos") List<Integer> roomNos);

  Integer getMinExtraRate(@Param("roomNos") List<Integer> roomNos, @Param("searchDTO") SearchDTO searchDTO);
}
