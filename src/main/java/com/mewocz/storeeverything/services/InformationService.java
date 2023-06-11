package com.mewocz.storeeverything.services;

import com.mewocz.storeeverything.model.Category;
import com.mewocz.storeeverything.model.Information;
import com.mewocz.storeeverything.model.SharedInformations;
import com.mewocz.storeeverything.model.User;
import com.mewocz.storeeverything.repository.InformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service

public class InformationService {

    private final InformationRepository informationRepository;

    private final CategoryService categoryService;
    private final UserService userService;
    private final SharedInformationsService sharedInformationsService;

    @Autowired
    public InformationService(InformationRepository informationRepository, CategoryService categoryService, UserServiceImpl userService, SharedInformationsService sharedInformationsService) {
        this.informationRepository = informationRepository;
        this.categoryService = categoryService;
        this.userService = userService;
        this.sharedInformationsService = sharedInformationsService;
    }

    public List<Information> findAllByUserId(long userId, String sortField, String sortDirection) {

        Sort sort;
        Sort.Order order;
        if (sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())) order = new Sort.Order(Sort.Direction.ASC, sortField).ignoreCase();
        else order = new Sort.Order(Sort.Direction.DESC, sortField).ignoreCase();

        sort=Sort.by(order);

        if(sortField == "categoryPopularity"){

        }

        return informationRepository.findAllByUser_Id(userId, sort);
    }

    public List<Information> getInformationWithTodaysReminderDate(Long userId) {
        LocalDate reminderDate = LocalDate.now();
        return informationRepository.findByReminderDateAndUserId(reminderDate, userId);
    }

    public void shareNote(Long id, User user){
        Information information = getInformationById(id);
        SharedInformations newShare = new SharedInformations(0, information, user);
        sharedInformationsService.addNewSharedInformation(newShare);
    }

    public List<Information> getInformationByPopularityForUser(Long userId) {

        List<Information> informationList = informationRepository.findAllByUser_Id(userId);

        Map<Category, Integer> categoryCountMap = new HashMap<>();

        for (Information information : informationList) {
            Category category = information.getCategory();
            categoryCountMap.put(category, categoryCountMap.getOrDefault(category, 0) + 1);
        }

        // Znajdź kategorię z największą liczbą wystąpień
        int maxCount = 0;
        Category mostFrequentCategory = null;
        for (Map.Entry<Category, Integer> entry : categoryCountMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostFrequentCategory = entry.getKey();
            }
        }

        return informationRepository.findAllByCategory(mostFrequentCategory);
    }

    public void save(Information theInformation) throws ParseException {
        informationRepository.save(theInformation);
    }

    public void generateUUID(Long id){
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();

        Information information = informationRepository.findById(id).get();
        information.setUuid(uuidAsString);

        informationRepository.save(information);
    }

    public Information getInformationByUUID(String uuid){
        return informationRepository.findByUuid(uuid);
    }

//    public Information saveInformation(InformationRequest informationRequest){
//        if(informationRequest.getLink().isEmpty()){
//            informationRequest.setLink(null);
//        }
//
//        if(informationRequest.getReminderDate().isEmpty()){
//            informationRequest.setReminderDate(null);
//        }
//
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//        Date time = new Date();
//        String date = formatter.format(time);
//
//        informationRequest.setCreationDate(date);
//
//        Category category = categoryService.getCategoryById(informationRequest.getCategory()).orElse(null);
//
//        Information information = Information.build(
//                0L,
//                category,
//                informationRequest.getTitle(),
//                informationRequest.getContent(),
//                informationRequest.getLink(),
//                informationRequest.getCreationDate(),
//                informationRequest.getReminderDate(),
//                0
//        );
//
//        return informationRepository.save(information);
//    }

    public ArrayList<Information> getInformations()
    {
        return (ArrayList<Information>) informationRepository.findAll();
    }

    public void deleteInformation(String id){
        informationRepository.deleteById(Long.valueOf(id));
    }

    public void deleteInformation(Long id){
        informationRepository.deleteById(id);
    }

    public Information getInformationById(Long id) {
        return informationRepository.findById(id).orElse(null);
    }

//    public Information updateInformation(InformationRequest informationRequest, Long id) {
//        Information information = getInformationById(id);
//        Category category = categoryService.getCategoryById(informationRequest.getCategory()).orElse(null);
//
//        information.setTitle(informationRequest.getTitle());
//        information.setContent(informationRequest.getContent());
//        information.setLink(informationRequest.getLink());
//        information.setReminderDate(informationRequest.getReminderDate());
//        information.setCategory(category);
//
//        return informationRepository.save(information);
//    }

    public List<Information> getInformationsSortedByIdAsc() {
        return (ArrayList<Information>) informationRepository.findAll();

    }

    public List<Information> getInformationsSortedByIdDesc() {
        ArrayList<Information> lista = (ArrayList<Information>) informationRepository.findAll();
        Collections.sort(lista, new Comparator<Information>() {
            @Override
            public int compare(Information o1, Information o2) {
                return o2.getId().compareTo(o1.getId());
            }
        });

        return lista;
    }

    public void updateInformation(Information information) {
        informationRepository.save(information);
    }
}
