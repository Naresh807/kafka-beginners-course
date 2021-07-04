package com.training.kafka.consumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerDemoAssignSeek {

	//if we start more than one pod for a consumer group then ConsumerCoordinate revoke existing partition assignment and rebalanceing happen again
	public static void main(String[] args) {
		final Logger logger = LoggerFactory.getLogger(ConsumerDemoAssignSeek.class);
		
		 String bootStrapServer = "127.0.0.1:9092";
		 String topic = "firstTopic";
		 
		 // create Producer properties
		 Properties properties = new Properties();
		 
		 properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
		 properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		 properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		 properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		 /*properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest/latest/none");
					 //earliest = would read data from start
					 //latest = would read from latest
					 //none= will throw error if no offset is saved */
		 
		 //Create consumer 
		 KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
		 
		 //assign and seek are mostly used to replay data or fetch a specific message
		 
		 //assign
		 TopicPartition partitionToReadFrom = new TopicPartition(topic, 0);
		 long offsetToReadFrom = 15L;
		 consumer.assign(Arrays.asList(partitionToReadFrom));
		 
		 consumer.seek(partitionToReadFrom, offsetToReadFrom);
		 
		 int numberOfMessagesToRead = 5;
		 boolean keepOnReading = true;
		 int numberOfMessageReadSoFar = 0;
		 
		 //poll for new data
		 while(keepOnReading) {
			ConsumerRecords<String,String> records =  consumer.poll(Duration.ofMillis(100));
			
			for (ConsumerRecord<String, String> record : records) {
				numberOfMessageReadSoFar += 1;
				 logger.info("Key: "+record.key()+" Value: "+record.value());
				 logger.info("Partition: "+record.partition() + ", Offset: "+record.offset() );
				 
				 if(numberOfMessageReadSoFar >= numberOfMessagesToRead) {
					 keepOnReading = false;
				 }
			}
		 }
		 
		 
			 

	}

}
