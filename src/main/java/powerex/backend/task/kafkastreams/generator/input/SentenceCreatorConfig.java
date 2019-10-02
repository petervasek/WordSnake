package powerex.backend.task.kafkastreams.generator.input;

import lombok.experimental.UtilityClass;

@UtilityClass
class SentenceCreatorConfig {

  static final Integer SENTENCE_MIN_LENGTH = 8;

  static final Integer SENTENCE_MAX_LENGTH = 20;

}
