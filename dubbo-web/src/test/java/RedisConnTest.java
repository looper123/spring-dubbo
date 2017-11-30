import com.itcast.dubbo.web.cache.RedisStringCache;
import com.itcast.dubbo.web.entity.School;
import org.junit.Test;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by zebon lu on 2017/6/13.
 */
public class RedisConnTest extends BaseSpringTestCase {

    @Resource(name = "redisStringCache")
    private RedisStringCache redisStringCache;


    @Test
    public void test3() {
        List<List<School>> outList = new ArrayList<>();
        List<School> innerList1 = new ArrayList<>();
        List<School> innerList2 = new ArrayList<>();
        innerList1.add(new School("清华1", "计算机", "高", new Date()));
        innerList1.add( new School("清华2", "计算机", "高", new Date()));
        innerList2.add( new School("清华3", "计算机", "高", new Date()));
        innerList2.add( new School("清华4", "计算机", "高", new Date()));
        outList.add(innerList1);
        outList.add(innerList2);
        redisStringCache.set("com.gongren.zw.school.list", outList);
    }


    @Test
    public void test4() {
        redisStringCache.setIfAbsent("lzp1", "lzp1");
    }

    @Test
    public void test6() {
        redisStringCache.setWithExpire("lzp1", "lzp20", 1000);
    }


    @Test
    public void test5() {
        Map<String, Object> map = new HashMap<>();
        map.put("abc", "abc");
        map.put("adc", "adc");
        map.put("acc", "acc");
        map.put("aac", "aac");
        redisStringCache.mSetWithPipelined(map);
    }

    @Test
    public void test7() {
        Map<String, Object> map = new HashMap<>();
        map.put("com.gongren.odd.2", "abc");
        map.put("com.gongren.odd.1", "adc");
        map.put("com.gongren.odd.3", "acc");
        map.put("com.gongren.odd.4", "aac");
        redisStringCache.msetWithPipelinedAndExpire(map, 20000);
    }

    @Test
    public void test8() {
//        List<School> values =  (List<School>)redisStringCache.getValue("com.gongren.zw.school.list");
        List<List<School>> values =  (List<List<School>>)redisStringCache.getValue("com.gongren.zw.school.list");
        for(int i=0;i<values.size();i++){
            System.out.println("第"+(i+1)+"次循环");
            for (School value :values.get(i)){
                System.out.println("-------------"+value);
            }
        }
    }


    @Test
    public void test1() {
        redisStringCache.delete("lzp1");
    }

    @Test
    public void test2() {
        redisStringCache.mDelete("a");
    }

    @Test
    public void test10() {
        long expire = redisStringCache.getExpire("lzp1");
        System.out.println("==========" + expire);
    }

    @Test
    public void test11() {
        redisStringCache.flushAll();
    }

    @Test
    public void test12() {
        redisStringCache.flushDB();
    }

}
