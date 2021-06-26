package ru.job4j.cars.store;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.cars.model.*;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HbmStoreTest {

    private Store store;

    @Before
    public void setUp() {
        store = new HbmStore();
    }

    @Test
    public void findAll() {
        var body1 = new Body();
        body1.setName("Sedan");
        var body2 = new Body();
        body2.setName("Hatchback");
        var brand1 = new Brand("Toyota");
        var brand2 = new Brand("Audi");
        var color1 = new Color();
        color1.setName("Black");
        var color2 = new Color();
        color2.setName("White");
        var model1 = new Model("Avensis");
        model1.setBrand(brand1);
        var model2 = new Model("Camry");
        model2.setBrand(brand1);
        var model3 = new Model("A6");
        model3.setBrand(brand2);
        var model4 = new Model("Q5");
        model4.setBrand(brand2);
        var photo1 = new Photo();
        photo1.setName("Photo1");
        var photo2 = new Photo();
        photo2.setName("Photo2");
        var user1 = new User("User1", "email1", "pass1");
        var user2 = new User("User2", "email2", "pass2");

        var post1 = new Post();
        post1.setCreated(LocalDateTime.now());
        post1.setBody(body1);
        post1.setBrand(brand1);
        post1.setModel(model1);
        post1.setColor(color1);
        post1.setPhoto(photo1);
        post1.setUser(user1);

        var post2 = new Post();
        post2.setCreated(LocalDateTime.now());
        post2.setBody(body2);
        post2.setBrand(brand2);
        post2.setModel(model3);
        post2.setColor(color2);
        post2.setPhoto(photo2);
        post2.setUser(user2);

        store.save(post1);
        store.save(post2);
        var allPost = store.findAll(Post.class);
        assertThat(allPost.size(), is(2));
        var todayPost = store.findTodayPost();
        assertThat(todayPost.size(), is(2));
        var withPhoto = store.findPostWithPhoto();
        assertThat(withPhoto.size(), is(2));
        var withBrand = store.findPostWithBrand(brand1);
        assertThat(withBrand.size(), is(1));
    }
}