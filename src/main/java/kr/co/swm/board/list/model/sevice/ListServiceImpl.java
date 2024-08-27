package kr.co.swm.board.list.model.sevice;


import kr.co.swm.board.list.model.DTO.ListDTO;
import kr.co.swm.board.list.model.DTO.MainSearchDTO;
import kr.co.swm.board.list.model.DTO.PageInfoDTO;
import kr.co.swm.board.list.model.DTO.SearchDTO;
import kr.co.swm.board.mapper.ListMapper;
import okhttp3.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListServiceImpl implements ListService {

    //Mapper 의존성 주입
    private final ListMapper listMapper;

    @Autowired
    public ListServiceImpl(ListMapper listMapper){
        this.listMapper = listMapper;
    }

    //장소 불러오기
    @Override
    public List<ListDTO> getPlace(PageInfoDTO pi, SearchDTO searchDTO) {
        return listMapper.getPlace(pi, searchDTO);
    }

    //별점 불러오기
    @Override
    public double getAvgRate(int boardNo) {
        return listMapper.getAvgRate(boardNo);
    }

    //게시글의 수
    @Override
    public int getTotalCount(SearchDTO searchDTO){
        return listMapper.getTotalCount(searchDTO);
    }

    //최저 기본 가격
    @Override
    public List<ListDTO> getCost() {
        return listMapper.getCost();
    }

    @Override
    public int getListCount(MainSearchDTO mainSearchDTO) {
        return listMapper.getListCount(mainSearchDTO);
    }

    @Override
    public List<ListDTO> getList(MainSearchDTO mainSearchDTO) {

        List<ListDTO> getList = listMapper.getList(mainSearchDTO);
        for(ListDTO item : getList) {
            System.out.println("========== ServiceImpl Get List ==========");
            System.out.println("Board Name : " + item.getBoardName());
            System.out.println("Board Type : " + item.getBoardType());
            System.out.println("Board Address : " + item.getBoardAddress());
            System.out.println("Board Count : " + item.getBoardCount());
            System.out.println("==========================================");
        }

        return getList;
    }

    @Override
    public List<String> getFacilities(MainSearchDTO mainSearchDTO) {
        List<String> facilities = listMapper.getFacilities(mainSearchDTO);
        return facilities.stream().distinct().collect(Collectors.toList());
    }


    // 기간 게시물 수 조회
    @Override
    public int DateListCount(MainSearchDTO mainDTO) {
        return listMapper.DateListCount(mainDTO);
    }
    // 기간 게시물 조회
    @Override
    public List<ListDTO> dateSearch(MainSearchDTO mainDTO) {
        return listMapper.dateSearch(mainDTO);
    }
    // 기간 부가시설 조회
    @Override
    public List<String> dateFacil(MainSearchDTO mainDTO) {
        return listMapper.dateFacil(mainDTO);
    }

}
