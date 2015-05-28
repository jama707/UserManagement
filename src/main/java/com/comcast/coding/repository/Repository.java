package com.comcast.coding.repository;

import com.comcast.coding.entity.User;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jama Asatillayev on 5/27/2015.
 */
public interface Repository<T extends Serializable> {

    boolean delete(Long t);

    T get(Long t);

    User save(T t);

    List<T> paging(int page, int limit);

    void deleteAll();
}
