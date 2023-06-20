package com.njupt.bean;

public class UserService {

    private String name;

    private String password;

    private String id;

    private UserDao userDao;

    public UserService(String name) {
        this.name = name;
    }

    public void queryUserInfo(String uId) {
        System.out.println(userDao.queryUserName(uId));
    }

    @Override
    public String toString() {
        return name + ":" + password + ":" + id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}