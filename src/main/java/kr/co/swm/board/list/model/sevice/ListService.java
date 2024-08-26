package kr.co.swm.board.list.model.sevice;

import kr.co.swm.board.list.model.DTO.ListDTO;
import kr.co.swm.board.list.model.DTO.PageInfoDTO;
import kr.co.swm.board.list.model.DTO.SearchDTO;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

public interface ListService {
    //장소 불러오기
    List<ListDTO> getPlace(PageInfoDTO pi, SearchDTO searchDTO);
    // 게시글의 수
    int getTotalCount(SearchDTO searchDTO);
    //  최저 기본 가격
    List<ListDTO> getCost();

    //  체크인 & 체크아웃 지정할 때 나오는 리스트
    List<ListDTO> getCheck(String checkinDate, String checkoutDate);




}
