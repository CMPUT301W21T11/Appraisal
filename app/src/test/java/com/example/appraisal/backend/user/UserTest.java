package com.example.appraisal.backend.user;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserTest {
    @Before
    public void init() {
    }

    @Test
    public void getIDTest() {
        User user = new User("123", "testUsername", "email", "789");
        Assert.assertEquals("123", user.getId());
    }

    @Test
    public void getUserNameTest() {
        User user = new User("123", "testUsername", "email", "789");
        Assert.assertEquals("testUsername", user.getUsername());
    }

    @Test
    public void getEmailTest() {
        User user = new User("123", "testUsername", "email", "789");
        Assert.assertEquals("email", user.getEmail());
    }

    @Test
    public void getPhoneTest() {
        User user = new User("123", "testUsername", "email", "789");
        Assert.assertEquals("789", user.getPhoneNumber());
    }

    @After
    public void end() {
    }
}
