package com.example.backend.converter;

import com.example.backend.annotation.ExportColumn;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Element;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.stereotype.Component;

import java.awt.Color;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;

/**
 * Spring HTTP message converter that serializes a collection
 * of objects into PDF format.
 *
 * <p>The converter uses the {@link ExportColumn} annotation
 * to determine the exported column order and header names.
 */
@Component
public class PDFHttpMessageConverter
        extends AbstractHttpMessageConverter<Collection<?>> {

    /**
     * Date format used while exporting date values.
     */
    private static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("dd-MM-yyyy");

    /**
     * Creates a converter supporting application/pdf.
     */
    public PDFHttpMessageConverter() {
        super(MediaType.APPLICATION_PDF);
    }

    /**
     * Reading PDF into Java objects is not supported.
     *
     * @param clazz target collection type
     * @param inputMessage HTTP request
     * @return never returns
     */
    protected Collection<?> readInternal(
            final Class<? extends Collection<?>> clazz,
            final HttpInputMessage inputMessage) {

        throw new UnsupportedOperationException(
                "PDF import is not supported.");
    }

    /**
     * Indicates whether this converter supports the supplied class.
     *
     * @param clazz class being evaluated
     * @return true if the class is a Collection
     */
    protected boolean supports(final Class<?> clazz) {
        return Collection.class.isAssignableFrom(clazz);
    }

    /**
     * Writes the supplied collection as a PDF document.
     *
     * <p>The first object's type is inspected using reflection to
     * determine the fields that should be exported. Fields annotated
     * with {@link ExportColumn} are ordered according to their
     * {@code order()} value and their {@code header()} values are
     * used to create the table header.
     *
     * <p>The generated PDF contains a title followed by a table
     * representing the exported objects. {@link Date} values are
     * formatted using the configured date format, {@code null}
     * values are rendered as empty strings, and alternating row
     * colors are applied to improve readability.
     *
     * @param data collection to export
     * @param outputMessage HTTP response
     * @throws IOException if PDF generation fails
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

        try {

            // Create the PDF document and attach it to the HTTP response.
            Document document =
                    new Document();

            PdfWriter.getInstance(
                    document,
                    outputMessage.getBody());

            document.open();

            // Fonts used throughout the document.
            Font titleFont =
                    FontFactory.getFont(
                            FontFactory.HELVETICA_BOLD,
                            18);

            Font headerFont =
                    FontFactory.getFont(
                            FontFactory.HELVETICA_BOLD,
                            11,
                            Color.WHITE);

            Font dataFont =
                    FontFactory.getFont(
                            FontFactory.HELVETICA,
                            10);

            // Determine the runtime type of the exported objects.
            Class<?> type =
                    data.iterator().next().getClass();

            // Add the document title.
            Paragraph title =
                    new Paragraph(
                            type.getSimpleName() + " Report",
                            titleFont);

            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);

            document.add(title);

            // Retrieve and order fields using the ExportColumn annotation.
            Field[] fields =
                    type.getDeclaredFields();

            Arrays.sort(
                    fields,
                    Comparator.comparingInt(field ->
                            field.getAnnotation(ExportColumn.class).order()));

            // Create the table that will contain the exported data.
            PdfPTable table =
                    new PdfPTable(fields.length);

            table.setWidthPercentage(100);
            table.setSpacingBefore(10);

            // Create the table header.
            for (Field field : fields) {

                ExportColumn column =
                        field.getAnnotation(ExportColumn.class);

                PdfPCell cell =
                        new PdfPCell(
                                new Phrase(
                                        column.header(),
                                        headerFont));

                cell.setHorizontalAlignment(
                        Element.ALIGN_CENTER);

                cell.setBackgroundColor(
                        new Color(33, 150, 243));

                cell.setPadding(8);

                table.addCell(cell);
            }

            // Used to alternate row background colors.
            boolean alternate = false;

            // Write one table row for each exported object.
            for (Object object : data) {

                for (Field field : fields) {

                    field.setAccessible(true);

                    Object value =
                            field.get(object);

                    String text;

                    // Export null values as empty cells.
                    if (value == null) {

                        text = "";

                        // Format dates using the configured date format.
                    } else if (value instanceof Date date) {

                        text =
                                DATE_FORMAT.format(date);

                        // Export all other values using their string representation.
                    } else {

                        text =
                                value.toString();
                    }

                    PdfPCell cell =
                            new PdfPCell(
                                    new Phrase(
                                            text,
                                            dataFont));

                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setPadding(6);

                    // Apply alternating row colors for readability.
                    if (alternate) {
                        cell.setBackgroundColor(
                                new Color(245, 245, 245));
                    }

                    table.addCell(cell);
                }

                alternate = !alternate;
            }

            // Add the completed table to the document.
            document.add(table);

            // Finish writing the PDF.
            document.close();

        } catch (DocumentException | IllegalAccessException ex) {

            throw new IOException(
                    "Unable to generate PDF.",
                    ex);
        }
    }
}