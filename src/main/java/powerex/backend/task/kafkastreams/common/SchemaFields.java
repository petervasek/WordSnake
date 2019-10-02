package powerex.backend.task.kafkastreams.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SchemaFields {

  static final String KEY_RECORD_NAME = "sentence_key";

  public static final String KEY_TIME_FIELD_NAME = "time";

  static final String VALUE_RECORD_NAME = "sentence_value";

  public static final String VALUE_SENTENCE_FIELD_NAME = "sentence";

}
