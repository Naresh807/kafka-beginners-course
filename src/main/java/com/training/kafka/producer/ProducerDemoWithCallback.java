package com.training.kafka.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class ProducerDemoWithCallback {
	
	public static void main(String args[]) {
		
		final Logger logger = LoggerFactory.getLogger(ProducerDemoWithCallback.class);
		
		 String bootStrapServer = "127.0.0.1:9092";
		// create Producer properties
		  Properties properties = new Properties();
		  properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
		  properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		  properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		//  properties.setProperty("acks", "");
		
		  
		// create producer
		  KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
		
		  for(int i=1; i<11; i++) {
		
		//create a producer record
		  ProducerRecord<String, String> record = new ProducerRecord<String, String>("firstTopic", "Hello Java "+i);
		//send data - asynchronous
		  producer.send(record, new Callback() {
												public void onCompletion(RecordMetadata metadata, Exception exception) {
													// execute every time when record is successfully sent or exception is thrown
												   if(exception == null) {
													   // the record is succesfully sent
													   logger.info("Received new metadata. \n"+
															          "Topic:"+metadata.topic()+"\n"+
															          "Partition:"+metadata.partition()+"\n"+
															          "OffSet:"+metadata.offset()+"\n"+
															          "Timestamp:"+metadata.timestamp());
												   } else {
													   logger.error("Error while producing "+exception);
												   }
												   
												}
											});
		  
		  }
		  //flush data
		  producer.flush();
		  //flush & close
		  producer.close();
	}


}
