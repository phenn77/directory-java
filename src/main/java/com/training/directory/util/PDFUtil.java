package com.training.directory.util;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

@Log4j2
public class PDFUtil {

    private PDFUtil() {

    }

//    public static void generateDocument(List<Map<String, Object>> queryResults) {
//        try (Document document = new Document()) {
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            PdfWriter.getInstance(document, outputStream);
//            document.open();
//
//            // Write column names
//            Map<String, Object> firstRow = queryResults.get(0);
//            for (String column : firstRow.keySet()) {
//                Font boldFont = new Font(Font.HELVETICA, 12, Font.BOLD);
//                Paragraph paragraph = new Paragraph(column, boldFont);
//                document.add(paragraph);
//            }
//            document.add(new Paragraph("\n"));
//
//            // Write data rows
//            for (Map<String, Object> row : queryResults) {
//                for (Object value : row.values()) {
//                    Paragraph paragraph = new Paragraph(value.toString());
//                    document.add(paragraph);
//                }
//                document.add(new Paragraph("\n"));
//            }
//
//        } catch (Exception e) {
//
//        }
//
//    }

    public static Document createDocument(String titleName) {
        var document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, new ByteArrayOutputStream());

        // Opening the created document to modify it
        document.open();

        // Creating font
        // Setting font style and size
        var fontTitle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontTitle.setSize(20);

        // Creating paragraph
        var title = new Paragraph(titleName, fontTitle);
        // Aligning the paragraph in document
        title.setAlignment(Element.ALIGN_CENTER);
        // Adding the created paragraph in document
        document.add(title);

        return document;
    }

    public static void generatePDF(
            String titleName,
            Map<String, Object> data,
            Boolean addTable,
            List<Map<String, Object>> tableData) {
        // Creating the Object of Document
        try (var document = new Document(PageSize.A4)) {
            // Getting instance of PdfWriter
            PdfWriter.getInstance(document, new ByteArrayOutputStream());

            // Opening the created document to modify it
            document.open();

            // Creating font
            // Setting font style and size
            var fontTitle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
            fontTitle.setSize(20);

            // Creating paragraph
            var title = new Paragraph(titleName, fontTitle);
            // Aligning the paragraph in document
            title.setAlignment(Element.ALIGN_CENTER);
            // Adding the created paragraph in document
            document.add(title);

            if (BooleanUtils.isTrue(addTable) && !tableData.isEmpty()) {
                var table = createTable(tableData);

                document.add(table);
            }
        } catch (DocumentException e) {
            log.error("Error generating PDF. Error: {}", (Object) ExceptionUtils.getRootCauseStackTrace(e));
        }
    }

    private static PdfPTable createTable(List<Map<String, Object>> data) {
        // Retrieve 1st data to generate header
        var headerData = data.get(0);

        var headerSize = headerData.size();

        var table = new PdfPTable(headerSize);

        // Setting width of table, its columns and spacing
        table.setWidthPercentage(100f);
//        table.setWidths(new int[] { 3, 3, 3 });
        table.setSpacingBefore(5);

        var font = FontFactory.getFont(FontFactory.TIMES_ROMAN);

        headerData
                .keySet()
                .forEach(name -> {
                    // Create Table Cells for table header
                    var cell = new PdfPCell();

                    // Setting the background color and padding
                    cell.setBackgroundColor(Color.MAGENTA);
                    cell.setPadding(5);

                    // Creating font
                    // Setting font style and size
                    font.setColor(Color.WHITE);

                    // Adding headings in the created table cell/ header
                    // Adding Cell to table
                    cell.setPhrase(new Phrase(name, font));
                    table.addCell(cell);
                });

        data.forEach(x -> x.values()
                .forEach(field -> {
                    font.setColor(Color.WHITE);

                    var cell = new PdfPCell();
                    cell.setBackgroundColor(Color.GRAY);

                    cell.setPhrase(new Phrase(String.valueOf(cell), font));
                    table.addCell(cell);
                }));

        return table;
    }

//    https://techblogstation.com/spring-boot/export-data-to-pdf-in-spring-boot/
//    public static void generate() {
//
//        // Creating the Object of Document
//        Document document = new Document(PageSize.A4);
//
//        // Getting instance of PdfWriter
//        PdfWriter.getInstance(document, response.getOutputStream());
//
//        // Opening the created document to modify it
//        document.open();
//
//        // Creating font
//        // Setting font style and size
//        Font fontTiltle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
//        fontTiltle.setSize(20);
//
//        // Creating paragraph
//        Paragraph paragraph = new Paragraph("List Of Students", fontTiltle);
//
//        // Aligning the paragraph in document
//        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
//
//        // Adding the created paragraph in document
//        document.add(paragraph);
//
//        // Creating a table of 3 columns
//        PdfPTable table = new PdfPTable(3);
//
//        // Setting width of table, its columns and spacing
//        table.setWidthPercentage(100f);
//        table.setWidths(new int[] { 3, 3, 3 });
//        table.setSpacingBefore(5);
//
//        // Create Table Cells for table header
//        PdfPCell cell = new PdfPCell();
//
//        // Setting the background color and padding
//        cell.setBackgroundColor(CMYKColor.MAGENTA);
//        cell.setPadding(5);
//
//        // Creating font
//        // Setting font style and size
//        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
//        font.setColor(CMYKColor.WHITE);
//
//        // Adding headings in the created table cell/ header
//        // Adding Cell to table
//        cell.setPhrase(new Phrase("ID", font));
//        table.addCell(cell);
//        cell.setPhrase(new Phrase("Student Name", font));
//        table.addCell(cell);
//        cell.setPhrase(new Phrase("Section", font));
//        table.addCell(cell);
//
//        // Iterating over the list of students
//        for (Student student : studentList) {
//            // Adding student id
//            table.addCell(String.valueOf(student.getId()));
//            // Adding student name
//            table.addCell(student.getName());
//            // Adding student section
//            table.addCell(student.getSection());
//        }
//        // Adding the created table to document
//        document.add(table);
//
//        // Closing the document
//        document.close();
//
//    }
}
