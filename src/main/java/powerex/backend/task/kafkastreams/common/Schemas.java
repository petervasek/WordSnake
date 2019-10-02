package powerex.backend.task.kafkastreams.common;

import static powerex.backend.task.kafkastreams.common.SchemaFields.*;

import lombok.experimental.UtilityClass;
import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;

@UtilityClass
public class Schemas {

  public static final Schema keySchema = SchemaBuilder.builder()
      .record(KEY_RECORD_NAME).fields()
      .name(KEY_TIME_FIELD_NAME).type().stringType().stringDefault("empty")
      .endRecord();

  public static final Schema valueSchema = SchemaBuilder.builder()
      .record(VALUE_RECORD_NAME).fields()
      .name(VALUE_SENTENCE_FIELD_NAME).type().stringType().stringDefault("empty")
      .endRecord();

}
