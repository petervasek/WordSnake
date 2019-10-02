package powerex.backend.task.kafkastreams.generator;

import java.util.Random;

public class SentenceCreator {

  private WordBank wordBank = new WordBank();
  private Random rand = new Random();

  public String getSentence(){

    int length = rand.nextInt(wordBank.wordList.size()/5)+10;
    StringBuilder result = new StringBuilder();

    for(int i = 0 ; i<length ; i++){
      result.append(wordBank.wordList.get(rand.nextInt(wordBank.wordList.size())));
      result.append(" ");
    }

    return result.toString();
  }
}
