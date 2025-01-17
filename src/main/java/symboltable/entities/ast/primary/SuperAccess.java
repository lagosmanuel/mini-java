package main.java.symboltable.entities.ast.primary;

import main.java.model.Token;
import main.java.symboltable.SymbolTable;
import main.java.symboltable.entities.Class;
import main.java.symboltable.entities.Constructor;
import main.java.symboltable.entities.type.Type;
import main.java.symboltable.entities.Unit;
import main.java.symboltable.entities.ast.Access;
import main.java.symboltable.entities.ast.expression.Expression;
import main.java.codegen.Instruction;
import main.java.codegen.Comment;
import main.java.config.CodegenConfig;
import main.java.messages.SemanticErrorMessages;
import main.java.exeptions.SemanticException;

import java.util.List;

public class SuperAccess extends Access {
    private final Token className;
    private final List<Expression> arguments;
    private Constructor constructor;
    private String super_vt_label;

    @Override
    public boolean isAssignable() {
        return getChained() != null && getChained().isAssignable();
    }

    @Override
    public boolean isStatement() {
        return (arguments != null && getChained() == null) ||
               (arguments == null && getChained() != null && getChained().isStatement());
    }

    public SuperAccess(Token identifier, Token className, List<Expression> arguments) {
        super(identifier);
        this.className = className;
        this.arguments = arguments;
    }

    @Override
    public Type checkType() throws SemanticException {
        if (className == null) return null;
        Class myclass = SymbolTable.getClass(className.getLexeme());
        Class superclass = myclass != null? SymbolTable.getClass(myclass.getSuperType().getName()):null;
        Type supertype = superclass != null? Type.createType(superclass.getToken(), null):null;
        this.super_vt_label = superclass != null? superclass.getVTLabel():null;

        if (SymbolTable.actualUnit.isStatic()) {
            SymbolTable.throwException(
                SemanticErrorMessages.SUPER_ACCESS_STATIC,
                getIdentifier()
            );
            return null;
        }

        if (supertype == null) return null;

        if (arguments != null) {
            constructor = superclass.getConstructor(Unit.getMangledName(superclass.getName(), arguments.size()));
            if (constructor == null) {
                SymbolTable.throwException(
                    String.format(
                        SemanticErrorMessages.CONSTRUCTOR_NOT_FOUND,
                        arguments.size(),
                        superclass.getName()
                    ),
                    getIdentifier()
                );
            } else if (superclass != SymbolTable.actualClass && constructor.isPrivate()) {
                SymbolTable.throwException(
                    String.format(
                        SemanticErrorMessages.CONSTRUCTOR_PRIVATE,
                        arguments.size(),
                        superclass.getName()
                    ),
                    getIdentifier()
                );
            } else if (getChained() != null) {
                SymbolTable.throwException(
                    SemanticErrorMessages.SUPER_ACCESS_CHAINED,
                    getChained().getIdentifier()
                );
            } else constructor.argumentsMatch(arguments, getIdentifier());
        } else return getChained() != null? getChained().checkType(supertype):supertype;

        return null;
    }

    public boolean isConstructorCall() {
        return arguments != null;
    }

    @Override
    public void generate() {
        if (isConstructorCall()) {
            arguments.forEach(Expression::generate);
            SymbolTable.getGenerator().write(
                Instruction.LOAD.toString(),
                CodegenConfig.OFFSET_THIS,
                Comment.LOAD_SUPER
            );
            SymbolTable.getGenerator().write(
                Instruction.PUSH.toString(),
                constructor.getLabel(),
                Comment.SUPER_LOAD.formatted(constructor.getLabel())
            );
            SymbolTable.getGenerator().write(
                Instruction.CALL.toString(),
                Comment.SUPER_CALL.formatted(constructor.getLabel())
            );
        } else {
            SymbolTable.getGenerator().write(
                Instruction.LOAD.toString(),
                CodegenConfig.OFFSET_THIS,
                Comment.LOAD_SUPER
            );
        }
        if (getChained() != null) getChained().generate(super_vt_label);
    }

    public boolean isVoid() {
        return isConstructorCall() || (getChained() != null && getChained().isVoid());
    }
}
