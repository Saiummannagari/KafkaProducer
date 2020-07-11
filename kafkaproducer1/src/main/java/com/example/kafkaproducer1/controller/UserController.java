package com.example.kafkaproducer1.controller;

import com.example.kafkaproducer1.UserEventProducer;
import com.example.kafkaproducer1.model.User;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

@RestController
@Slf4j
public class UserController {

	
	@Autowired 
	UserEventProducer userEventProducer;
      
	
	
        @PostMapping("/publish")
        public ResponseEntity<User> post(@RequestBody User user) throws Exception{

        	userEventProducer.sendEvent(user);
        	//SendResult<String, User> sendResult=userEventProducer.sendEventSynchronous(user);
        	//log.info("SendResult is:{}",sendResult.toString());

            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        }
    }

