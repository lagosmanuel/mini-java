package main.java.semantic.entities.model.statement;

import main.java.model.Token;
import main.java.semantic.SymbolTable;
import main.java.semantic.entities.model.Type;
import main.java.semantic.entities.model.Statement;
import main.java.semantic.entities.model.statement.expression.CompositeExpression;
import main.java.messages.SemanticErrorMessages;
import main.java.exeptions.SemanticException;

public class ForEach extends Statement {
    private final LocalVar declaration;
    private final CompositeExpression iterable;
    private final Statement statement;

    public ForEach(Token identifier, LocalVar declaration, CompositeExpression iterable, Statement statement) {
        super(identifier);
        this.declaration = declaration;
        this.iterable = iterable;
        this.statement = statement;
        if (statement != null) statement.setBreakable();
    }

    @Override
    public void check() throws SemanticException {
        if (checked()) return;
        super.check();

        if (declaration != null) {
            declaration.setParent(getParent());
            declaration.check();
        }
        Type iterableType = iterable != null? iterable.checkType():null;

        if (declaration != null && iterable != null && iterableType != null
            && !declaration.getType().compatible(iterableType)) {
            SymbolTable.throwException(
                String.format(
                    SemanticErrorMessages.FOREACH_TYPE_NOT_COMPATIBLE,
                    declaration.getType().getName(),
                    iterableType.getName()
                ),
                getIdentifier()
            );
        }

        if (statement != null) {
            statement.check();
            if (statement.hasReturn()) setReturnable();
        }

        if (getParent() != null && declaration != null)
            declaration.getLocalVars().forEach(getParent()::removeLocalVar);
    }
}