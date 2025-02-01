package com.kc1vmz.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.List;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.kc1vmz.object.APRSMessage;
import com.kc1vmz.object.ApplicationContext;
import com.kc1vmz.object.ApplicationVersion;

public class NetParticipantReport {

    private final static int MAX_ROWS = 29;

    /**
     * create a net participation report based on ICS-309 PDF file
     * @param ctx
     * @param messages
     * @throws FileNotFoundException
     */
    public static void createParticipantReport(ApplicationContext ctx, List<APRSMessage> messages) throws FileNotFoundException {
        try {
            PdfDocument pdf = new PdfDocument(new PdfWriter(new FileOutputStream(ctx.getNetParticipantReportName())));
            Document document = new Document(pdf);
            document.setMargins(30, 32, 30, 32);

            int pageCount = 1;
            if ((messages != null) && (!messages.isEmpty())) {
                // calculate number of pages
                pageCount = (messages.size() / MAX_ROWS) + 1;
            }

            for (int page = 1; page <= pageCount; page++) {
                document.add(addDocumentHeader(ctx));
                document.add(addInfoHeader(ctx));
                document.add(addOperatorHeader(ctx));
                document.add(addCommunicationsLogBanner());

                Table communicationsLogHeader = addCommunicationsLogHeader();
                addCommunicationLogRows(communicationsLogHeader, messages, page);
                document.add(communicationsLogHeader);

                document.add(addFooter(ctx, page, pageCount));
                document.add(addGenerator());
            }
            document.close();
        } catch ( Exception e) {
            System.out.println("Exception caught creating Net Participant Report - " + e.getMessage());
        }
    }

    /**
     * add the document header
     *
     * @param table table to create
     * @param ctx application context
     */
    private static Table addDocumentHeader(ApplicationContext ctx) {
        Table table = new Table(3);
        table.useAllAvailableWidth();

        Cell cell1 = new Cell().add(new Paragraph("COMMUNICATIONS LOG")).setBorder(new SolidBorder(1)).setVerticalAlignment(VerticalAlignment.MIDDLE);
        PdfUtils.makeBold(cell1);
        table.addCell(cell1);
        Cell cell2 = new Cell().add(new Paragraph("TASK # " + ctx.getTaskId())).setBorder(new SolidBorder(1)).setVerticalAlignment(VerticalAlignment.MIDDLE);
        PdfUtils.makeBold(cell2);
        table.addCell(cell2);

        LocalDateTime now = LocalDateTime.now();
        String dateStr = DateUtils.getDateStr(now);
        String timeStr = DateUtils.getTimeStr(now);
        Cell cell3 = new Cell().add(new Paragraph("DATE PREPARED:" + dateStr + " \n" +
                                    "TIME PREPARED: " + timeStr + "\n")).setBorder(new SolidBorder(1));
        PdfUtils.fixupFont(cell3);
        table.addCell(cell3);
        return table;
    }

    /**
     * add communications log banner
     * 
     * @return table
     */
    private static Table addCommunicationsLogBanner() {
        Table table = new Table(1);

        table.useAllAvailableWidth();
        Cell cell = new Cell().add(new Paragraph("LOG")).setBorder(new SolidBorder(1)).setTextAlignment(TextAlignment.CENTER);
        PdfUtils.makeDarkBackground(cell);
        table.addCell(cell);
        return table;
    }

    /**
     * add communications log header
     *
     * @return table
     */
    private static Table addCommunicationsLogHeader() {
        Table table = new Table(4);
        table.useAllAvailableWidth();
        Cell cell1 = new Cell().add(new Paragraph("TIME")).setBorder(new SolidBorder(1)).setTextAlignment(TextAlignment.CENTER);
        PdfUtils.fixupFontBold(cell1);
        table.addCell(cell1);

        Cell cell2 = new Cell().add(new Paragraph("FROM")).setBorder(new SolidBorder(1)).setTextAlignment(TextAlignment.CENTER);
        PdfUtils.fixupFontBold(cell2);
        table.addCell(cell2);

        Cell cell3 = new Cell().add(new Paragraph("TO")).setBorder(new SolidBorder(1)).setTextAlignment(TextAlignment.CENTER);
        PdfUtils.fixupFontBold(cell3);
        table.addCell(cell3);

        Cell cell4 = new Cell().add(new Paragraph("SUBJECT")).setBorder(new SolidBorder(1)).setTextAlignment(TextAlignment.CENTER);
        PdfUtils.fixupFontBold(cell4);
        table.addCell(cell4);

        return table;
    }

