package main.java.semantic.entities.model.statement;

import main.java.model.Token;
import main.java.model.TokenType;
import main.java.semantic.SymbolTable;
import main.java.semantic.entities.model.Type;
import main.java.semantic.entities.model.statement.expression.Expression;
import main.java.semantic.entities.model.statement.expression.CompositeExpression;
import main.java.messages.SemanticErrorMessages;
import main.java.exeptions.SemanticException;

public class Assignment extends Expression {
    private final CompositeExpression left;
    private final CompositeExpression right;
    private final Token operator;

    public Assignment(CompositeExpression left, CompositeExpression right, Token operator) {
        super(operator);
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public boolean isStatement() {
        return true;
    }

    @Override
    public Type checkType() throws SemanticException {
        if (getType() != null) return getType();
        Type leftType = left != null? left.checkType():null;
        Type rightType = right != null? right.checkType():null;

        if (leftType != null && rightType != null && !leftType.compatible(rightType)) {
            SymbolTable.throwException(
                String.format(
                    SemanticErrorMessages.TYPE_NOT_COMPATIBLE,
                    leftType.getName(),
                    rightType.getName()
                ),
                getIdentifier()
            );
        }

        if (operator != null && operator.getType() != TokenType.opAssign &&
            leftType != null && !leftType.isNumeric()) {
            SymbolTable.throwException(
                String.format(
                    SemanticErrorMessages.ASSIGN_INCREMENT_TYPE_NOT_NUMERIC,
                    operator.getLexeme(),
                    leftType.getName()
                ),
                operator
            );
        }

        if (left != null && !left.isAssignable()) {
            SymbolTable.throwException(
                SemanticErrorMessages.ASSIGNMENT_NOT_ASIGNABLE,
                getIdentifier()
            );
        }

        setType(leftType);
        return leftType;
    }
}