package com.mewocz.storeeverything.repository;

import com.mewocz.storeeverything.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
