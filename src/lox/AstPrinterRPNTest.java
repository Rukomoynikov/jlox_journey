package lox;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AstPrinterRPNTest {

  @DisplayName("Test RPN for (1 + 2) * (4 - 3)")
  @Test
  void print() {
    Expr expr = new Expr.Binary(
      new Expr.Grouping(
        new Expr.Binary(
          new Expr.Literal(1),
          new Token(TokenType.PLUS, "+", null, 1),
          new Expr.Literal(2)
        )
      ),
      new Token(TokenType.STAR, "*", null, 1),
      new Expr.Grouping(
        new Expr.Binary(
          new Expr.Literal(4),
          new Token(TokenType.MINUS, "-", null, 1),
          new Expr.Literal(3)
        )
      )
    );

    String result = new AstPrinterRPN().print(expr);

    Assertions.assertEquals(result, "1 2 + 4 3 - *");
  }
}