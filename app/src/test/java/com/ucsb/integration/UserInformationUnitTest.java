package com.ucsb.integration;

import com.ucsb.integration.MainPage.Profile.UserInformation;

import org.junit.Test;
import static org.junit.Assert.*;


public class UserInformationUnitTest {
    @Test
    public void test_getUsername() {
        UserInformation userInformation = new UserInformation("username", "fullname", "email", "number", "id", "imageUrl");
        assertEquals("username", userInformation.getUsername());
    }

    @Test
    public void test_getFullname() {
        UserInformation userInformation = new UserInformation("username", "fullname", "email", "number", "id", "imageUrl");
        assertEquals("fullname", userInformation.getFullname());
    }

    @Test
    public void test_getEmail() {
        UserInformation userInformation = new UserInformation("username", "fullname", "email", "number", "id", "imageUrl");
        assertEquals("email", userInformation.getEmail());
    }

    @Test
    public void test_getPhonenumber() {
        UserInformation userInformation = new UserInformation("username", "fullname", "email", "number", "id", "imageUrl");
        assertEquals("number", userInformation.getPhonenumber());
    }

    @Test
    public void test_getId() {
        UserInformation userInformation = new UserInformation("username", "fullname", "email", "number", "id", "imageUrl");
        assertEquals("id", userInformation.getId());
    }

    @Test
    public void test_getImageUrl() {
        UserInformation userInformation = new UserInformation("username", "fullname", "email", "number", "id", "imageUrl");
        assertEquals("imageUrl", userInformation.getImageUrl());
    }

    @Test
    public void test_setUsername() {
        UserInformation userInformation = new UserInformation();
        userInformation.setUsername("testUsername");
        assertEquals("testUsername", userInformation.getUsername());
    }

    @Test
    public void test_setFullname() {
        UserInformation userInformation = new UserInformation();
        userInformation.setFullname("testFullname");
        assertEquals("testFullname", userInformation.getFullname());
    }

    @Test
    public void test_setEmail() {
        UserInformation userInformation = new UserInformation();
        userInformation.setEmail("testEmail");
        assertEquals("testEmail", userInformation.getEmail());
    }

    @Test
    public void test_setPhonenumber() {
        UserInformation userInformation = new UserInformation();
        userInformation.setPhonenumber("testPhonenumber");
        assertEquals("testPhonenumber", userInformation.getPhonenumber());
    }

    @Test
    public void test_setId() {
        UserInformation userInformation = new UserInformation();
        userInformation.setId("testId");
        assertEquals("testId", userInformation.getId());
    }

    @Test
    public void test_setImageUrl() {
        UserInformation userInformation = new UserInformation();
        userInformation.setImageUrl("testImageUrl");
        assertEquals("testImageUrl", userInformation.getImageUrl());
    }
}
