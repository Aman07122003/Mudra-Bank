package com.example.backend.converter;

import com.example.backend.annotation.CsvColumn;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;

@Component
public class PDFHttpMessageConverter {

    private static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("dd-MM-yyyy");

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

            Font titleFont =
                    FontFactory.getFont(
                            FontFactory.HELVETICA_BOLD,
                            20);

            Font subTitleFont =
                    FontFactory.getFont(
                            FontFactory.HELVETICA,
                            11);

            Font headerFont =
                    FontFactory.getFont(
                            FontFactory.HELVETICA_BOLD,
                            11,
                            Color.WHITE);

            Font bodyFont =
                    FontFactory.getFont(
                            FontFactory.HELVETICA,
                            10);

            Paragraph title =
                    new Paragraph(
                            "Mudra Bank",
                            titleFont);

            title.setAlignment(Element.ALIGN_CENTER);

            document.add(title);

            Paragraph reportTitle =
                    new Paragraph(
                            data.iterator()
                                    .next()
                                    .getClass()
                                    .getSimpleName()
                                    + " Report",
                            subTitleFont);

            reportTitle.setAlignment(Element.ALIGN_CENTER);

            document.add(reportTitle);

            Paragraph generated =
                    new Paragraph(
                            "Generated On : "
                                    + DATE_FORMAT.format(new Date()),
                            subTitleFont);

            generated.setSpacingAfter(20);

            document.add(generated);

            Class<?> type =
                    data.iterator().next().getClass();

            Field[] fields =
                    type.getDeclaredFields();

            Arrays.sort(
                    fields,
                    Comparator.comparingInt(field ->
                            field.getAnnotation(
                                    CsvColumn.class).order()));

            PdfPTable table =
                    new PdfPTable(fields.length);

            table.setWidthPercentage(100);

            // Header

            for (Field field : fields) {

                CsvColumn column =
                        field.getAnnotation(CsvColumn.class);

                PdfPCell cell =
                        new PdfPCell(
                                new Phrase(
                                        column.header(),
                                        headerFont));

                cell.setBackgroundColor(
                        new Color(37, 99, 235));

                cell.setHorizontalAlignment(
                        Element.ALIGN_CENTER);

                cell.setPadding(8);

                table.addCell(cell);
            }

            // Data

            boolean alternate = false;

            for (T object : data) {

                alternate = !alternate;

                for (Field field : fields) {

                    field.setAccessible(true);

                    Object value =
                            field.get(object);

                    String text;

                    if (value == null) {
                        text = "";
                    } else if (value instanceof Date date) {
                        text = DATE_FORMAT.format(date);
                    } else {
                        text = value.toString();
                    }

                    PdfPCell cell =
                            new PdfPCell(
                                    new Phrase(
                                            text,
                                            bodyFont));

                    cell.setPadding(6);

                    if (alternate) {
                        cell.setBackgroundColor(
                                new Color(245, 245, 245));
                    }

                    if (value instanceof Number
                            || value instanceof BigDecimal) {

                        cell.setHorizontalAlignment(
                                Element.ALIGN_RIGHT);

                    } else if (value instanceof Date) {

                        cell.setHorizontalAlignment(
                                Element.ALIGN_CENTER);

                    } else {

                        cell.setHorizontalAlignment(
                                Element.ALIGN_LEFT);
                    }

                    table.addCell(cell);
                }
            }

            document.add(table);

            document.add(new Paragraph(" "));

            Paragraph total =
                    new Paragraph(
                            "Total Records : "
                                    + data.size(),
                            subTitleFont);

            document.add(total);

            Paragraph footer =
                    new Paragraph(
                            "Generated by Mudra Bank",
                            subTitleFont);

            footer.setAlignment(Element.ALIGN_CENTER);

            footer.setSpacingBefore(15);

            document.add(footer);

            document.close();

            return outputStream.toByteArray();

        } catch (Exception ex) {

            throw new RuntimeException(
                    "Unable to generate PDF",
                    ex);
        }
    }
}