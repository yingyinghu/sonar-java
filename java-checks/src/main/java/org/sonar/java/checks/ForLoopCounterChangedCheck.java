/*
 * SonarQube Java
 * Copyright (C) 2012 SonarSource
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.java.checks;

import com.google.common.collect.Sets;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.AssignmentExpressionTree;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ForStatementTree;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.StatementTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.UnaryExpressionTree;
import org.sonar.plugins.java.api.tree.VariableTree;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

import java.util.Set;

@Rule(
  key = "ForLoopCounterChangedCheck",
  name = "Loop invariants should not be calculated inside the loop",
  tags = {"performance"},
  priority = Priority.MAJOR)
@ActivatedByDefault
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.CPU_EFFICIENCY)
@SqaleConstantRemediation("3min")
public class ForLoopCounterChangedCheck extends BaseTreeVisitor implements JavaFileScanner {

  private final Set<String> loopCounters = Sets.newHashSet();
  private JavaFileScannerContext context;

  @Override
  public void scanFile(JavaFileScannerContext context) {
    this.context = context;
    loopCounters.clear();
    scan(context.getTree());
  }

  @Override
  public void visitForStatement(ForStatementTree tree) {
    Set<String> pendingLoopCounters = Sets.newHashSet();
    for (StatementTree statementTree : tree.initializer()) {
      if (statementTree.is(Tree.Kind.VARIABLE)) {
        pendingLoopCounters.add(((VariableTree) statementTree).simpleName().name());
      }
    }
    scan(tree.initializer());
    scan(tree.condition());
    scan(tree.update());
    loopCounters.addAll(pendingLoopCounters);
    scan(tree.statement());
    loopCounters.removeAll(pendingLoopCounters);
  }

  @Override
  public void visitAssignmentExpression(AssignmentExpressionTree tree) {
    if (tree.variable().is(Tree.Kind.IDENTIFIER)) {
      checkIdentifier((IdentifierTree) tree.variable());
    }
    super.visitAssignmentExpression(tree);
  }

  @Override
  public void visitUnaryExpression(UnaryExpressionTree tree) {
    if ((isIncrement(tree) || isDecrement(tree)) && tree.expression().is(Tree.Kind.IDENTIFIER)) {
      checkIdentifier((IdentifierTree) tree.expression());
    }
    super.visitUnaryExpression(tree);
  }

  private boolean isIncrement(UnaryExpressionTree tree) {
    return tree.is(Tree.Kind.PREFIX_INCREMENT) || tree.is(Tree.Kind.POSTFIX_INCREMENT);
  }

  private boolean isDecrement(UnaryExpressionTree tree) {
    return tree.is(Tree.Kind.POSTFIX_DECREMENT) || tree.is(Tree.Kind.PREFIX_DECREMENT);
  }

  private void checkIdentifier(IdentifierTree identifierTree) {
    if (loopCounters.contains(identifierTree.name())) {
      context.addIssue(identifierTree, this, "Refactor the code in order to not assign to this loop counter from within the loop body.");
    }
  }


}