    /**
     * add info header
     *
     * @param ctx application context
     * @return table
     */
    private static Table addInfoHeader(ApplicationContext ctx) {
        Table table = new Table(2);
        table.useAllAvailableWidth();

        String dateStr = DateUtils.getDateStr(ctx.getStartTime());
        String startTimeStr = DateUtils.getTimeStr(ctx.getStartTime());
        String endTimeStr = DateUtils.getTimeStr(ctx.getEndTime());

        String operationalPeriod = String.format("OPERATIONAL PERIOD %s from %s to %s", dateStr, startTimeStr, endTimeStr);

        Cell cell1 = new Cell().add(new Paragraph(operationalPeriod)).setBorder(new SolidBorder(1));
        PdfUtils.fixupFont(cell1);
        table.addCell(cell1);
        Cell cell2 = new Cell().add(new Paragraph("TASK NAME: " + ctx.getTaskName())).setBorder(new SolidBorder(1));
        PdfUtils.fixupFont(cell2);
        table.addCell(cell2);
        return table;
    }

    /**
     * add operator header
     *
     * @param ctx application context
     * @return table
     */
    private static Table addOperatorHeader(ApplicationContext ctx) {
        Table table = new Table(2);

        table.useAllAvailableWidth();
        Cell cell1 = new Cell().add(new Paragraph("RADIO OPERATOR NAME: "+ctx.getOperatorName())).setBorder(new SolidBorder(1));
        PdfUtils.fixupFont(cell1);
        table.addCell(cell1);
        Cell cell2 = new Cell().add(new Paragraph("STATION I.D. " + ctx.getCallsign())).setBorder(new SolidBorder(1));
        PdfUtils.fixupFont(cell2);
        table.addCell(cell2);
        return table;
    }

    /**
     * add communication log data rows
     *
     * @param table table to add rows to
     * @param messages list of messages to log
     * @param page current page number
     */
    private static void addCommunicationLogRows(Table table, List<APRSMessage> messages, int page) {
        table.useAllAvailableWidth();

        if ((messages == null) || (messages.isEmpty()) ) {
            return;
            // need to make this paged
        }

        // skip over
        int skipCount = (page - 1) * MAX_ROWS;
        int itemCount = 0;

        for (APRSMessage message : messages) {
            // skip to corret page of messages
            if (skipCount != 0) {
                skipCount--;
                continue;
            }

            String timeStr = DateUtils.getTimeStr(message.getTime());

            Cell cellTime = new Cell().add(new Paragraph(timeStr));
            Cell cellFrom = new Cell().add(new Paragraph((message.getSrcCallsign())));
            Cell cellTo = new Cell().add(new Paragraph((message.getDestCallsign())));
            Cell cellMsg = new Cell().add(new Paragraph((message.getMessage())));
            table.addCell(cellTime);
            table.addCell(cellFrom);
            table.addCell(cellTo);
            table.addCell(cellMsg);

            itemCount++;
            if (itemCount == MAX_ROWS) {
                // limit page to MAX_ROWS rows
                break;
            }
        }

        // build blank rows
        if (itemCount < MAX_ROWS) {
            for (; itemCount < MAX_ROWS; itemCount++) {
                // add row of 4 cells
                for (int i = 0; i < 4; i++) {
                    Cell cell = new Cell();
                    cell.setHeight(16);
                    table.addCell(cell);
                }
            }
        }

    }

    /**
     * add footer
     *
     * @param ctx application context
     * @param pageCurrent current page number
     * @param pageCount total number of pages
     * @return table
     */
    private static Table addFooter(ApplicationContext ctx, int pageCurrent, int pageCount) {
        Table table = new Table(2);
        table.useAllAvailableWidth();
        table.addCell(new Cell().add(new Paragraph("Page " + pageCurrent + " of " + pageCount)).setBorder(new SolidBorder(1)));
        Cell cell = new Cell().add(new Paragraph("309 Form")).setBorder(new SolidBorder(1)).setTextAlignment(TextAlignment.RIGHT);
        PdfUtils.makeBold(cell);
        table.addCell(cell);
        return table;
    }

    /**
     * add generator row
     *
     * @return table
     */
    private static Table addGenerator() {
        Table table = new Table(1);
        table.useAllAvailableWidth();
        String text = String.format("309 communications log generated by %s %s", ApplicationVersion.getApplicationName(), ApplicationVersion.getVersion());
        table.addCell(new Cell().add(new Paragraph(text)).setBorder(new SolidBorder(1)).setTextAlignment(TextAlignment.RIGHT));
        return table;
    }
}
