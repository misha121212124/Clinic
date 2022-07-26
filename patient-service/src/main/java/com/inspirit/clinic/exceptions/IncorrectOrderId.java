package com.inspirit.clinic.exceptions;

import java.util.NoSuchElementException;

public class IncorrectOrderId extends NoSuchElementException {
    public IncorrectOrderId() {
        super();
    }

    public IncorrectOrderId(String s) {
        super(s);
    }
}
