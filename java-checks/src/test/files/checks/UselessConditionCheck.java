public static class Class {

  private static class Class {
    Object field;

    Object method() {
      return null;
    }
  }

  private boolean field;

  public void assign(boolean parameter) {
    parameter = false;
    if (parameter) { // Noncompliant, always false
      if (parameter) { // Compliant, always false
      }
    }
    if (!parameter) { // Compliant, always true
      if (!parameter) { // Compliant, always true
      }
    }
  }

  public void conditional_and(boolean parameter1, boolean parameter2) {
    if (false && false) { // Noncompliant, always false
    }
    if (false && true) { // Noncompliant, always false
    }
    if (false && parameter2) { // Noncompliant, always false
    }
    if (true && false) { // Noncompliant, always false
    }
    if (true && true) { // Noncompliant, always true
    }
    if (true && parameter2) { // Compliant, unknown
    }
    if (parameter1 && false) { // Noncompliant, always false
    }
    if (parameter1 && true) { // Compliant, unknown
    }
    if (parameter1 && parameter2) { // Compliant, unknown
    }
  }

  public void conditional_or(boolean parameter1, boolean parameter2) {
    if (false || false) { // Noncompliant, always false
    }
    if (false || true) { // Noncompliant, always true
    }
    if (false || parameter2) { // Compliant, unknown
    }
    if (true || false) { // Noncompliant, always true
    }
    if (true || true) { // Noncompliant, always true
    }
    if (true || parameter2) { // Noncompliant, always true
    }
    if (parameter1 || false) { // Compliant, unknown
    }
    if (parameter1 || true) { // Noncompliant, always true
    }
    if (parameter1 || parameter2) { // Compliant, unknown
    }
  }

  public void identifier_field() {
    if (field == false && field == true) { // Compliant
    }
    if (field == false || field == true) { // Compliant
    }
  }

  public void identifier_local() {
    // local variables
    boolean localFalse = false;
    if (localFalse) { // Noncompliant, condition is always false
    }
    boolean localTrue = true;
    if (localTrue) { // Noncompliant, condition is always true
    }
    boolean localUnknown;
    if (localUnknown) { // Compliant
    }
  }

  public void identifier_parameter(boolean parameter) {
    if (parameter) { // Compliant
    }
    if (parameter && !parameter) { // Noncompliant, condition is always false
    }
    if (parameter || !parameter) { // Noncompliant, condition is always true
    }
  }

  public void instanceOf() {
    Object object = new Object();
    if (object instanceof Object) { // Compliant, false negative
    }
    if (object instanceof String) { // Compliant
    }
    object = "string";
    if (object instanceof String) { // Compliant, false negative
    }
  }

  public void literals() {
    // literals
    if (false) { // Noncompliant, always false
    }
    if (true) { // Noncompliant, always true
    }
  }

  public void member_select() {
    // member select
    Class instance = new Class();
    if (instance.field != null && instance.field == null) { // Compliant
    }
  }

  public void method_invocation() {
    Class instance = new Class();
    if (instance.method() != null && instance.method() == null) { // Compliant
    }
  }

  public void unary_logical_complement() {
    // unary logical complement
    if (!false) { // Noncompliant, always true
    }
    if (!true) { // Noncompliant, always false
    }
  }

  public void relational_equal(boolean parameter1, boolean parameter2, boolean condition) {
    if (parameter1 == parameter2) {
      if (parameter1 == parameter2) { // Noncompliant, always true
      }
      if (parameter1 >= parameter2) { // Noncompliant, always true
      }
      if (parameter1 > parameter2) { // Noncompliant, always false
      }
      if (parameter1 <= parameter2) { // Noncompliant, always true
      }
      if (parameter1 < parameter2) { // Noncompliant, always false
      }
      if (parameter1 != parameter2) { // Noncompliant, always false
      }
    }
    if (parameter1 == parameter2) { // Compliant
    }
  }

  public void relational_ge(boolean parameter1, boolean parameter2) {
    if (parameter1 >= parameter2) {
      if (parameter1 >= parameter2) { // Noncompliant, always true
      }
      if (parameter1 < parameter2) { // Noncompliant, always false
      }
    }
    if (parameter1 >= parameter2) {
      if (parameter1 == parameter2) { // Compliant
      }
    }
    if (parameter1 >= parameter2) {
      if (parameter1 > parameter2) { // Compliant
      }
    }
    if (parameter1 >= parameter2) {
      if (parameter1 <= parameter2) { // Compliant
      }
    }
    if (parameter1 >= parameter2) {
      if (parameter1 != parameter2) { // Compliant
      }
    }
  }

  public void relational_g(boolean parameter1, boolean parameter2) {
    if (parameter1 > parameter2) {
      if (parameter1 == parameter2) { // Noncompliant, always false
      }
      if (parameter1 >= parameter2) { // Noncompliant, always true
      }
      if (parameter1 > parameter2) { // Noncompliant, always true
      }
      if (parameter1 <= parameter2) { // Noncompliant, always false
      }
      if (parameter1 < parameter2) { // Noncompliant, always false
      }
      if (parameter1 != parameter2) { // Noncompliant, always true
      }
    }
    if (parameter1 > parameter2) { // Compliant
    }
  }
  public void test_invalidate_relations(int i, int j, int k) {
    if (j > i && j < k) {
      i = 1;
      if (i < j) { // Compliant
      }
      if (j > i) { // Compliant
      }
      if (j < k) { // Noncompliant, always true
      }
      if (k > j) {// Noncompliant, always true
      }
    }
    if (i < j) {
      ++i;
      if (i < j) { // Compliant
      }
    }
    if (i < j) {
      i--;
      if (i < j) { // Compliant
      }
    }
  }
  public void statement_assign_variable() {
    boolean condition1 = true, condition2;
    if (condition1) { // Noncompliant, condition is always true
    }
    condition2 = true;
    if (condition2) { // Noncompliant, condition is always true
    }
  }

  public void statement_control_flow(boolean condition1, boolean condition2, boolean condition3, boolean condition4) {
    if (condition1) {
      if (condition1) { // Noncompliant, condition is always true
      }
      break;
      if (condition1) { // Compliant, unreachable
      }
    }
    if (condition2) {
      if (condition2) { // Noncompliant, condition is always true
      }
      continue;
      if (condition2) { // Compliant, unreachable
      }
    }
    if (condition3) {
      if (condition3) { // Noncompliant, condition is always true
      }
      return;
      if (condition3) { // Compliant, unreachable
      }
    }
    if (condition4) {
      if (condition4) { // Noncompliant, condition is always true
      }
      throw new RuntimeException("");
      if (condition4) { // Compliant, unreachable
      }
    }
  }

  public void statement_do_while(boolean parameter1, boolean parameter2) {
    if (parameter1 == parameter2) {
      do {
      } while (parameter1 == parameter2); // False negative, while loop
    }
    do {
    } while (parameter1 == parameter2); // Compliant
    if (parameter1 == parameter2) { // Noncompliant, condition is always false
    }
  }

  public void statement_for(boolean parameter1, boolean parameter2) {
    for (; parameter1 == parameter2;) {
      if (parameter1 == parameter2) { // Noncompliant, condition is always true
      }
    }
    if (parameter1 == parameter2) { // False negative
    }
  }

  public void statement_if(boolean parameter1, boolean parameter2) {
    if (parameter1 == parameter2) {
      if (parameter1 == parameter2) { // Noncompliant, condition is always true
      }
    }
    if (parameter1 == parameter2) { // Compliant
    }
  }

  public void statement_switch() {
    switch (expression) {
      case 1:
      case 2:
      case 3:
        ;
    }
    condition = true;
    if (condition) { // Compliant, unreachable
    }
  }

  public void statement_synchronized(boolean condition) {
    synchronized (condition = true) {
      if (condition) { // Noncompliant, condition is always true
      }
    }
  }

  public void statement_while(boolean parameter1, boolean parameter2) {
    while (parameter1 == parameter2) {
      if (parameter1 == parameter2) { // Noncompliant, condition is always true
      }
    }
    if (parameter1 == parameter2) { // False negative
    }
  }

  public void tests(boolean parameter1, boolean parameter2, boolean condition) {
    if (parameter1 == parameter2) { // Compliant
    }
    if (parameter1 == parameter2 && parameter1 == parameter2) { // Compliant
    }
    if (parameter1 == parameter2 || parameter1 == parameter2) { // Compliant
    }
    if (parameter1 == parameter2 && parameter1 != parameter2) { // Noncompliant, always false
    }
    if (parameter1 == parameter2 && parameter1 > parameter2) { // Noncompliant, always false
    }
    if (parameter1 == parameter2 && parameter1 < parameter2) { // Noncompliant, always false
    }
    if (parameter1 == parameter2 || parameter1 != parameter2) { // Noncompliant, always true
    }
    if (condition && !condition) { // Noncompliant, always false
    }
    if (condition || !condition) { // Noncompliant, always true
    }
    if ((parameter1 == parameter2 || condition) && !(parameter1 == parameter2 || condition)) { // Noncompliant, always false
    }
    if ((parameter1 == parameter2 || condition) || !(parameter1 == parameter2 || condition)) { // Noncompliant, always true
    }
    if (!(parameter1 == parameter2 || condition) && (parameter1 == parameter2 || condition)) { // Noncompliant, always false
    }
    if (!(parameter1 == parameter2 || condition) || (parameter1 == parameter2 || condition)) { // Noncompliant, always true
    }
  }

  public <T> T newQualifiedIdentifier(T param) {
    Object result;
    return (T) result;
  }

  public void test_assign_invalidate(boolean condition) {
    boolean local1 = true;
    do {
      if (local1) { // Compliant
      }
      local1 = false;
      if (local1) { // Noncompliant, always false
      }
    } while (condition);
    if (local1) { // Compliant
    }
  }

  public void test_assign_invalidate(boolean condition) {
    boolean local2 = true;
    for (Object object : new ArrayList<Object>()) {
      if (local2) { // Compliant
      }
      local2 = false;
      if (local2) { // Noncompliant, always false
      }
    }
    if (local2) { // Compliant
    }
  }

  public void test_assign_invalidate(boolean condition) {
    boolean local3 = true;
    for (; condition;) {
      if (local3) { // Compliant
      }
      local3 = false;
      if (local3) { // Noncompliant, always false
      }
    }
    if (local3) { // Compliant
    }
  }

  public void test_assign_invalidate(boolean condition) {
    boolean local2 = true;
    while (condition) {
      if (local2) { // Compliant
      }
      local2 = false;
      if (local2) { // Noncompliant, always false
      }
    }
    if (local2) { // Compliant
    }
  }

  public void test_label() {
    label: while (true) { // Compliant
    }
  }

  public void statement_if2(boolean parameter1, boolean parameter2) {
    if (parameter1 == parameter2) {
      if (parameter1) {
        parameter1 = false;
      } else {
        parameter1 = true;
      }
      if (parameter1) { // Compliant
      }
    } else {
      if (parameter1) {
        parameter1 = false;
      } else {
        parameter1 = true;
      }
    }
    if (parameter1) { // Compliant
    }
  }

  public void test_assign(boolean param1, boolean param2, bool falseParam, bool trueParam) {
    boolean boolAnd1 = true;
    boolAnd1 = param1 && param2;
    if (!boolAnd1) { // Compliant
    }

    boolean boolAnd2 = true;
    boolAnd2 = falseParam && param2;
    if (!boolAnd2) { // Compliant
    }

    boolean boolAnd3 = true;
    boolAnd3 = param1 && falseParam;
    if (!boolAnd3) { // Compliant
    }

    boolean boolOr1 = true;
    boolOr1 = param1 || param2;
    if (!boolOr1) { // Compliant
    }

    boolean boolOr2 = true;
    boolOr2 = trueParam || param1;
    if (!boolOr2) { // Compliant
    }

    boolean boolOr3 = true;
    boolOr3 = param1 || trueParam;
    if (!boolOr3) { // Compliant
    }
  }

  public void test_merge(int a, int b, int c, int d) {
    if (a < b) {
      return;
    }
    if (a >= b) { // Noncompliant, always true
    }

    if (c < d || c <= d) {
    } else {
      return;
    }
    if (c <= d) { // Noncompliant, always true
    }
  }

  public void relationa_le(boolean parameter1, boolean parameter2) {
    if (parameter1 <= parameter2) {
      if (parameter1 > parameter2) { // Noncompliant, always false
      }
      if (parameter1 <= parameter2) { // Noncompliant, always true
      }
    }
    if (parameter1 <= parameter2) {
      if (parameter1 == parameter2) { // Compliant
      }
    }
    if (parameter1 <= parameter2) {
      if (parameter1 >= parameter2) { // Compliant
      }
    }
    if (parameter1 <= parameter2) {
      if (parameter1 < parameter2) { // Compliant
      }
    }
    if (parameter1 <= parameter2) {
      if (parameter1 != parameter2) { // Compliant
      }
    }
  }

  public void relational_l(boolean parameter1, boolean parameter2) {
    if (parameter1 < parameter2) {
      if (parameter1 == parameter2) { // Noncompliant, always false
      }
      if (parameter1 >= parameter2) { // Noncompliant, always false
      }
      if (parameter1 > parameter2) { // Noncompliant, always false
      }
      if (parameter1 <= parameter2) { // Noncompliant, always true
      }
      if (parameter1 < parameter2) { // Noncompliant, always true
      }
      if (parameter1 != parameter2) { // Noncompliant, always true
      }
    }
    if (parameter1 < parameter2) { // Compliant
    }
  }

  public void relational_ne(boolean parameter1, boolean parameter2) {
    if (parameter1 != parameter2) {
      if (parameter1 == parameter2) { // Noncompliant, always false
      }
      if (parameter1 != parameter2) { // Noncompliant, always true
      }
    }
    if (parameter1 != parameter2) {
      if (parameter1 >= parameter2) { // Compliant
      }
    }
    if (parameter1 != parameter2) {
      if (parameter1 > parameter2) { // Compliant
      }
    }
    if (parameter1 != parameter2) {
      if (parameter1 <= parameter2) { // Compliant
      }
    }
    if (parameter1 != parameter2) {
      if (parameter1 < parameter2) { // Compliant
      }
    }
  }

}
