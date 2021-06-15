package ru.job4j.cars.store;

import ru.job4j.cars.model.Brand;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;

import java.util.List;

public interface Store {

    List<Post> findAllPost();

    List<Post> findTodayPost();

    List<Post> findPostWithPhoto();

    List<Post> findPostWithBrand(Brand brand);

    Post findPostById(int id);

    boolean save(Post post);

    boolean delete(int id);

    List<User> findAllUser();

    User findUserByEmail(String email);

    boolean save(User user);
}
