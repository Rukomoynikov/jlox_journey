package lox;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class ScannerTest {

  @Test
  @DisplayName("Basic scanTokens")
  void scanTokensBasic() {
    Scanner scanner = new Scanner(")");
    List<Token> tokens =  scanner.scanTokens();

    assertEquals(2, tokens.size());
    assertEquals("RIGHT_PAREN ) null", tokens.get(0).toStrging());
  }

  @Test
  @DisplayName("Test strings")
  void scanTokensString() {
    Scanner scanner = new Scanner("\"Hello\"");
    List<Token> tokens =  scanner.scanTokens();

    assertEquals(2, tokens.size());
    assertEquals("STRING \"Hello\" null", tokens.get(0).toStrging());
  }

  @Test
  @DisplayName("Test numbers")
  void scanTokensNumbers() {
    Scanner scanner = new Scanner("11");
    List<Token> tokens =  scanner.scanTokens();

    assertEquals(2, tokens.size());
    assertEquals("NUMBER 11 null", tokens.get(0).toStrging());
  }
}