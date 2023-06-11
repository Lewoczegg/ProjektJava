package com.mewocz.storeeverything.services;

import com.mewocz.storeeverything.model.Information;
import com.mewocz.storeeverything.model.SharedInformations;
import com.mewocz.storeeverything.model.User;
import com.mewocz.storeeverything.repository.SharedInformationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SharedInformationsService {

    private SharedInformationsRepository sharedInformationsRepository;

    @Autowired
    public SharedInformationsService(SharedInformationsRepository sharedInformationsRepository) {
        this.sharedInformationsRepository = sharedInformationsRepository;
    }

    public ArrayList<Information> getSharedInformationsForUser(User user){
        ArrayList<Information> result = new ArrayList<>();
        for(SharedInformations share : sharedInformationsRepository.findAllBySharedUser(user)){
            result.add(share.getSource());
        }
        return result;
    }

    public void addNewSharedInformation(SharedInformations newShare){
        sharedInformationsRepository.save(newShare);
    }
}
