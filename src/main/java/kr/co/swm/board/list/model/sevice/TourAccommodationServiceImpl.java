package kr.co.swm.board.list.model.sevice;


import kr.co.swm.board.list.model.DTO.*;
import kr.co.swm.board.mapper.ListMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TourAccommodationServiceImpl implements TourAccommodationService {
    private final ListMapper listMapper;

    @Override
    public List<ListDTO> getAccommodation(PageInfoDTO pi, SearchDTO searchDTO) {
        return listMapper.getPlace(pi, searchDTO);
    }

    //게시글의 수
    @Override
    public int getTotalCount(SearchDTO searchDTO){
        return listMapper.getTotalCount(searchDTO);
    }

    // 부대시설 불러오기
    @Override
    public List<String> getUniqueFacilities() {
        return listMapper.getUniqueFacilities();
    }

    @Override
    public int getListCount(MainSearchDTO mainSearchDTO) {

        System.out.println("<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>");
        System.out.println("Main Search : " + mainSearchDTO.getMainSearch());
        System.out.println("BoardType : " + mainSearchDTO.getBoardType());
        System.out.println("<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>");

        int listCount = listMapper.getListCount(mainSearchDTO);

        System.out.println("========== ServiceImpl List Count ==========");
        System.out.println(listCount);
        System.out.println("==========================================");
        return listCount;
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

    @Override
    public List<RoomDTO> getRooms(int accommodationNo) {
        return listMapper.getRooms(accommodationNo);
    }

    @Override
    public Integer getMinBasicRate(List<Integer> roomNos) {
        return listMapper.getMinBasicRate(roomNos);
    }

    @Override
    public Integer getMinExtraRate(List<Integer> roomNos, SearchDTO searchDTO) {
        return listMapper.getMinExtraRate(roomNos, searchDTO);
    }
}
