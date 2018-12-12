package com.example.greeting;

import com.example.greeting.MyObject.Phone;
import com.example.greeting.MyObject.PhoneRepository;
import com.example.greeting.Utils.CustomErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private PhoneRepository repository;


    // Get all Phones
    @RequestMapping(value = "/phones",method = RequestMethod.GET)
    public List<Phone> getAllPhone(){
        System.out.println("Get all Phones");
        return repository.findAll();
    }

    // Get Single Phone
    @RequestMapping(value = "/phones/{id}",method = RequestMethod.GET)
    public ResponseEntity<?> getPhone(@PathVariable("id") long id){
        if(!repository.findById(id).isPresent()){
            System.out.println("can 't find" + id);
            return new ResponseEntity<>(new CustomErrorType("Phone with id " + id + " not found"),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Phone>(repository.findById(id).get(),HttpStatus.OK);
    }

    // Create Phone
    @RequestMapping(value = "/phones/",method = RequestMethod.POST)
    public ResponseEntity<?> createPhone(@RequestBody Phone phone, UriComponentsBuilder ucBuilder){
//        // New version
        System.out.println("Create new phone");
        phone.id = counter.incrementAndGet();
        repository.save(phone);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/phones/{id}").buildAndExpand(phone.getId()).toUri());

        return new ResponseEntity<String>(headers,HttpStatus.CREATED);
    }

    // Update Phone
    @RequestMapping(value = "/phones/{id}",method = RequestMethod.PUT)
    public ResponseEntity<?> updatePhones(@PathVariable("id") long id,@RequestBody Phone phone){
        // New version
        System.out.println("Update phone with id");

        if(!repository.findById(phone.id).isPresent()){
            return new ResponseEntity<>(new CustomErrorType("Can not find phone with id " + id),HttpStatus.NOT_FOUND);
        }

        Phone curPhone = repository.findById(id).get();
        curPhone.setName(phone.getName());
        curPhone.setDetailPhone(phone.getDetailPhone().getColor(),phone.getDetailPhone().getOs());

        repository.save(curPhone);

        return new ResponseEntity<Phone>(curPhone,HttpStatus.OK);

    }
    // Delete Phone
    @RequestMapping(value = "/phones/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePhone(@PathVariable("id") long id){
        //  New version
        System.out.println("Delete phone with id");

        if(!repository.findById(id).isPresent()){
            return new ResponseEntity<>(new CustomErrorType("Can not find phone with id " + id),HttpStatus.NOT_FOUND);
        }
        //New version
        repository.deleteById(id);
        return new ResponseEntity<Phone>(HttpStatus.NO_CONTENT);
    }

    // Delete All Phone
    @RequestMapping(value = "/phones/",method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAllPhone(){
        System.out.println("Delete all phones");
        //New version
        repository.deleteAll();
        return new ResponseEntity<Phone>(HttpStatus.NO_CONTENT);
    }

    // Get all Phones from anotherserver
    @RequestMapping(value = "/another/phones",method = RequestMethod.GET)
    public ResponseEntity<?>  getAllAnotherPhone(){
        System.out.println("Get phones from another web service");
        String REST_SERVICE_URI = "http://localhost:9090";
        RestTemplate restTemplate = new RestTemplate();
        List<Phone> phoneMap = restTemplate.getForObject(REST_SERVICE_URI+"/another/phones/", List.class);

        return new ResponseEntity<List<Phone>>(phoneMap,HttpStatus.OK);

    }

}
