package com.mewocz.storeeverything.repository;

import com.mewocz.storeeverything.model.Information;
import com.mewocz.storeeverything.model.SharedInformations;
import com.mewocz.storeeverything.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SharedInformationsRepository extends JpaRepository<SharedInformations, Long> {

    public List<SharedInformations> findAllBySharedUser(User user);
}
