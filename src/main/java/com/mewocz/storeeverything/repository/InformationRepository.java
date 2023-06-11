package com.mewocz.storeeverything.repository;

import com.mewocz.storeeverything.model.Category;
import com.mewocz.storeeverything.model.Information;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface InformationRepository extends CrudRepository<Information, Long>{

    public List<Information> findAllByUser_Id(Long id);



    public List<Information> findAllByUser_Id(Long id, Sort sort);


    public List<Information> findAllByCategory(Category category);

    public Information findByUuid(String uuid);


    @Query("SELECT i FROM Information i WHERE i.reminderDate = :reminderDate AND i.user.id = :userId")
    List<Information> findByReminderDateAndUserId(LocalDate reminderDate, Long userId);
}
