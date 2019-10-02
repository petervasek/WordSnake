package powerex.backend.task.kafkastreams.generator.input;

import static powerex.backend.task.kafkastreams.generator.input.SentenceCreatorConfig.*;

import java.util.Random;
import java.util.stream.IntStream;

public class SentenceCreator {

  private Random rand = new Random();

  public String getSentence(){

    int length = SENTENCE_MIN_LENGTH + rand.nextInt(SENTENCE_MAX_LENGTH - SENTENCE_MIN_LENGTH);
    StringBuilder result = new StringBuilder();

    IntStream.range(0, length)
        .forEach(x -> {
          result.append(WordBank.wordList.get(rand.nextInt(WordBank.wordList.size())));
          result.append(" ");
          }
        );

    return result.toString();
  }
}
