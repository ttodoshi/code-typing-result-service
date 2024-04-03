package org.codetyping.result.exceptions.classes;

public class CodeExampleNotFoundException extends RuntimeException {
    public CodeExampleNotFoundException(String UUID) {
        super("code example by UUID '" + UUID + "' not found");
    }
}
