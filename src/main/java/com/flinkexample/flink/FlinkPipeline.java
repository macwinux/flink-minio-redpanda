package com.flinkexample.flink;

import static com.flinkexample.flink.sourceandsink.Producers.createStringProducerForTopic;

import java.time.Duration;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.connector.file.src.FileSource;
import org.apache.flink.connector.file.src.reader.TextLineInputFormat;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;

import com.flinkexample.flink.operator.WordsCapitalizer;

public class FlinkPipeline {

    public static void capitalize() throws Exception {

        String propertiesFilePath = "application.properties";
        ParameterTool parameters = ParameterTool.fromPropertiesFile(propertiesFilePath);

        String outputTopic = parameters.get("outputTopic");
        String address = parameters.get("address");
        String path = parameters.get("path");
        String sourceName = parameters.get("source");
        //String outputTopic = "flink_output";
        //String address = "redpanda:29092";

        
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        final FileSource<String> source =
            FileSource.forRecordStreamFormat(new TextLineInputFormat(), new Path(path))
            .monitorContinuously(Duration.ofSeconds(1L))
            .build();
        DataStream<String> stream = environment.fromSource(source, WatermarkStrategy.noWatermarks(), sourceName);
        KafkaSink<JsonNode> flinkKafkaProducer= createStringProducerForTopic(outputTopic, address);
        stream.map(new MapToJson()).sinkTo(flinkKafkaProducer);    
        environment.execute();
    }

    public static class MapToJson implements MapFunction < String ,JsonNode> {
        ObjectMapper jsonParser = new ObjectMapper();
        public JsonNode map(String value) throws Exception {
            return jsonParser.readValue(value, JsonNode.class);
        } 
    }

    public static void main(String[] args) throws Exception {
    	capitalize();
    }

}
