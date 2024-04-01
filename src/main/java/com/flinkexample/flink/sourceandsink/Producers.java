package com.flinkexample.flink.sourceandsink;

import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.formats.json.JsonSerializationSchema;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;

public class Producers {


    
	public static KafkaSink<JsonNode> createStringProducerForTopic(String topic, String kafkaAddress) {
		JsonSerializationSchema<JsonNode> jsonFormat=new JsonSerializationSchema<>();
   	 return KafkaSink
   		     .<JsonNode>builder()
   		     .setBootstrapServers(kafkaAddress)
   		     .setRecordSerializer(KafkaRecordSerializationSchema
   		    		 						.builder()
   		    		 						.setTopic(topic)
   		    		 						.setValueSerializationSchema(jsonFormat)
   		     								.build())
   		     .build(); 
    }


}
