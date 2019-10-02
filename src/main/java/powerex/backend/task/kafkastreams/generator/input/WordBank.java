package powerex.backend.task.kafkastreams.generator.input;

import java.util.Arrays;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
class WordBank {

  static List<String> wordList = Arrays.asList(
      "Apache,", "Kafka.", "was      originally", "developed,", "LinkedIn,",
      "and", "was", "subsequently", "open.", "sourced", "early", "2011.",
      "Graduation", "from", "the", "Apache", "Incubator", "occurred",
      "October", "2012.", "2014,", "Jun", "Rao,", "Jay", "Kreps,",
      "and", "Neha", "Narkhede,", "who", "had", "worked",
      "LinkedIn,", "created", "new", "company", "named", "Confluent",
      "with", "focus", "According", "Quora",
      "post", "from", "2014,", "Kreps", "chose", "name", "the", "software",
      "after", "the", "author", "Franz", "because",
      "system", "optimized", "for", "writing,", "and", "liked", "Kafka", "work"
  );
}
