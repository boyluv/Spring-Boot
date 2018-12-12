package com.example.greeting;

import com.example.greeting.MyObject.Phone;
import com.example.greeting.MyObject.PhoneRepository;
import com.example.greeting.Service.PhoneService;
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

    @Autowired
    private PhoneService phoneService;

    // Get all Phones
    @RequestMapping(value = "/phones",method = RequestMethod.GET)
    public ResponseEntity<?> getAllPhone(){
        if(phoneService.getAllPhones() == null)
            return new ResponseEntity<>(new CustomErrorType("Is empty"),HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(phoneService.getAllPhones());
    }

    // Get Single Phone
    @RequestMapping(value = "/phones/{id}",method = RequestMethod.GET)
    public ResponseEntity<?> getPhone(@PathVariable("id") long id){
        Phone curPhone = phoneService.getPhoneWithId(id);
        if(curPhone == null){
            return new ResponseEntity<>(new CustomErrorType("Phone with id "+ id+" not found"), HttpStatus.NOT_FOUND);
        }
        return  ResponseEntity.ok(curPhone);
    }

    // Create Phone
    @RequestMapping(value = "/phones/",method = RequestMethod.POST)
    public ResponseEntity<?> createPhone(@RequestBody Phone phone, UriComponentsBuilder ucBuilder){

        phoneService.createNewPhone(phone);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/phones/{id}").buildAndExpand(phone.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);


    }

    // Update Phone
    @RequestMapping(value = "/phones/{id}",method = RequestMethod.PUT)
    public ResponseEntity<?> updatePhones(@PathVariable("id") long id,@RequestBody Phone phone){

        Phone curPhone = phoneService.getPhoneWithId(id);

        if (curPhone == null) {
            return new ResponseEntity<>(new CustomErrorType("Can not find phone with id "+id), HttpStatus.NOT_FOUND);
        }


        return new ResponseEntity(phoneService.updatePhone(curPhone, phone), HttpStatus.OK);


    }
    // Delete Phone
    @RequestMapping(value = "/phones/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePhone(@PathVariable("id") long id){
        if (!phoneService.deletePhoneWithId(id)) {
            return new ResponseEntity<>(new CustomErrorType("Can not find phone with id " + id), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Delete All Phone
    @RequestMapping(value = "/phones/",method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAllPhone(){
        System.out.println("Delete all phones");
        //New version
        phoneService.deleteAllPhone();
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
