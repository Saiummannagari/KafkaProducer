package com.example.kafkaproducer1;


import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.example.kafkaproducer1.model.User;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserEventProducer {
	
	@Autowired
	KafkaTemplate<String,User> kafkaTemplate;
	
	
	public void sendEvent(User user)
	{
		ListenableFuture<SendResult<String,User>> listenableFuture=kafkaTemplate.sendDefault(user.getName(),user);
		listenableFuture.addCallback(new ListenableFutureCallback<SendResult<String,User>>() {

			@Override
			public void onSuccess(SendResult<String, User> result) {
				// TODO Auto-generated method stub
				handleSuccess(user.getName(),user,result);
				
			}

			

			@Override
			public void onFailure(Throwable ex) {
				// TODO Auto-generated method stub
				handleFailure(user.getName(),user,ex);
				
			}
		});
	}
	private void handleSuccess(String name, User user, SendResult<String, User> result) {
		// TODO Auto-generated method stub
		log.info("Message sent successfully for key:{},value:{} in partition:{}",name,user,result.getRecordMetadata().partition());
	}
	
	private void handleFailure(String name, User user, Throwable ex) {
		// TODO Auto-generated method stub
		log.error("error sending the message.The exception is:",ex.getMessage());
	}

	
	public SendResult<String, User> sendEventSynchronous(User user) throws Exception
	{
		SendResult<String,User> sendResult=null;
		try
		{
			sendResult=kafkaTemplate.sendDefault(user.getName(),user).get();
		}
		catch(ExecutionException | InterruptedException e)
		{
			log.error("error sending the message.The exception is:",e.getMessage());
			throw e;
		}
		catch(Exception e)
		{
			log.error("error sending the message.The exception is:",e.getMessage());
			throw e;
		}
		
		return sendResult;
	}
	
	
}
