import java.util.HashMap;

public class MapTest {
    public static void main(String[] args) {
        HashMap<String, ObjectFactory<User>> map = new HashMap<>();
        map.put("A",() -> new UserFactory().getUser());
        System.out.println("map塞值完成了");
        ObjectFactory<User> a = map.get("A");
        a.getObject();

    }
}


class UserFactory  {
    public User getUser(){
        return new User();
    }
}

class User{
    public User() {
        System.out.println("用户初始化已经完成了");
    }
}
