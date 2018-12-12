package com.example.greeting;

import com.example.greeting.MyObject.Phone;
import com.example.greeting.Service.PhoneService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
//

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = GreetingApplication.class)
@AutoConfigureMockMvc
public class GreetingControllerTest {

    @Autowired
    private GreetingController greetingController;

    @MockBean
    private PhoneService phoneService;

    @Autowired
    private MockMvc mvc;

    private Phone testPhone;

    private ArrayList<Phone> testListPhone;

    @Before
    public void setup()  {
        this.mvc = standaloneSetup(this.greetingController).build();// Standalone context
        testPhone = new Phone(1,"Apple","Black","Android");

        testListPhone = new ArrayList<Phone>();

        testListPhone.add(new Phone(1, "Apple",new Phone.DetailPhone("Black", "Android")));
        testListPhone.add(new Phone(2, "Oppo", new Phone.DetailPhone("White", "Android")));

    }

    /* GET */
    @Test
    public void getAllPhone() throws Exception{

        when(phoneService.getAllPhones()).thenReturn(testListPhone);
        this.mvc.perform(get("/phones").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Apple")))
                .andExpect(jsonPath("$[1].name", is("Oppo")));
    }

    /* GET */
    @Test
    public void getPhone() throws Exception {
        when(phoneService.getPhoneWithId(1L)).thenReturn(testPhone);

        mvc.perform(get("/phones/{id}",1L).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Apple")))
                .andExpect(jsonPath("$.detailPhone.color", is("Black")))
                .andExpect(jsonPath("$.detailPhone.os", is("Android")));
    }

    /* POST*/
    @Test
    public void createPhone()throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writeValueAsString(testPhone);
        this.mvc.perform(post("/phones/")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    /* PUT */
    @Test
    public void updatePhones()throws Exception {
        when(phoneService.getPhoneWithId(1L)).thenReturn(testPhone);
        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writeValueAsString(testPhone);
        this.mvc.perform(put("/phones/{id}",1L)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /* DELETE */
    @Test
    public void deletePhone()throws Exception {
        when(phoneService.deletePhoneWithId(1L)).thenReturn(true);

        this.mvc.perform(delete("/phones/{id}",1L))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteAllPhone()throws Exception {
        this.mvc.perform(delete("/phones/"))
                .andExpect(status().isNoContent());
    }
}