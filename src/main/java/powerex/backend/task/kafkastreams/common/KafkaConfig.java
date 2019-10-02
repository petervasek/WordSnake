package powerex.backend.task.kafkastreams.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class KafkaConfig {

  public static final String KAFKA_BROKERS = "localhost:9091,localhost:9092,localhost:9093";

  public static final String SCHEMA_REGISTRY_URL = "http://localhost:8081";

  public static final String STREAMS_APP_ID = "sentence-transformer-stream";

  public static final String CONSUMER_GROUP_ID = "processed-sentence-consumer";

  public static final String PROCESSED_DATA_TOPIC = "processed-sentence";

  public static final String RAW_DATA_TOPIC = "raw-sentence";

}
