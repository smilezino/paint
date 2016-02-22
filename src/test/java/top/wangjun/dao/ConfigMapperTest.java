package top.wangjun.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.wangjun.model.Config;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-test-mapper.xml")
public class ConfigMapperTest {

    @Resource
    private ConfigMapper mapper;

    @Test
    public void testInsert() throws Exception {
        Config config = new Config();
        config.setKey("key");
        config.setValue("value");
        mapper.insert(config);

    }
}