package com.example.greeting;

import com.example.greeting.MyObject.Phone;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;

public class GreetingControllerTest {
    private static final String REST_SERVICE_URI = "http://localhost:8080";

    /* GET */
    @Test
    public void getAllPhone() {
        System.out.println("Testing getAllPhone API-----------");

        RestTemplate restTemplate = new RestTemplate();
        List<LinkedHashMap<String, Object>> phoneMap = restTemplate.getForObject(REST_SERVICE_URI+"/phones/", List.class);

        if(phoneMap!=null){
            for(LinkedHashMap<String, Object> map : phoneMap){
                System.out.println("User : id="+map.get("id")+", Name="+map.get("name") +", detail phone = "+map.get("detailPhone"));;
            }
        }else{
            System.out.println("No user exist----------");
        }

    }

    /* GET */
    @Test
    public void getPhone() {
        int id = 3;
        System.out.println("Testing getPhone API");
        RestTemplate restTemplate = new RestTemplate();

        Phone phoneMap = restTemplate.getForObject(REST_SERVICE_URI+"/phones/"+id,Phone.class);
        if(phoneMap !=null)
            System.out.println("User : id="+phoneMap.getId()+", Name="+phoneMap.getName() +", detail phone = {"  +" color = "+phoneMap.getDetailPhone().getColor()+" os = "+phoneMap.getDetailPhone().getOs() +"}");
        else
            System.out.println("Phone with id "+id + " not found");
    }

    /* POST*/
    @Test
    public void createPhone() {
        System.out.println("Testing create new Phone API");
        RestTemplate restTemplate = new RestTemplate();
        Phone newPhone = new Phone(100,"Blackberry","black","BlackberryOS");
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI+"/phones/", newPhone, Phone.class);
        System.out.println("Location: "+uri.toASCIIString());
    }

    /* PUT */
    @Test
    public void updatePhones() {
        System.out.println("Testing update exist Phone API");
        RestTemplate restTemplate = new RestTemplate();
        Phone phoneUpdated= new Phone(1,"Oppo_updated","red","android");
        restTemplate.put(REST_SERVICE_URI+"/phones/1",phoneUpdated);
        System.out.println(phoneUpdated);
    }

    /* DELETE */
    @Test
    public void deletePhone() {
        int id = 2;
        System.out.println("Testing update exist Phone API");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(REST_SERVICE_URI+"/phones/"+id);
    }

    @Test
    public void deleteAllPhone() {
        System.out.println("Testing update exist Phone API");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(REST_SERVICE_URI+"/phones/");
    }
}