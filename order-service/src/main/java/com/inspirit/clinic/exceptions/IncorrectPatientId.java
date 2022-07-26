package com.inspirit.clinic.exceptions;

import java.util.NoSuchElementException;

public class IncorrectPatientId extends NoSuchElementException {
    public IncorrectPatientId() {
        super();
    }

    public IncorrectPatientId(String s) {
        super(s);
    }
}
