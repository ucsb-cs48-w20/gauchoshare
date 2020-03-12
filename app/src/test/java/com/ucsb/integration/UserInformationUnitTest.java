package com.ucsb.integration;

import com.ucsb.integration.MainPage.Profile.UserInformation;

import org.junit.Test;
import static org.junit.Assert.*;


public class UserInformationUnitTest {
    @Test
    public void test_getUsername() {
        UserInformation userInformation = new UserInformation("username", "fullname", "email", "number", "id", "imageURL", "venmo");
        assertEquals("username", userInformation.getUsername());
    }

    @Test
    public void test_getFullname() {
        UserInformation userInformation = new UserInformation("username", "fullname", "email", "number", "id", "imageURL", "venmo");
        assertEquals("fullname", userInformation.getFullname());
    }

    @Test
    public void test_getEmail() {
        UserInformation userInformation = new UserInformation("username", "fullname", "email", "number", "id", "imageURL", "venmo");
        assertEquals("email", userInformation.getEmail());
    }

    @Test
    public void test_getPhonenumber() {
        UserInformation userInformation = new UserInformation("username", "fullname", "email", "number", "id", "imageURL", "venmo");
        assertEquals("number", userInformation.getPhonenumber());
    }

    @Test
    public void test_getId() {
        UserInformation userInformation = new UserInformation("username", "fullname", "email", "number", "id", "imageURL", "venmo");
        assertEquals("id", userInformation.getId());
    }

    @Test
    public void test_getImageUrl() {
        UserInformation userInformation = new UserInformation("username", "fullname", "email", "number", "id", "imageURL", "venmo");
        assertEquals("imageURL", userInformation.getImageURL());
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
        userInformation.setImageURL("testImageUrl");
        assertEquals("testImageUrl", userInformation.getImageURL());
    }

    @Test
    public void test_setVenmo() {
        UserInformation userInformation = new UserInformation();
        userInformation.setVenmo("testVenmo");
        assertEquals("testVenmo", userInformation.getVenmo());
    }

    @Test
    public void test_compareTo1() {
        UserInformation user1 = new UserInformation();
        UserInformation user2 = new UserInformation();
        user1.setUsername("Kyle");
        user2.setUsername("Robert");
        int compare = user1.compareTo(user2);
        if (compare < 0) compare = -1;
        if (compare > 0) compare = 1;
        assertEquals(-1, compare);
    }

    @Test
    public void test_compareTo2() {
        UserInformation user1 = new UserInformation();
        UserInformation user2 = new UserInformation();
        user1.setUsername("Kyle");
        user2.setUsername("robert");
        int compare = user1.compareTo(user2);
        if (compare < 0) compare = -1;
        if (compare > 0) compare = 1;
        assertEquals(-1, compare);
    }

    @Test
    public void test_compareTo3() {
        UserInformation user1 = new UserInformation();
        UserInformation user2 = new UserInformation();
        user1.setUsername("Robert");
        user2.setUsername("Kyle");
        int compare = user1.compareTo(user2);
        if (compare < 0) compare = -1;
        if (compare > 0) compare = 1;
        assertEquals(1, compare);
    }

    @Test
    public void test_compareTo4() {
        UserInformation user1 = new UserInformation();
        UserInformation user2 = new UserInformation();
        user1.setUsername("Robert");
        user2.setUsername(null);
        int compare = user1.compareTo(user2);
        if (compare < 0) compare = -1;
        if (compare > 0) compare = 1;
        assertEquals(1, compare);
    }

    @Test
    public void test_compareTo5() {
        UserInformation user1 = new UserInformation();
        UserInformation user2 = new UserInformation();
        user1.setUsername(null);
        user2.setUsername("Robert");
        int compare = user1.compareTo(user2);
        if (compare < 0) compare = -1;
        if (compare > 0) compare = 1;
        assertEquals(-1, compare);
    }
}
