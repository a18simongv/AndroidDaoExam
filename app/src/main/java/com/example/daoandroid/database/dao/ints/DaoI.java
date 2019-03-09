package com.example.daoandroid.database.dao.ints;

import java.util.List;

public interface DaoI<T,U> {

    T getById(U id);
    List<T> listAll();

    boolean insert(T element);
}
