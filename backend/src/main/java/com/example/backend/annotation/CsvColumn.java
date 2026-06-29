package com.example.backend.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * Specifies the order and header name of a field
 * when exporting an object to a CSV file.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CsvColumn {

    /**
     * Position of the column in the CSV file.
     *
     * @return column order
     */
    int order();

    /**
     * Header displayed in the CSV file.
     *
     * @return column header
     */
    String header();
}
