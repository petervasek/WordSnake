package powerex.backend.task.kafkastreams;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;

public class Schemas {

  public static Schema keySchema = SchemaBuilder.builder()
      .record("sentence_key").fields()
      .name("time").type().stringType().stringDefault("empty")
      .endRecord();

  public static Schema valueSchema = SchemaBuilder.builder()
      .record("sentence_value").fields()
      .name("sentence").type().stringType().stringDefault("empty")
      .endRecord();

}
