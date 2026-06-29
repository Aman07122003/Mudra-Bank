package com.example.backend.converter;

import com.example.backend.annotation.CsvColumn;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

@Component
public class PDFHttpMessageConverter {

    public <T> byte[] toPdf(final Collection<T> data) {

        if (data == null || data.isEmpty()) {
            return new byte[0];
        }

        try {

            ByteArrayOutputStream outputStream =
                    new ByteArrayOutputStream();

            Document document = new Document();

            PdfWriter.getInstance(document, outputStream);

            document.open();

            Class<?> type =
                    data.iterator().next().getClass();

            Field[] fields =
                    type.getDeclaredFields();

            Arrays.sort(
                    fields,
                    Comparator.comparingInt(field ->
                            field.getAnnotation(CsvColumn.class).order()));

            PdfPTable table =
                    new PdfPTable(fields.length);

            // Header
            for (Field field : fields) {

                CsvColumn column =
                        field.getAnnotation(CsvColumn.class);

                table.addCell(new Phrase(column.header()));
            }

            // Data
            for (T object : data) {

                for (Field field : fields) {

                    field.setAccessible(true);

                    Object value =
                            field.get(object);

                    table.addCell(
                            value == null
                                    ? ""
                                    : value.toString());
                }
            }

            document.add(new Paragraph(type.getSimpleName()));
            document.add(new Paragraph(" "));
            document.add(table);

            document.close();

            return outputStream.toByteArray();

        } catch (Exception ex) {

            throw new RuntimeException(
                    "Unable to generate PDF",
                    ex);
        }
    }
}