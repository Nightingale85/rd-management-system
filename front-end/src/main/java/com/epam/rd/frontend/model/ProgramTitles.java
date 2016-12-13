package com.epam.rd.frontend.model;

import java.util.Map;
import java.util.Objects;

public class ProgramTitles {
    private Map<Long, String> titles;

    public ProgramTitles(Map<Long, String> titles) {
        this.titles = Objects.requireNonNull(titles, "ProgramTitles can't be NULL");
    }

    public Map<Long, String> getTitles() {
        return titles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProgramTitles programTitles = (ProgramTitles) o;
        return Objects.equals(titles, programTitles.titles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titles);
    }
}
