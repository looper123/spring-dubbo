import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by zebon lu on 2017/5/20.
 */
public class HelloTest extends BaseSpringTestCase {



    @Test
    public void testFindMessageDetail() {
        try {
//            MvcResult andReturn = this.mockMvc.perform((post("/hello/say.action").param("messageId", "27504555574347153247458658352")).accept(MediaType.parseMediaType("application/json;charset=UTF-8"))).andReturn();
            MvcResult andReturn = this.mockMvc.perform((post("/hello/say.action")).accept(MediaType.parseMediaType("application/json;charset=UTF-8"))).andReturn();
            if (andReturn.getResponse().getStatus() == 200) {
                    String contentAsString = andReturn.getResponse().getContentAsString();
                LOGGER.info("请求成功,返回结果为{}", contentAsString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
