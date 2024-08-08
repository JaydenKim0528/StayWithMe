package kr.co.swm.testMvc.service;

import kr.co.swm.testMvc.TestDto;
import java.util.List;



public interface TestService {

    List<TestDto> selectAll();
}
