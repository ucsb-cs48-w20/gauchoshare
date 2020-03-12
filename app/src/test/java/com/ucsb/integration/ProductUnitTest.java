package com.ucsb.integration;

import com.ucsb.integration.MainPage.Listing.Product;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProductUnitTest {
    @Test
    public void test_getCategory() {
        Product product = new Product("category", "createdBy", "description",
                "imageURL", "price", "title", true, "id");
        assertEquals("category", product.getCategory());
    }

    @Test
    public void test_getCreatedBy() {
        Product product = new Product("category", "createdBy", "description",
                "imageURL", "price", "title", true, "id");
        assertEquals("createdBy", product.getCreatedBy());
    }

    @Test
    public void test_getDescription() {
        Product product = new Product("category", "createdBy", "description",
                "imageURL", "price", "title", true, "id");
        assertEquals("description", product.getDescription());
    }

    @Test
    public void test_getImageURL() {
        Product product = new Product("category", "createdBy", "description",
                "imageURL", "price", "title", true, "id");
        assertEquals("imageURL", product.getImageURL());
    }

    @Test
    public void test_getPrice() {
        Product product = new Product("category", "createdBy", "description",
                "imageURL", "price", "title", true, "id");
        assertEquals("price", product.getPrice());
    }

    @Test
    public void test_getTitle() {
        Product product = new Product("category", "createdBy", "description",
                "imageURL", "price", "title", true, "id");
        assertEquals("title", product.getTitle());
    }

    @Test
    public void test_getSold() {
        Product product = new Product("category", "createdBy", "description",
                "imageURL", "price", "title", true, "id");
        assertEquals(true, product.getSold());
    }

    @Test
    public void test_getId() {
        Product product = new Product("category", "createdBy", "description",
                "imageURL", "price", "title", true, "id");
        assertEquals("id", product.getId());
    }

    @Test
    public void test_setCategory() {
        Product product = new Product();
        product.setCategory("category");
        assertEquals("category", product.getCategory());
    }

    @Test
    public void test_setCreatedBy() {
        Product product = new Product();
        product.setCreatedBy("createdBy");
        assertEquals("createdBy", product.getCreatedBy());
    }

    @Test
    public void test_setDescription() {
        Product product = new Product();
        product.setDescription("description");
        assertEquals("description", product.getDescription());
    }

    @Test
    public void test_setImageURL() {
        Product product = new Product();
        product.setImageURL("imageURL");
        assertEquals("imageURL", product.getImageURL());
    }

    @Test
    public void test_setPrice() {
        Product product = new Product();
        product.setPrice("price");
        assertEquals("price", product.getPrice());
    }

    @Test
    public void test_setTitle() {
        Product product = new Product();
        product.setTitle("title");
        assertEquals("title", product.getTitle());
    }

    @Test
    public void test_setSold() {
        Product product = new Product();
        product.setSold(true);
        assertEquals(true, product.getSold());
    }

    @Test
    public void test_setId() {
        Product product = new Product();
        product.setId("id");
        assertEquals("id", product.getId());
    }
}
