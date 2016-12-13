package com.epam.rd.dto;

import java.util.Objects;

public class ExceptionDto {
    private String reason;
    private Class classException;

    public ExceptionDto(String reason, Class classException) {
        this.reason = reason;
        this.classException = classException;
    }

    public ExceptionDto() {
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Class getClassException() {
        return classException;
    }

    public void setClassException(Class classException) {
        this.classException = classException;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExceptionDto that = (ExceptionDto) o;
        return Objects.equals(reason, that.reason) &&
                Objects.equals(classException, that.classException);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reason, classException);
    }
}
