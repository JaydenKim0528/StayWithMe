package kr.co.swm.board.list.model.sevice;


import kr.co.swm.board.list.model.dto.ListDto;
import kr.co.swm.board.list.model.dto.PageInfoDto;
import kr.co.swm.board.mapper.ListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
      public List<ListDto> getPlace(PageInfoDto pi){
        List<ListDto> place = listMapper.getPlace(pi);
          return listMapper.getPlace(pi);
      }

    //별점 불러오기
        @Override
        public double getAvgRate(int boardNo) {
        return listMapper.getAvgRate(boardNo);
    }

      @Override
      public int getTotalCount(){
          return listMapper.getTotalCount();
      }

}
