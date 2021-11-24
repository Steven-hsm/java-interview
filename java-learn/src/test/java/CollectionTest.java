import com.hsm.java.stream.User;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class CollectionTest {
    List<User> userList = new ArrayList<>();
    List<String> strList = new ArrayList<>();
    @Before
    public void initList() {
        userList.add(new User("张三", 30));
        userList.add(new User("李四", 20));
        userList.add(new User("王五", 26));
        userList.add(new User("赵六", 27));
        userList.add(new User("何七", 27));
        strList.add("张三");
        strList.add("李四");
        strList.add("王五");
        strList.add("何七");
    }


    @Test
    public void countTest() {
        userList.size();
    }

    @Test
    public void joinTest() {
        System.out.println(String.join(",",strList));
    }

    @Test
    public void joinTest2() {
        System.out.println(Arrays.asList(String.join(",",strList).split(",")));
    }


    @Test
    public void hashMapTest() {
        int total = 8;
        Random random = new Random();
        long startTime = System.currentTimeMillis();
        HashMap<String, String> dataMap = new HashMap<>();
        for (int i = 0; i < total; i++) {
            dataMap.put(random.nextInt(1000000)+"key",random.nextInt(1000000)+"value");
        }
        System.out.println("总时间：" + (System.currentTimeMillis() - startTime));

        long startTime2 = System.currentTimeMillis();
        HashMap<String, String> dataMap2 = new HashMap<>(total);
        for (int i = 0; i < total; i++) {
            dataMap2.put(random.nextInt(1000000)+"key",random.nextInt(1000000)+"value");
        }
        System.out.println("总时间：" + (System.currentTimeMillis() - startTime2));

    }

}



