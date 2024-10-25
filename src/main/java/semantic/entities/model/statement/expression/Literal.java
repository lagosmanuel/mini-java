package main.java.semantic.entities.model.statement.expression;

import main.java.model.Token;
import main.java.semantic.SymbolTable;
import main.java.semantic.entities.model.Type;
import main.java.semantic.entities.model.type.ClassType;
import main.java.semantic.entities.model.type.PrimitiveType;
import main.java.messages.SemanticErrorMessages;
import main.java.exeptions.SemanticException;

public class Literal extends Operand {
    private final Token value;

    public Literal(Token value) {
        super(value);
        this.value = value;
    }

    @Override
    public Type checkType() throws SemanticException {
        if (value == null) return null;
        return switch (value.getType()) {
            case intLiteral -> PrimitiveType.INT_TYPE;
            case floatLiteral -> PrimitiveType.FLOAT_TYPE;
            case charLiteral -> PrimitiveType.CHAR_TYPE;
            case trueLiteral, falseLiteral -> PrimitiveType.BOOLEAN_TYPE;
            case stringLiteral -> ClassType.STRING_TYPE;
            case nullLiteral -> ClassType.NULL_TYPE;
            default -> {
                SymbolTable.throwException(
                    SemanticErrorMessages.LITERAL_INVALID_TYPE,
                    value
                );
                yield null;
            }
        };
    }
}