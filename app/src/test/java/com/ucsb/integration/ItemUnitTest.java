package com.ucsb.integration;

import com.ucsb.integration.MainPage.Find.Item;

import org.junit.Test;
import static org.junit.Assert.*;


public class ItemUnitTest {
    @Test
    public void test_getName() {
        Item item = new Item("name", 24, 30);
        assertEquals("name", item.getName());
    }

    @Test
    public void test_getPhoto() {
        Item item = new Item("name", 24, 30);
        assertEquals(24, item.getPhoto());
    }

    @Test
    public void test_getPrice() {
        Item item = new Item("name", 24, 30);
        assertEquals(30, item.getPrice());
    }

    @Test
    public void test_setName() {
        Item item = new Item();
        item.setName("testName");
        assertEquals("testName", item.getName());
    }

    @Test
    public void test_setPhoto() {
        Item item = new Item();
        item.setPhoto(10);
        assertEquals(10, item.getPhoto());
    }

    @Test
    public void test_setPrice() {
        Item item = new Item();
        item.setPrice(40);
        assertEquals(40, item.getPrice());
    }
}
