package com.inspirit.exceptions;

import java.util.NoSuchElementException;

public class IncorrectUserId extends NoSuchElementException {
    public IncorrectUserId() {
        super();
    }

    public IncorrectUserId(String s) {
        super(s);
    }
}
