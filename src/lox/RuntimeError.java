package lox;

public class RuntimeError extends RuntimeException {
  final Token token;

  RuntimeError(Token operator, String message) {
    super(message);
    this.token = operator;
  }
}
