package com.adinstar.pangyo.service;

import com.adinstar.pangyo.mapper.TestMapper;
import com.adinstar.pangyo.model.Dummy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTestService {
    @Autowired
    private TestService testService;

    @MockBean
    private TestMapper testMapper;

    private Dummy dummy = new Dummy();

    @Before
    public void setup() {
        dummy.init();
    }

    @Test
    public void Test() {
        Assert.assertEquals(1, 1);
    }

    @Test
    public void Test2() {
        when(testMapper.select(any())).thenReturn(dummy.test);

        com.adinstar.pangyo.model.Test test = testService.get(1);

        Assert.assertEquals(dummy.test.getVal1(), test.getVal1());
        Assert.assertEquals(dummy.test.getVal2(), test.getVal2());
        Assert.assertEquals(dummy.test.getVal3(), test.getVal3());
    }
}
