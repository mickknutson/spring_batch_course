package io.baselogic.batch;

import io.baselogic.batch.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {


    @Test
	public void contextLoads() {
        Application.main(new String[0]);
	}

} // The End...
