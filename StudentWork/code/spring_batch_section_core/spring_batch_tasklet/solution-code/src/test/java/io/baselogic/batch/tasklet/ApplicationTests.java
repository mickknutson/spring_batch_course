package io.baselogic.batch.tasklet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    //@SuppressWarnings("SpringJavaAutowiringInspection")
    //@SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
//    @Autowired
//    private MockMvc mockMvc;

    @Test
	public void contextLoads() {
        Application.main(new String[0]);
	}

}
