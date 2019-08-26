package com.winning.devops.start;

import com.winning.devops.start.component.PropsComponent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootStartApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private PropsComponent propsComponent;

    @Test
    public void testProps() {
        System.out.println(propsComponent.getName());
        System.out.println(propsComponent.getUrl());
        System.out.println(propsComponent.getDesc());
    }

}

