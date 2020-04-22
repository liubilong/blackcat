package com.sileton.blackcat.repository;

import java.util.List;

import com.sileton.blackcat.model.CatItem;

public interface CatRepository {
    List<CatItem> findAll();
    CatItem findbyid(Long id);
    Long insert(CatItem item);
    void update(CatItem item);
    void delete(CatItem item);
}
