package powerex.backend.task.kafkastreams.generator.input;

import java.util.Random;
import java.util.stream.IntStream;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class SentenceCreator {

  private Random rand = new Random();

  @Value("${snake.length.min}")
  private Integer sentenceMinLength = 8;

  @Value("${snake.length.max}")
  private Integer sentenceMaxLength = 20;

  public String getSentence(){

    int length = sentenceMinLength +
        rand.nextInt(sentenceMaxLength - sentenceMinLength);

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
