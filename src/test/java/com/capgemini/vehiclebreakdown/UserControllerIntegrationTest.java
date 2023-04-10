package com.capgemini.vehiclebreakdown;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.capgemini.vehiclebreakdown.model.Mechanic;
import com.capgemini.vehiclebreakdown.model.SearchMechanicListResponse;
import com.capgemini.vehiclebreakdown.model.User;
import com.capgemini.vehiclebreakdown.model.UserLoginResponse;
import com.capgemini.vehiclebreakdown.model.UserRequestResponse;
import com.capgemini.vehiclebreakdown.model.UserUpdateRequest;
import com.capgemini.vehiclebreakdown.model.UserUpdateResponse;
import com.capgemini.vehiclebreakdown.repository.AssistanceRequiredRepository;
import com.capgemini.vehiclebreakdown.repository.MechanicRepository;
import com.capgemini.vehiclebreakdown.repository.UserRepository;


@SpringBootTest(classes = OnRoadVehicleBreakdownAssistanceSystemApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {

	private static String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
    	UserControllerIntegrationTest.token = token;
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AssistanceRequiredRepository assistanceRequiredRepository;

    @Autowired
    private MechanicRepository mechanicRepository;

    @Autowired
    private UserRepository userRepository;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:"+port+"/users";
    }
    
    @Test
    @Order(1)
    void testUserRegistration() {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        Map map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");

        headers.setAll(map);

        Map req_payload = new HashMap();
        req_payload.put("username","Pinky");
        req_payload.put("emailId", "pinky@gmail.com");
        req_payload.put("phoneNumber", 1675188976);
        req_payload.put("userPassword", "pink1234");


        HttpEntity<?> request = new HttpEntity<>(req_payload, headers);

        ResponseEntity<?> response = new RestTemplate().postForEntity(getRootUrl()+"/register", request, String.class);
        assertNotNull(response.getBody());
    }

    @Test
    @Order(2)
    void testLoginUser() {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        Map map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");
        headers.setAll(map);
        Map req_payload = new HashMap();
        req_payload.put("username", "Pinky");
        req_payload.put("password", "pink1234");
        HttpEntity<?> request = new HttpEntity<>(req_payload, headers);
        ResponseEntity<UserLoginResponse> response = new RestTemplate().postForEntity(getRootUrl()+"/login", request, UserLoginResponse.class);
        assertNotNull(response.getBody());
        this.setToken(response.getBody().getToken());
    }

//  @Test
//  void testLogoutUser()
//  {
//      MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
//      Map map = new HashMap<String, String>();
//      map.put("Content-Type", "application/json");
//      headers.setAll(map);
//      Map req_payload = new HashMap();
//      req_payload.put("emailId", "pinky@gmail.com");
//      req_payload.put("userPassword", "pink1234");
//      HttpEntity<?> request = new HttpEntity<>(req_payload, headers);
//      ResponseEntity<?> response = new RestTemplate().postForEntity(getRootUrl()+"/logout", request, String.class);
//      assertNotNull(response.getBody());
//
//  }

    @Test
    @Order(3)
    void testSearchMechanicByLocation() {
        assertNotNull(this.getToken());
        String location="Malad";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getToken());
        HttpEntity<List<Mechanic>> entity = new HttpEntity<List<Mechanic>>(null, headers);
        ResponseEntity<SearchMechanicListResponse> getResponse = restTemplate.exchange(getRootUrl()+"/searchMechanic/"+location, HttpMethod.GET, entity, SearchMechanicListResponse.class);
        System.out.println(getResponse.getBody());
        assertEquals(200, getResponse.getStatusCodeValue());
    }

    @Test
    @Order(4)
    void testaddRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(getToken());

        Map req_payload = new HashMap();
        req_payload.put("assistanceType","Tire Puncture");
        req_payload.put("mechanicId",25);
        req_payload.put("location", "Malad");


        HttpEntity<?> request = new HttpEntity<>(req_payload, headers);

        ResponseEntity<UserRequestResponse> response = new RestTemplate().postForEntity(getRootUrl()+"/addRequest", request, UserRequestResponse.class);
        assertNotNull(response.getBody());
    }

