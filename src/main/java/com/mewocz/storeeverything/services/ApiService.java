package com.mewocz.storeeverything.services;

import com.mewocz.storeeverything.repository.ApiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiService {

    private final ApiRepository apiRepository;

    @Autowired
    public ApiService(ApiRepository apiRepository){
        this.apiRepository = apiRepository;
    }

    public boolean isANoun(String word){
        return apiRepository.isANoun(word);
    }
}
