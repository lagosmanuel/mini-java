package main.java.semantic.entities.predefined;

import main.java.model.Token;
import main.java.model.TokenType;
import main.java.semantic.entities.model.statement.Block;
import main.java.semantic.entities.model.statement.Return;
import main.java.semantic.entities.model.statement.expression.Literal;

public class BlockPredefined extends Block {
    private final MethodPredefined method;

    public BlockPredefined(MethodPredefined method) {
        super(new Token(
            main.java.model.TokenType.leftBrace,
            "{",
            0,
            0
        ));
        this.method = method;
        if (method.equals(MethodPredefined.read)) addReturn0();
    }

    @Override
    public void generate() {
        switch (method) {
            // TODO
        }
    }

    private void addReturn0() {
        addStatement(new Return(
            new Token(
                TokenType.kwReturn,
                "return",
                0,
                0
            ),
            new Literal(
                new Token(
                    TokenType.intLiteral,
                    "0",
                    0,
                    0
                )
            )
        ));
    }
}
