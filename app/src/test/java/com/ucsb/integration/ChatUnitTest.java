package com.ucsb.integration;

import com.ucsb.integration.MainPage.Message.Chat;

import org.junit.Test;
import static org.junit.Assert.*;

public class ChatUnitTest {
    @Test
    public void test_getSender() {
        Chat chat = new Chat("sender","receiver", "message");
        assertEquals("sender", chat.getSender());
    }

    @Test
    public void test_getReceiver() {
        Chat chat = new Chat("sender","receiver", "message");
        assertEquals("receiver", chat.getReceiver());
    }

    @Test
    public void test_getMessage() {
        Chat chat = new Chat("sender","receiver", "message");
        assertEquals("message", chat.getMessage());
    }

    @Test
    public void test_setSender() {
        Chat chat = new Chat();
        chat.setSender("testSender");
        assertEquals("testSender", chat.getSender());
    }

    @Test
    public void test_setReceiver() {
        Chat chat = new Chat();
        chat.setReceiver("testReceiver");
        assertEquals("testReceiver", chat.getReceiver());
    }

    @Test
    public void test_setMessage() {
        Chat chat = new Chat();
        chat.setMessage("testMessage");
        assertEquals("testMessage", chat.getMessage());
    }
}
