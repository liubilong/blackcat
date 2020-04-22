package com.sileton.blackcat.repository;

import com.sileton.blackcat.model.CatItem;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryCatRespository implements CatRepository {
    private final AtomicLong currentID = new AtomicLong();
    private ConcurrentMap<Long,CatItem> cats = new ConcurrentHashMap<Long, CatItem>() ;

    @Override
    public List<CatItem> findAll() {
        List<CatItem> items = new ArrayList<CatItem>(cats.values());
        Collections.sort(items);
        return items;
    }

    @Override
    public CatItem findbyid(Long id) {
        return cats.get(id);
    }

    @Override
    public Long insert(CatItem item) {
        Long id = currentID.incrementAndGet();
        item.setId(id);
        cats.putIfAbsent(id,item);
        return id;
    }

    @Override
    public void update(CatItem item) {
        cats.replace(item.getId(),item);
    }

    @Override
    public void delete(CatItem item) {
        cats.remove(item.getId());
    }
}
