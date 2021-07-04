package com.training.kafka.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class ProducerDemo {
	
	public static void main(String args[]) {
		
		 String bootStrapServer = "127.0.0.1:9092";
		// create Producer properties
		  Properties properties = new Properties();
		  properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
		  properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		  properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		//  properties.setProperty("acks", "");
		
		// create producer
		  KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
		
		//create a producer record
		  ProducerRecord<String, String> record = new ProducerRecord<String, String>("firstTopic", "Hello Java");
		//send data - asynchronous
		  producer.send(record);
		  //flush data
		  producer.flush();
		  //flush & close
		  producer.close();
	}

}
