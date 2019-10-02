package powerex.backend.task.kafkastreams.generator.input;

import java.util.Random;

public class SentenceCreator {

  private Random rand = new Random();

  public String getSentence(){

    int length = rand.nextInt(WordBank.wordList.size()/5)+10;
    StringBuilder result = new StringBuilder();

    for(int i = 0 ; i<length ; i++){
      result.append(WordBank.wordList.get(rand.nextInt(WordBank.wordList.size())));
      result.append(" ");
    }

    return result.toString();
  }
}
