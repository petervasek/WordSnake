package powerex.backend.task.kafkastreams.generator.output;

import static powerex.backend.task.kafkastreams.generator.output.SnakeMove.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class WordSnakeGenerator {

  private Random r = new Random();
  private static final String EXCEPTION_SHARED_PART = "Unexpected value: ";

  public String getSnake(String sentence) {
    char[][] array = new char[sentence.length()][sentence.length()];
    String[] words = sentence.split(" ");
    int[] yx = new int[] {0, 0};
    SnakeMove nextMove = INIT;

    /*if snake in random movement (UP, DOWN, LEFT, RIGHT) gets STUCK,
    restart with simple path generator (only DOWN, LEFT, RIGHT)
    is used */

    for (String w: words) {
      nextMove = getNextMove(array, sentence.length(), w, yx, nextMove, true);
      if (nextMove != STUCK) {
        writeWord(array, w, yx, nextMove);
      }
      else {
        array = new char[sentence.length()][sentence.length()];
        yx = new int[] {0, 0};
        nextMove = INIT;

        for (String w1: words) {
          nextMove = getNextMove(array, sentence.length(), w1, yx, nextMove, false);
          writeWord(array, w1, yx, nextMove);
        }

        break;
      }
    }

    return "\n" + convertCharArrayToString(array, sentence.length());
  }
  
  private SnakeMove getNextMove(char[][] array, int arraySize,
      String word, int[] yx, SnakeMove nextMove, boolean allowedUp) {

    List<SnakeMove> possibleWays = new ArrayList<>();

    switch (nextMove) {
      case INIT:
        return RIGHT;
      case UP:
      case DOWN:
        if (yx[1] + word.length() <= arraySize && checkNoCollision(array, word, yx, RIGHT))
          possibleWays.add(RIGHT);
        if (yx[1] - word.length() >= 0 && checkNoCollision(array, word, yx, LEFT))
          possibleWays.add(LEFT);
        break;
      case RIGHT:
      case LEFT:
        if (yx[0] - word.length() >= 0 && checkNoCollision(array, word, yx, UP))
          possibleWays.add(UP);
        if (yx[0] + word.length() <= arraySize && checkNoCollision(array, word, yx, DOWN))
          possibleWays.add(DOWN);
        break;
      default:
        throw new IllegalStateException(EXCEPTION_SHARED_PART + nextMove);
    }

    if(!allowedUp) {
      possibleWays.remove(UP);
    }

    if (possibleWays.isEmpty()) {
      return STUCK;
    }
    return possibleWays.get(r.nextInt(possibleWays.size()));
  }

  private void writeWord(char[][] array, String word, int[] yx, SnakeMove nextMove) {

    Stream<Character> letters = word.chars().mapToObj(c -> (char) c);

    switch(nextMove){
      case UP:
        letters.forEach(c ->
            array[yx[0]--][yx[1]] = c
        );
        yx[0]++;
        break;
      case RIGHT:
        letters.forEach(c ->
          array[yx[0]][yx[1]++] = c
        );
        yx[1]--;
        break;
      case DOWN:
        letters.forEach(c ->
          array[yx[0]++][yx[1]] = c
        );
        yx[0]--;
        break;
      case LEFT:
        letters.forEach(c ->
          array[yx[0]][yx[1]--] = c
        );
        yx[1]++;
        break;
      default:
        throw new IllegalStateException(EXCEPTION_SHARED_PART + nextMove);
    }
  }
  
  private boolean checkNoCollision(char[][] array, String word, int[] yx, SnakeMove nextMove) {

    int gap = SnakeGeneratorConfig.MIN_GAP;

    return IntStream.range(1, word.length() + gap)
        .allMatch(i -> {
          switch(nextMove) {
            case UP:
              if(yx[0] - i >= 0 && (int) array[yx[0] - i][yx[1]] != 0) {
                return false;
              }
              break;
            case RIGHT:
              if((int) array[yx[0]][yx[1] + i] != 0) {
                return false;
              }
              break;
            case DOWN:
              if((int) array[yx[0] + i][yx[1]] != 0) {
                return false;
              }
              break;
            case LEFT:
              if(yx[1] - i >= 0 && (int) array[yx[0]][yx[1] - i] != 0) {
                return false;
              }
              break;
            default:
              throw new IllegalStateException(EXCEPTION_SHARED_PART + nextMove);
          }
          return true;
        });
  }

  private String convertCharArrayToString(char[][] array, int maxLength) {
    StringBuilder result = new StringBuilder();

    for (int i = 0 ; i < getNumOfArrayLines(array, maxLength) ; i++){
      result.append(new String(array[i]));
      result.append("\n");
    }

    return result.toString();
  }

  private int getNumOfArrayLines(char[][] array, int maxLength) {

    int lineCounter = 0;

    for (int i = 0 ; i < maxLength ; i++) {
      int emptyLineCounter = 0;
      for (int j = 0 ; j < maxLength ; j++) {
        if ((int) array[i][j] != 0) {
          lineCounter++;
          break;
        }
        emptyLineCounter++;
      }
      if (emptyLineCounter == maxLength) {
        break;
      }
    }

    return lineCounter;
  }
}
