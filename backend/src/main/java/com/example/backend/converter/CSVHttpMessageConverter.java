package com.example.backend.converter;

import com.example.backend.annotation.CsvColumn;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Converts a collection of objects into CSV format.
 *
 * <p>The converter uses the {@link CsvColumn} annotation
 * to determine the column order and header names.
 */
@Component
public class CSVHttpMessageConverter {
    private static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("dd-MM-yyyy");

    /**
     * Converts the given collection into CSV format.
     *
     * @param data collection to convert
     * @param <T> object type
     * @return CSV representation of the collection
     */
    public <T> String toCsv(final Collection<T> data) {

        if (data == null || data.isEmpty()) {
            return "";
        }

        try (
                StringWriter stringWriter = new StringWriter();
                CSVWriter csvWriter = new CSVWriter(stringWriter)
        ) {

            Class<?> type = data.iterator().next().getClass();

            Field[] fields = type.getDeclaredFields();

            Arrays.sort(
                    fields,
                    Comparator.comparingInt(field ->
                            field.getAnnotation(CsvColumn.class).order()));

            String[] header = new String[fields.length];

            for (int i = 0; i < fields.length; i++) {
                header[i] = fields[i]
                        .getAnnotation(CsvColumn.class)
                        .header();
            }

            csvWriter.writeNext(header);

            for (T object : data) {

                String[] values = new String[fields.length];

                for (int i = 0; i < fields.length; i++) {

                    fields[i].setAccessible(true);

                    Object value = fields[i].get(object);

                    if (value == null) {
                        values[i] = "";
                    } else if (value instanceof Date date) {
                        values[i] = DATE_FORMAT.format(date);
                    } else {
                        values[i] = value.toString();
                    }
                }

                csvWriter.writeNext(values);
            }

            return stringWriter.toString();

        } catch (Exception ex) {

            throw new RuntimeException(
                    "Unable to generate CSV",
                    ex);
        }
    }
}