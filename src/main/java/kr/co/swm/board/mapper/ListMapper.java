package kr.co.swm.board.mapper;

import kr.co.swm.board.list.model.DTO.ListDTO;
import kr.co.swm.board.list.model.DTO.PageInfoDTO;
import kr.co.swm.board.list.model.DTO.SearchDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ListMapper {
  // 업소 게시글 불러오기
  List<ListDTO> getPlace(@Param("pi") PageInfoDTO pi, @Param("searchDTO") SearchDTO searchDTO);
  // 총 게시글 개수
  int getTotalCount(@Param("searchDTO") SearchDTO searchDTO);
  // 최저 기본 가격
  List<ListDTO> getCost();

  // 체크인 & 체크아웃 지정할 때 나오는 리스트
  List<ListDTO> getCheck(@Param("checkinDate") String checkinDate, @Param("checkoutDate") String checkoutDate);

}
