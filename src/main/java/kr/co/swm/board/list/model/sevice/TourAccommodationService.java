package kr.co.swm.board.list.model.sevice;

import kr.co.swm.board.list.model.DTO.*;

import java.util.List;

public interface TourAccommodationService {
    //검색조건으로 업소 조회
    List<ListDTO> getAccommodation(PageInfoDTO pi, SearchDTO searchDTO);
    // 게시글의 수
    int getTotalCount(SearchDTO searchDTO);

    // 부대식별 옵션 전체조회
    List<String> getUniqueFacilities();

    // 게시물 수 조회
    int getListCount(MainSearchDTO mainSearchDTO);
    // 게시물 데이터 조회
    List<ListDTO> getList(MainSearchDTO mainSearchDTO);
    // 부가시설 조회
    List<String> getFacilities(MainSearchDTO mainSearchDTO);

    // 업소 기준 객실정보 조회
    List<RoomDTO> getRooms(int accommodationNo);

    // 객실 일반 최저가 조회
    Integer getMinBasicRate(List<Integer> roomNos);

    // 객실 추가요금 최저가 조회
    Integer getMinExtraRate(List<Integer> roomNos, SearchDTO searchDTO);
}
