package com.example.mongo.rest;
 
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;

import com.example.mongo.rest.model.Movie;
import org.springframework.web.client.RestTemplate;
 

public class SpringBootRestTestClient {
 
    public static final String REST_SERVICE_URI = "http://localhost:8080/SpringBootRestApi/api";
     
    /* GET */
    @SuppressWarnings("unchecked")
    private static void listAllUsers(){
        System.out.println("Testing listAllUsers API-----------");
         
        RestTemplate restTemplate = new RestTemplate();
        List<LinkedHashMap<String, Object>> usersMap = restTemplate.getForObject(REST_SERVICE_URI+"/user/", List.class);
         
        if(usersMap!=null){
            for(LinkedHashMap<String, Object> map : usersMap){
                System.out.println("Movie : id="+map.get("id")+", Name="+map.get("name")+", Age="+map.get("age")+", Salary="+map.get("salary"));;
            }
        }else{
            System.out.println("No user exist----------");
        }
    }
     
    /* GET */
    private static void getUser(){
        System.out.println("Testing getUser API----------");
        RestTemplate restTemplate = new RestTemplate();
        Movie movie = restTemplate.getForObject(REST_SERVICE_URI+"/movie/1", Movie.class);
        System.out.println(movie);
    }
     
    /* POST */
    private static void createUser() {
        System.out.println("Testing create Movie API----------");
        RestTemplate restTemplate = new RestTemplate();
        Movie movie = new Movie(0,"Sarah",51,134);
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI+"/movie/", movie, Movie.class);
        System.out.println("Location : "+uri.toASCIIString());
    }
 
    /* PUT */
    private static void updateUser() {
        System.out.println("Testing update Movie API----------");
        RestTemplate restTemplate = new RestTemplate();
        Movie movie = new Movie(1,"Tomy",33, 70000);
        restTemplate.put(REST_SERVICE_URI+"/movie/1", movie);
        System.out.println(movie);
    }
 
    /* DELETE */
    private static void deleteUser() {
        System.out.println("Testing delete Movie API----------");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(REST_SERVICE_URI+"/user/3");
    }
 
 
    /* DELETE */
    private static void deleteAllUsers() {
        System.out.println("Testing all delete Users API----------");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(REST_SERVICE_URI+"/user/");
    }
 
    public static void main(String args[]){
        listAllUsers();
        getUser();
        createUser();
        listAllUsers();
        updateUser();
        listAllUsers();
        deleteUser();
        listAllUsers();
        deleteAllUsers();
        listAllUsers();
    }
}