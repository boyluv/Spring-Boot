package com.example.greeting.Service;

import com.example.greeting.MyObject.Phone;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class PhoneService {
    private static final AtomicLong counter = new AtomicLong();
    private static List<Phone> phones;

    public PhoneService() {
        phones = populateDummyPhones();
    }

    private static List<Phone> populateDummyPhones() {

        List<Phone> phoneList = new ArrayList<Phone>();

        phoneList.add(new Phone(counter.incrementAndGet(),"Oppo","red","android"));
        phoneList.add(new Phone(counter.incrementAndGet(),"Nokia","green","window"));
        phoneList.add(new Phone(counter.incrementAndGet(),"Apple","blue","ios"));

        return  phoneList;
    }

    public List<Phone> getAllPhones() {
        // Return list all phones
        return phones;
    }

    public Phone findById(long id){
        for (Phone phone: phones){
            if(phone.getId() == id){
                return phone;
            }
        }
        return null;
    }

    public Phone findByName(String name){
        for (Phone phone: phones){
            if(phone.getName().equalsIgnoreCase(name)){
                return phone;
            }
        }
        return null;
    }

    public boolean isPhoneExist(Phone phone){
        return findByName(phone.getName()) != null;
    }

    public void addNewPhone(Phone phone){
        phone.setId(counter.incrementAndGet());
        phones.add(phone);
    }

    public void updatePhone(Phone phone){
        int index = phones.indexOf(phone);
        phones.set(index,phone);

    }

    public void deletePhoneWithId(long id){
        for (Phone item : phones){
            if(item.getId() == id){
                phones.remove(item);
                return;
            }
        }
    }

    public void deleteAll(){
        phones.clear();
    }
}
