package kr.co.swm.testMvc.service;



import kr.co.swm.testMvc.TestDto;
import kr.co.swm.testMvc.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {


    private final TestMapper testMapper;

    @Autowired
    public TestServiceImpl(TestMapper testMapper) {
        this.testMapper = testMapper;
    }

    @Override
    public List<TestDto> selectAll() {
        return testMapper.selectAll();
    }
}
