package com.example;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class StreamJob {
  public static void main(String[] args) throws Exception {
    final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    env.setParallelism(1);

    final DataStream<Tuple2<String, Integer>> dataStream = env.fromData(
        Tuple2.of("hello", 1),
        Tuple2.of("world", 2));

    dataStream.print();

    env.execute();
  }
}
