package com.example.a3;

public class User {
    public String username;
    //String email;
    public String id;
    //String password;
    public User()
    {

    }
    public User(String name,String id)
    {
        this.username=name;
        //this.email=email;
        //this.password=password;
        this.id=id;
    }
    public void setName(String name)
    {
        this.username=name;
    }
    public void setId(String id)
    {
        this.id=id;
    }
    public String getName()
    {
        return username;
    }
    public String getId()
    {
        return id;
    }
}
