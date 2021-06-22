package ru.job4j.cars.store;

import ru.job4j.cars.model.*;

import java.util.List;

public interface Store {

    <T> boolean save(T model);

    <T> boolean update(T model);

    <T> boolean delete(T model);

    <T> List<T> findAll(Class<T> cl);

    <T> T findById(Class<T> cl, Integer id);

    List<Post> findTodayPost();

    List<Post> findPostWithPhoto();

    List<Post> findPostWithBrand(Brand brand);

    User findUserByEmail(String email);
}
