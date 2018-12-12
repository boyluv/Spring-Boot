package com.example.greeting.Service;

import com.example.greeting.MyObject.Phone;
import com.example.greeting.MyObject.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PhoneService {
    private static final AtomicLong counter = new AtomicLong();
    private static List<Phone> phones;

    @Autowired
    private PhoneRepository repository;


    public PhoneService() {
        phones = populateDummyPhones();
    }

    private static List<Phone> populateDummyPhones() {

        List<Phone> phoneList = new ArrayList<Phone>();
//
//        phoneList.add(new Phone(counter.incrementAndGet(),"Oppo","red","android"));
//        phoneList.add(new Phone(counter.incrementAndGet(),"Nokia","green","window"));
//        phoneList.add(new Phone(counter.incrementAndGet(),"Apple","blue","ios"));

        return  phoneList;
    }

    public List<Phone> getAllPhones() {
        // Return list all phones
        if(repository.findAll().isEmpty())
            return null;
        return repository.findAll();
    }

    public Phone getPhoneWithId(Long id) {
        if(!repository.findById(id).isPresent())
            return null;
        else
            return repository.findById(id).get();
    }

    public void createNewPhone(Phone phone) {
        phone.id = counter.incrementAndGet();
        repository.save(phone);
    }
    public Phone updatePhone(Phone curPhone,Phone phone) {

        curPhone.name = phone.name;
        curPhone.detailPhone.color = phone.detailPhone.color;
        curPhone.detailPhone.os = phone.detailPhone.os;

        repository.save(curPhone);
        return curPhone;
    }

    public Boolean deletePhoneWithId(Long id) {
        if (!repository.findById(id).isPresent()) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }

    public void deleteAllPhone() {
        repository.deleteAll();
    }


    public Phone findByName(String name){
        for (Phone phone: phones){
            if(phone.getName().equalsIgnoreCase(name)){
                return phone;
            }
        }
        return null;
    }
}
