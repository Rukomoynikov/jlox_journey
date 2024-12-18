package lox;

public class Interpreter implements Expr.Visitor<Object> {
  void interpret(Expr expression) {
    try {
      Object value = evaluate(expression);
      System.out.println(stringify(value));
    } catch (RuntimeError error) {
      Lox.runtimeError(error);
    }
  }

  @Override
  public Object visitBinaryExpr(Expr.Binary expr) {
    Object left = evaluate(expr.left);
    Object right = evaluate(expr.right);

    switch (expr.operator.type) {
      case MINUS:
        return (double)left - (double)right;
      case SLASH:
        checkNumberOperands(expr.operator, left, right);
        return (double)left / (double)right;
      case STAR:
        checkNumberOperands(expr.operator, left, right);
        return (double)left * (double)right;
      case PLUS:
        if(left instanceof Double && right instanceof Double) {
          return (double)left + (double)right;
        }

        if(left instanceof String && right instanceof String) {
          return (String)left + (String)right;
        }

        break;
      case GREATER:
        checkNumberOperands(expr.operator, left, right);
        return (double)left > (double)right;
      case GREATER_EQUAL:
        checkNumberOperands(expr.operator, left, right);
        return (double)left >= (double)right;
      case LESS:
        checkNumberOperands(expr.operator, left, right);
        return (double)left < (double)right;
      case LESS_EQUAL:
        checkNumberOperands(expr.operator, left, right);
        return (double)left <= (double)right;
      case EQUAL_EQUAL:
        return isEqual(left, right);
      case BANG_EQUAL:
        return !isEqual(left, right);
    }
    return null;
  }

  @Override
  public Object visitGroupingExpr(Expr.Grouping expr) {
    return evaluate(expr.expression);
  }

  @Override
  public Object visitLiteralExpr(Expr.Literal expr) {
    return expr.value;
  }

  @Override
  public Object visitUnaryExpr(Expr.Unary expr) {
    Object right = evaluate(expr.right);

    switch (expr.operator.type) {
      case TokenType.MINUS:
        checkNumberOperand(expr.operator, right);
        return -(double)right;
      case TokenType.BANG:
        return !isTruthy(right);
    }

    return null;
  }

  private Object evaluate(Expr expr) {
    return expr.accept(this);
  }

  private boolean isTruthy(Object value) {
    if(value == null) return false;
    if(value instanceof Boolean) return (boolean)value;

    return true;
  }

  private boolean isEqual(Object a, Object b) {
    if(a == null && b == null) return true;
    if(a == null || b == null) return false;

    return a.equals(b);
  }

  private void checkNumberOperand(Token operator, Object operand) {
    if(operand instanceof Double) return;
    throw new RuntimeError(operator, "Operand must be a number");
  }

  private void checkNumberOperands(Token operator, Object... operands) {
    for(Object operand : operands) {
      checkNumberOperand(operator, operand);
    }
  }

  private String stringify(Object value) {
    if(value == null) return "null";
    if(value instanceof Double) return value.toString();

    return value.toString();
  }
}
