package com.kc1vmz.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.kc1vmz.object.APRSLocation;
import com.kc1vmz.object.ApplicationContext;
import com.kc1vmz.object.ApplicationVersion;

public class NetParticipantLocationReport {
    private final static int MAX_ROWS = 29;

    /**
     * create a net participation map report based on ICS-309 PDF file
     * @param ctx
     * @param messages
     * @throws FileNotFoundException
     */
    public static void createParticipantMapReport(ApplicationContext ctx, Map<String, List<APRSLocation>> callsignLocations) throws FileNotFoundException {
        try {
            PdfDocument pdf = new PdfDocument(new PdfWriter(new FileOutputStream(ctx.getNetParticipantMapReportName())));
            Document document = new Document(pdf);
            document.setMargins(30, 32, 30, 32);

            List<APRSLocation> locations = new ArrayList<>();
            locations.addAll(generateLocationList(callsignLocations));

            int pageCount = 1;
            if ((locations != null) && (!locations.isEmpty())) {
                // calculate number of pages
                pageCount = (locations.size() / MAX_ROWS) + 1;
            }

            for (int page = 1; page <= pageCount; page++) {
                document.add(addDocumentHeader(ctx));
                document.add(addInfoHeader(ctx));
                document.add(addOperatorHeader(ctx));
                document.add(addCommunicationsLogBanner());

                Table communicationsLogHeader = addCommunicationsLogHeader();
                addCommunicationLogRows(communicationsLogHeader, locations, page);
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
     * create list of call signs and single location
     *
     * @return
     */
    private static List<APRSLocation> generateLocationList(Map<String, List<APRSLocation>> callsignLocations) {
        List<APRSLocation> locations = new ArrayList<>();

        if ((callsignLocations == null) || (callsignLocations.isEmpty())) {
            return locations;
        }

        for (var entry : callsignLocations.entrySet()) {
            // take the first item only
            for (APRSLocation location : entry.getValue()) {
                locations.add(location);
                break;
            }
        }

        return locations;
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

        Cell cell1 = new Cell().add(new Paragraph("PARTICIPANT LOCATIONS")).setBorder(new SolidBorder(1)).setVerticalAlignment(VerticalAlignment.MIDDLE);
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
        Table table = new Table(6);
        table.useAllAvailableWidth();
        Cell cell1 = new Cell().add(new Paragraph("TIME")).setBorder(new SolidBorder(1)).setTextAlignment(TextAlignment.CENTER);
        PdfUtils.fixupFontBold(cell1);
        table.addCell(cell1);

        Cell cell2 = new Cell().add(new Paragraph("PARTICIPANT")).setBorder(new SolidBorder(1)).setTextAlignment(TextAlignment.CENTER);
        PdfUtils.fixupFontBold(cell2);
        table.addCell(cell2);

        Cell cell3 = new Cell().add(new Paragraph("LONG")).setBorder(new SolidBorder(1)).setTextAlignment(TextAlignment.CENTER);
        PdfUtils.fixupFontBold(cell3);
        table.addCell(cell3);

        Cell cell4 = new Cell().add(new Paragraph("LAT")).setBorder(new SolidBorder(1)).setTextAlignment(TextAlignment.CENTER);
        PdfUtils.fixupFontBold(cell4);
        table.addCell(cell4);

        Cell cell5 = new Cell().add(new Paragraph("GRID")).setBorder(new SolidBorder(1)).setTextAlignment(TextAlignment.CENTER);
        PdfUtils.fixupFontBold(cell5);
        table.addCell(cell5);

        Cell cell6 = new Cell().add(new Paragraph("PATH")).setBorder(new SolidBorder(1)).setTextAlignment(TextAlignment.CENTER);
        PdfUtils.fixupFontBold(cell6);
        table.addCell(cell6);

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
    private static void addCommunicationLogRows(Table table, List<APRSLocation> locations, int page) {
        table.useAllAvailableWidth();

        if ((locations == null) || (locations.isEmpty()) ) {
            return;
        }

        // skip over
        int skipCount = (page - 1) * MAX_ROWS;
        int itemCount = 0;

        for (APRSLocation location : locations) {
            // skip to correct page of messages
            if (skipCount != 0) {
                skipCount--;
                continue;
            }

            String timeStr = DateUtils.getTimeStr(location.getLastTime());

            Cell cellTime = new Cell().add(new Paragraph(timeStr));
            Cell cellFrom = new Cell().add(new Paragraph((location.getSrcCallsign())));
            Cell cellLong = new Cell().add(new Paragraph((location.getLongitude().toString())));
            Cell cellLat = new Cell().add(new Paragraph((location.getLatitude().toString())));
            Cell cellPath = new Cell().add(new Paragraph((location.getPath())));

            String grid= "";
            try {
                grid = GridSquareConverter.latLonToGridSquare(location.getLatitude(), location.getLongitude());
            } catch (Exception e) {
            }

            Cell cellGrid = new Cell().add(new Paragraph(grid));

            table.addCell(cellTime);
            table.addCell(cellFrom);
            table.addCell(cellLong);
            table.addCell(cellLat);
            table.addCell(cellGrid);
            table.addCell(cellPath);

            itemCount++;
            if (itemCount == MAX_ROWS) {
                // limit page to MAX_ROWS rows
                break;
            }
        }

        // build blank rows
        if (itemCount < MAX_ROWS) {
            for (; itemCount < MAX_ROWS; itemCount++) {
                // add row of 6 cells
                for (int i = 0; i < 6; i++) {
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
        Cell cell = new Cell().add(new Paragraph("Location Form")).setBorder(new SolidBorder(1)).setTextAlignment(TextAlignment.RIGHT);
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
        String text = String.format("Net Participation Form generated by %s %s", ApplicationVersion.getApplicationName(), ApplicationVersion.getVersion());
        table.addCell(new Cell().add(new Paragraph(text)).setBorder(new SolidBorder(1)).setTextAlignment(TextAlignment.RIGHT));
        return table;
    }

}
