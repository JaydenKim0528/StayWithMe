package kr.co.swm.board.list.model.sevice;

import kr.co.swm.board.list.model.DTO.ListDTO;
import kr.co.swm.board.list.model.DTO.MainSearchDTO;
import kr.co.swm.board.list.model.DTO.PageInfoDTO;
import kr.co.swm.board.list.model.DTO.SearchDTO;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

public interface ListService {
    //장소 불러오기
//    List<ListDTO> getPlace(PageInfoDTO pi);
    List<ListDTO> getPlace(PageInfoDTO pi, SearchDTO searchDTO);
    // 게시글의 수
    int getTotalCount(SearchDTO searchDTO);
    // 별점 불러오기
    double getAvgRate(int boardNo);
    //  최저 기본 가격
    List<ListDTO> getCost();

    // 게시물 수 조회
    int getListCount(MainSearchDTO mainSearchDTO);
    // 게시물 데이터 조회
    List<ListDTO> getList(MainSearchDTO mainSearchDTO);
    // 부가시설 조회
    List<String> getFacilities(MainSearchDTO mainSearchDTO);


    // 기간 게시물 수 조회
    int DateListCount(MainSearchDTO mainDTO);
    // 기간 게시물 조회
    List<ListDTO> dateSearch(MainSearchDTO mainDTO);
    // 기간 부가시설 조회
    List<String> dateFacil(MainSearchDTO mainDTO);
}
