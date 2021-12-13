package com.hsm.java.threadlocal;

public class UserContext {
    private static  ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        UserContext.setUser(new User("hsm"));
        System.out.println(UserContext.getUser());
    }

    private static void setUser(User hsm) {
        userThreadLocal.set(hsm);
    }

    private static User getUser() {
        return userThreadLocal.get();
    }
}
