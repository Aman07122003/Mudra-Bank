package com.example.backend.converter;

import com.example.backend.annotation.ExportColumn;
import com.opencsv.CSVWriter;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;

/**
 * Spring HTTP message converter that serializes a collection
 * of objects into CSV format.
 *
 * <p>The converter supports the {@code text/csv} media type and
 * uses the {@link ExportColumn} annotation to determine the
 * exported column order and header names.
 */
@Component
public class CSVHttpMessageConverter
        extends AbstractHttpMessageConverter<Collection<?>> {

    /**
     * Date format used while exporting date values.
     */
    private static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("dd-MM-yyyy");

    /**
     * Creates a converter supporting the text/csv media type.
     */
    public CSVHttpMessageConverter() {
        super(new MediaType("text", "csv"));
    }

    /**
     * Reading CSV into Java objects is not supported.
     *
     * @param clazz target collection type
     * @param inputMessage HTTP input message
     * @return never returns
     */
    @Override
    protected Collection<?> readInternal(
            @NonNull final Class<? extends Collection<?>> clazz,
            @NonNull final HttpInputMessage inputMessage) {

        throw new UnsupportedOperationException(
                "CSV import is not supported.");
    }

    /**
     * Indicates whether this converter supports the supplied class.
     *
     * @param clazz class being evaluated
     * @return true if the class is a Collection
     */
    @Override
    protected boolean supports(@NonNull final Class<?> clazz) {
        return Collection.class.isAssignableFrom(clazz);
    }

    /**
     * Writes the supplied collection as CSV to the HTTP response.
     *
     * <p>The first object's type is inspected using reflection to
     * determine the fields that should be exported. Fields annotated
     * with {@link ExportColumn} are ordered according to their
     * {@code order()} value and their {@code header()} values are
     * written as the CSV header row.
     *
     * <p>Each object in the collection is then converted into a CSV
     * record. {@link Date} values are formatted using the configured
     * date format, {@code null} values are exported as empty strings,
     * and all other values are written using their
     * {@code toString()} representation.
     *
     * @param data collection to export
     * @param outputMessage HTTP response
     * @throws IOException if the CSV document cannot be generated
     */
    @Override
    protected void writeInternal(
            final Collection<?> data,
            final HttpOutputMessage outputMessage)
            throws IOException {

        // Nothing to export.
        if (data == null || data.isEmpty()) {
            return;
        }

        try (
                Writer writer =
                        new OutputStreamWriter(
                                outputMessage.getBody());

                CSVWriter csvWriter =
                        new CSVWriter(writer)
        ) {

            // Determine the runtime type of the exported objects.
            Class<?> type =
                    data.iterator().next().getClass();

            // Retrieve all declared fields and sort them according
            // to the order specified by the ExportColumn annotation.
            Field[] fields =
                    type.getDeclaredFields();

            Arrays.sort(
                    fields,
                    Comparator.comparingInt(field ->
                            field.getAnnotation(ExportColumn.class).order()));

            // Build and write the CSV header row.
            String[] header =
                    new String[fields.length];

            for (int i = 0; i < fields.length; i++) {

                ExportColumn column =
                        fields[i].getAnnotation(ExportColumn.class);

                header[i] = column.header();
            }

            csvWriter.writeNext(header);

            // Write one CSV row for every exported object.
            for (Object object : data) {

                String[] values =
                        new String[fields.length];

                for (int i = 0; i < fields.length; i++) {

                    fields[i].setAccessible(true);

                    Object value =
                            fields[i].get(object);

                    // Export null values as empty cells.
                    if (value == null) {

                        values[i] = "";

                        // Format dates using the configured date format.
                    } else if (value instanceof Date date) {

                        values[i] =
                                DATE_FORMAT.format(date);

                        // Export all other values using their string representation.
                    } else {

                        values[i] =
                                value.toString();
                    }
                }

                csvWriter.writeNext(values);
            }

            // Ensure all buffered data is written.
            csvWriter.flush();

        } catch (Exception ex) {

            throw new IOException(
                    "Unable to generate CSV.",
                    ex);
        }
    }
}