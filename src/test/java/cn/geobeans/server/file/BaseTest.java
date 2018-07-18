package cn.geobeans.server.file;

import com.alibaba.fastjson.JSON;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class BaseTest {

    private long before;

    @Before
    public void before(){
        before = System.currentTimeMillis();
    }

    @After
    public void after(){
        System.out.println("######### 耗时："+(System.currentTimeMillis() - before)+" ms ##########");
    }

    protected void printJson(Object item){
        System.out.println(JSON.toJSONString(item,true));
    }
}