//  @Test
//  @Order(5)
//  void testGiveFeedback() {
//      Long mechId=25L;
//      Long uId=22L;
//      AssistanceRequired assistanceRequired= assistanceRequiredRepository.findByUserIdAndMechanicId(uId,mechId);
//      Mechanic mechanic=mechanicRepository.findByMechanicId(mechId);
//      MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
//        Map map = new HashMap<String, String>();
//        map.put("Content-Type", "application/json");
//
//        headers.setAll(map);
//
//        Map req_payload = new HashMap();
//        req_payload.put("userId", 22);
//      req_payload.put("mechanicId",25);
//      req_payload.put("feedbackMessage", "very nice service");
//      req_payload.put("ratings",4);
//      req_payload.put("assistanceId",26);
//      //req_payload.put(mechanic, mechanic);
//      System.out.println(req_payload);
//
//
//      HttpEntity<?> request = new HttpEntity<>(req_payload, headers);
//
//      ResponseEntity<?> response = new RestTemplate().postForEntity(getRootUrl()+"/giveFeedback/"+mechId+"/"+uId, request, String.class);
//      assertNotNull(response.getBody());
//
//  }

    @Test
    @Order(6)
    void testGetAll() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getToken());
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> getResponse = restTemplate.exchange(getRootUrl()+"/all", HttpMethod.GET, entity, String.class);
        System.out.println(getResponse);
        assertEquals(200, getResponse.getStatusCodeValue());
    }

    @Test
    @Order(7)
    void testGetUserById() {
        Long id=52L;
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getToken());
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> getResponse = restTemplate.exchange(getRootUrl()+"/byid/"+id, HttpMethod.GET, entity, String.class);
        System.out.println(getResponse);
        assertEquals(200, getResponse.getStatusCodeValue());

    }

    @Test
    @Order(8)
    void testGetUserByEmailId() {
        String emailId="pantu@gmail.com";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getToken());
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> getResponse = restTemplate.exchange(getRootUrl()+"/byemail/"+emailId, HttpMethod.GET, entity, String.class);
        System.out.println(getResponse);
        assertEquals(200, getResponse.getStatusCodeValue());

    }

    @Test
    @Order(9)
    public void testUpdateUser() {
        long id=2;
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getToken());
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<User> user = restTemplate.exchange(getRootUrl()+"/byid/"+id, HttpMethod.GET, entity, User.class);
        System.out.println("[+]before update");
        System.out.println(user.getBody());
        assertNotNull(user.getBody());

        UserUpdateRequest update_req = new UserUpdateRequest();
        if(user.getBody().getEmailId().equals("pinky@gmail.com"))
            update_req.setEmailId("updated-pinky@gmail.com");
        else
            update_req.setEmailId("pinky@gmail.com");
        update_req.setPhoneNumber(9998887776L);
        update_req.setUsername("Pinky");

        HttpEntity<UserUpdateRequest> entity2 = new HttpEntity<>(update_req, headers);

        ResponseEntity<UserUpdateResponse> response = restTemplate.exchange(getRootUrl()+"/update", HttpMethod.POST, entity2, UserUpdateResponse.class);
        assertNotNull(response.getBody());
        System.out.println("[+]update");
        System.out.println(response.getBody());

        ResponseEntity<User> updatedUser = restTemplate.exchange(getRootUrl()+"/byid/"+id, HttpMethod.GET, entity, User.class);

        System.out.println("[+]after update");
        System.out.println(updatedUser.getBody());
        assertNotNull(updatedUser);
        assertNotNull(user.getBody());
        assertNotEquals(user.getBody().getEmailId(), updatedUser.getBody().getEmailId());
    }


}


