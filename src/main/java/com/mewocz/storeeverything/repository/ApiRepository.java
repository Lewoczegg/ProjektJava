package com.mewocz.storeeverything.repository;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

@Repository
public class ApiRepository {
    private Set<String> words;

    public ApiRepository(){
        words = new HashSet<String>();
        try {
            ClassPathResource file = new ClassPathResource("words.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String line;
            while((line = reader.readLine()) != null){
                words.add(line.trim().toLowerCase());
            }
            reader.close();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public boolean isANoun(String word){
        return words.contains(word);
    }


}
