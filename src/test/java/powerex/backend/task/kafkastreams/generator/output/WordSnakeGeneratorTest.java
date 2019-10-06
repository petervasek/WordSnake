package powerex.backend.task.kafkastreams.generator.output;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class WordSnakeGeneratorTest {

  private static WordSnakeGenerator snakeGenerator = new WordSnakeGenerator();

  private static final String TEST_SENTENCE = "VARIABLE MUST PROVIDE EITHER DIMENSION EXPRESSIONS ORAN ARRAY INITIALIZER";

  @Test
  public void getNumOfArrayLinesEmptyArray() {

    int size = TEST_SENTENCE.length();
    char[][] array = new char[size][size];

    int nonEmptyLines = snakeGenerator.getNumOfArrayLines(array, size);

    assertThat(nonEmptyLines).isEqualTo(0);
  }

  @Test
  public void getNumOfArrayLinesNonEmptyArray() {

    int size = 5;
    char[][] array = {{   '1',     '2',       '3',   '\u0000', '\u0000'},
                      {'\u0000', '\u0000',    '4',   '\u0000', '\u0000'},
                      {'\u0000', '\u0000',    '5',   '\u0000', '\u0000'},
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000'},
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000'}
    };

    int nonEmptyLines = snakeGenerator.getNumOfArrayLines(array, size);

    assertThat(nonEmptyLines).isEqualTo(3);
  }

  @Test
  public void convertCharArrayToString() {

    int size = 5;
    char[][] array = {{   '1',      '2',      '3',   '\u0000', '\u0000'},
                      {'\u0000', '\u0000',    '4',   '\u0000', '\u0000'},
                      {'\u0000', '\u0000',    '5',   '\u0000', '\u0000'},
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000'},
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000'}
    };

    String result = snakeGenerator.convertCharArrayToString(array, size);

    assertThat(result).isEqualTo("123\u0000\u0000\n\u0000\u00004\u0000\u0000\n\u0000\u00005\u0000\u0000\n");
  }

  /*collision check is only called if word to be written fit into array
  - no border checks are needed*/
  @Test
  public void checkNoCollisionUp() {

    int size = 7;
    int[] yx = {5,1};
    char[][] array = {{   '1',     '2',       '3',     '4',       '5',      '6',      '7'  },
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',    '8'  },
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',    '9'  },
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',    '8'  },
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',    '7'  },
                      {'\u0000',    '1',      '2',      '3',      '4',      '5',      '6'  },
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000'}
    };

    boolean resultTooLong = snakeGenerator.checkNoCollision(array, "toolong", yx, SnakeMove.UP);
    boolean resultShortEnough = snakeGenerator.checkNoCollision(array, "a", yx, SnakeMove.UP);

    assertThat(resultTooLong).isEqualTo(false);
    assertThat(resultShortEnough).isEqualTo(true);
  }

  @Test
  public void checkNoCollisionRight() {

    int size = 7;
    int[] yx = {4,1};
    char[][] array = {{   '1',      '2',      '3',      '4',      '5',      '6',      '7'  },
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',    '8'  },
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',    '9'  },
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',    '8'  },
                      {'\u0000',    '0',   '\u0000', '\u0000', '\u0000', '\u0000',    '7'  },
                      {'\u0000',    '1',      '2',      '3',      '4',      '5',      '6'  },
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000'}
    };

    boolean resultTooLong = snakeGenerator.checkNoCollision(array, "toolong", yx, SnakeMove.RIGHT);
    boolean resultShortEnough = snakeGenerator.checkNoCollision(array, "a", yx, SnakeMove.RIGHT);

    assertThat(resultTooLong).isEqualTo(false);
    assertThat(resultShortEnough).isEqualTo(true);
  }

  @Test
  public void checkNoCollisionDown() {

    int size = 7;
    int[] yx = {1,3};
    char[][] array = {{   '1',      '2',      '3',      '4',      '5',      '6',      '7'  },
                      {'\u0000',    '3',      '4',      '5',   '\u0000', '\u0000',    '8'  },
                      {'\u0000',    '2',   '\u0000', '\u0000', '\u0000', '\u0000',    '9'  },
                      {'\u0000',    '1',   '\u0000', '\u0000', '\u0000', '\u0000',    '8'  },
                      {'\u0000',    '0',   '\u0000', '\u0000', '\u0000', '\u0000',    '7'  },
                      {'\u0000',    '1',      '2',      '3',      '4',      '5',      '6'  },
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000'}
    };

    boolean resultTooLong = snakeGenerator.checkNoCollision(array, "toolong", yx, SnakeMove.DOWN);
    boolean resultShortEnough = snakeGenerator.checkNoCollision(array, "a", yx, SnakeMove.DOWN);

    assertThat(resultTooLong).isEqualTo(false);
    assertThat(resultShortEnough).isEqualTo(true);
  }

  @Test
  public void checkNoCollisionLeft() {

    int size = 7;
    int[] yx = {2,5};
    char[][] array = {{   '1',      '2',      '3',      '4',      '5',      '6',      '7'  },
                      {'\u0000',    '3',      '4',      '5',      '6',      '7',      '8'  },
                      {'\u0000',    '2',   '\u0000', '\u0000', '\u0000',    '8',      '9'  },
                      {'\u0000',    '1',   '\u0000', '\u0000', '\u0000', '\u0000',    '8'  },
                      {'\u0000',    '0',   '\u0000', '\u0000', '\u0000', '\u0000',    '7'  },
                      {'\u0000',    '1',      '2',      '3',      '4',      '5',      '6'  },
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000'}
    };

    boolean resultTooLong = snakeGenerator.checkNoCollision(array, "toolong", yx, SnakeMove.LEFT);
    boolean resultShortEnough = snakeGenerator.checkNoCollision(array, "a", yx, SnakeMove.LEFT);

    assertThat(resultTooLong).isEqualTo(false);
    assertThat(resultShortEnough).isEqualTo(true);
  }

  /*writeWord is called only after possibility of collisions is checked*/
  @Test
  public void writeWord() {

    int size = 7;
    int[] yx = {0,6};
    char[][] array = {{   '1',      '2',      '3',      '4',      '5',      '6',      '7'  },
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000'},
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000'},
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000'},
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000'},
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000'},
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000'}
    };

    snakeGenerator.writeWord(array, "test", yx, SnakeMove.DOWN);

    assertThat(snakeGenerator.convertCharArrayToString(array, size))
        .isEqualTo("123456t\n"
            + "\u0000\u0000\u0000\u0000\u0000\u0000e\n"
            + "\u0000\u0000\u0000\u0000\u0000\u0000s\n"
            + "\u0000\u0000\u0000\u0000\u0000\u0000t\n");
    assertThat(yx[0]).isEqualTo(3);
  }

  @Test
  public void getNextMoveOnlyDownPossible() {

    int size = 7;
    int[] yx = {0,6};
    char[][] array = {{   '1',      '2',      '3',      '4',      '5',      '6',      '7'  },
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000'},
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000'},
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000'},
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000'},
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000'},
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000'}
    };

    SnakeMove result = snakeGenerator.getNextMove(
        array, size, "test", yx, SnakeMove.RIGHT, true);

    assertThat(result).isEqualTo(SnakeMove.DOWN);
  }

  @Test
  public void getNextMoveOnlyUpPossible() {

    int size = 7;
    int[] yx = {6,4};
    char[][] array = {{   '1',      '2',      '3',      '4',      '5',      '6',      '7'},
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',    '8'},
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',    '9'},
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',    '8'},
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',    '7'},
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',    '6'},
                      {'\u0000', '\u0000', '\u0000', '\u0000',    '3',      '4',      '5'}
    };

    SnakeMove result = snakeGenerator.getNextMove(
        array, size, "test", yx, SnakeMove.LEFT, true);

    assertThat(result).isEqualTo(SnakeMove.UP);
  }

  @Test
  public void getNextMoveOnlyLeftPossible() {

    int size = 7;
    int[] yx = {5,6};
    char[][] array = {{   '1',      '2',      '3',      '4',      '5',      '6',      '7'  },
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',    '8'  },
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',    '9'  },
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',    '8'  },
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',    '7'  },
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',    '6'  },
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000'}
    };

    SnakeMove result = snakeGenerator.getNextMove(
        array, size, "test", yx, SnakeMove.DOWN, true);

    assertThat(result).isEqualTo(SnakeMove.LEFT);
  }

  @Test
  public void getNextMoveOnlyRightPossible() {

    int size = 7;
    int[] yx = {4,0};
    char[][] array = {{   '1',      '2',      '3',      '4',      '5',      '6',      '7'},
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',    '8'},
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',    '9'},
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',    '8'},
                      {   '5',   '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',    '7'},
                      {   '4',   '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',    '6'},
                      {   '3',      '2',      '1',      '2',      '3',      '4',      '5'}
    };

    SnakeMove result = snakeGenerator.getNextMove(
        array, size, "test", yx, SnakeMove.UP, true);

    assertThat(result).isEqualTo(SnakeMove.RIGHT);
  }

  /*Normal mode is Up, Down, Left, Right,
    in safe mode snake can`t go Up*/
  @Test
  public void getNextMoveGettingStuckNormalMode() {

    int size = 7;
    int[] yx = {4,1};
    char[][] array = {{   '1',      '2',      '3',      '4',      '5',      '6',      '7'},
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',    '8'},
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',    '9'},
                      {   '6',      '7',   '\u0000', '\u0000', '\u0000', '\u0000',    '8'},
                      {   '5',   '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',    '7'},
                      {   '4',   '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',    '6'},
                      {   '3',      '2',      '1',      '2',      '3',      '4',      '5'}
    };

    SnakeMove result = snakeGenerator.getNextMove(
        array, size, "test", yx, SnakeMove.RIGHT, true);

    assertThat(result).isEqualTo(SnakeMove.STUCK);
  }

  @Test
  public void getNextMoveGettingStuckSafeMode() {

    int size = 7;
    int[] yx = {6,1};
    char[][] array = {{   '1',      '2',      '3',      '4',      '5',      '6',      '7'},
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',    '8'},
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',    '9'},
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',    '8'},
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',    '7'},
                      {'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',    '6'},
                      {'\u0000',    '2',      '1',      '2',      '3',      '4',      '5'}
    };

    SnakeMove result = snakeGenerator.getNextMove(
        array, size, "test", yx, SnakeMove.LEFT, false);

    assertThat(result).isEqualTo(SnakeMove.STUCK);
  }

  @Test
  public void getSnake() {

    String result = snakeGenerator.getSnake(TEST_SENTENCE);
    String emptyResult = snakeGenerator.getSnake("");

    assertThat(result.length()).isNotZero();
    assertThat(emptyResult).containsOnlyWhitespaces();

    log.info(result);
  }

}
