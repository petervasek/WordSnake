package powerex.backend.task.kafkastreams.generator.output;

import static powerex.backend.task.kafkastreams.generator.output.SnakeMoves.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class WordSnakeGenerator {

  public String getSnake(String s) {
    char[][] array = new char[s.length()][s.length()];
    String[] words = s.split(" ");
    int[] yx = new int[] {0, 0};
    SnakeMoves nextMove = INIT;

    /*if snake in random movement (UP, DOWN, LEFT, RIGHT) gets STUCK,
    restart with simple path (only DOWN, LEFT, RIGHT)
    generator is used */

    for (String w: words) {
      nextMove = getNextMove(array, s.length(), w, yx, nextMove, true);
      if (nextMove != STUCK) {
        writeWord(array, w, yx, nextMove);
      }
      else {
        array = new char[s.length()][s.length()];
        yx = new int[] {0, 0};
        nextMove = INIT;

        for (String w1: words) {
          nextMove = getNextMove(array, s.length(), w1, yx, nextMove, false);
          writeWord(array, w1, yx, nextMove);
        }

        break;
      }
    }

    return "\n" + convertCharArrayToString(array, s.length());
  }

  private void writeWord(char[][] array, String w, int[] yx, SnakeMoves m) {
    switch(m){
      case UP:
        for(char c : w.toCharArray()) {
          array[yx[0]--][yx[1]] = c;
        }
        yx[0]++;
        break;
      case RIGHT:
        for(char c : w.toCharArray()) {
          array[yx[0]][yx[1]++] = c;
        }
        yx[1]--;
        break;
      case DOWN:
        for(char c : w.toCharArray()) {
          array[yx[0]++][yx[1]] = c;
        }
        yx[0]--;
        break;
      case LEFT:
        for(char c : w.toCharArray()) {
          array[yx[0]][yx[1]--] = c;
        }
        yx[1]++;
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + m);
    }
  }

  private SnakeMoves getNextMove(char[][] array, int maxLength, String w, int[] yx, SnakeMoves m, boolean allowedUp) {
    List<SnakeMoves> possibleWays = new ArrayList<>();
    Random r = new Random();

    switch (m) {
      case INIT:
        return RIGHT;
      case UP:
      case DOWN:
        if (yx[1] + w.length() <= maxLength && !checkCollision(array, w, yx, RIGHT))
          possibleWays.add(RIGHT);
        if (yx[1] - w.length() >= 0 && !checkCollision(array, w, yx, LEFT))
          possibleWays.add(LEFT);
        break;
      case RIGHT:
      case LEFT:
        if (yx[0] - w.length() >= 0 && !checkCollision(array, w, yx, UP))
          possibleWays.add(UP);
        if (yx[0] + w.length() <= maxLength && !checkCollision(array, w, yx, DOWN))
          possibleWays.add(DOWN);
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + m);
    }

    if(!allowedUp && possibleWays.contains(UP)) {
      possibleWays.remove(UP);
    }

    if (possibleWays.isEmpty()) {
      return STUCK;
    }
    return possibleWays.get(r.nextInt(possibleWays.size()));
  }

  private boolean checkCollision(char[][] array, String w, int[] yx, SnakeMoves m) {

    int gap = 5;

    switch(m){
      case UP:
        for(int i = 1 ; i < w.length() + gap ; i++) {
          if(yx[0] - i >= 0 && (int) array[yx[0] - i][yx[1]] != 0) {
            return true;
          }
        }
        break;
      case RIGHT:
        for(int i = 1 ; i < w.length() + gap ; i++) {
          if((int) array[yx[0]][yx[1] + i] != 0) {
            return true;
          }
        }
        break;
      case DOWN:
        for(int i = 1 ; i < w.length() + gap ; i++) {
          if((int) array[yx[0] + i][yx[1]] != 0) {
            return true;
          }
        }
        break;
      case LEFT:
        for(int i = 1 ; i < w.length() + gap ; i++) {
          if(yx[1] - i >= 0 && (int) array[yx[0]][yx[1] - i] != 0) {
            return true;
          }
        }
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + m);
    }
    return false;
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
