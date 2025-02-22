package com.kc1vmz.workflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kc1vmz.accessor.APRSFIAccessor;
import com.kc1vmz.accessor.APRSFISCRAPEAccessor;
import com.kc1vmz.accessor.CSVFileAccessor;
import com.kc1vmz.enums.APRSInformationSources;
import com.kc1vmz.object.APRSLocation;
import com.kc1vmz.object.APRSMessage;
import com.kc1vmz.object.ApplicationContext;
import com.kc1vmz.util.APRSMessageDisplay;
import com.kc1vmz.util.NetParticipantFile;
import com.kc1vmz.util.NetParticipantLocationFile;
import com.kc1vmz.util.NetParticipantLocationReport;
import com.kc1vmz.util.NetParticipantReport;

public class APRSNetParticipants {
    /**
     * generate documents
     *
     * @param ctx application context
     * @throws Exception
     */
    public static void generateAPRSNetParticipantList(ApplicationContext ctx) throws Exception {
        if (ctx.getAPRSprovider() == null) {
            throw new NullPointerException("APRS provider not found");
        }

        if (ctx.getAPRSprovider().equals(APRSInformationSources.APRSFI)) {
            generateAPRSNetParticipantListAPRSFI(ctx);
        } else if (ctx.getAPRSprovider().equals(APRSInformationSources.CSV)) {
            generateAPRSNetParticipantListCSV(ctx);
        } else if (ctx.getAPRSprovider().equals(APRSInformationSources.APRSFISCRAPE)) {
            generateAPRSNetParticipantListAPRSFIScrape(ctx);
        }

        throw new NullPointerException("Invalid APRS provider specified");
    }

    /**
     * generate documents from APRS.FI API
     *
     * @param ctx application context
     * @throws Exception
     */
    private static void generateAPRSNetParticipantListAPRSFI(ApplicationContext ctx) throws Exception {
        APRSFIAccessor acc = new APRSFIAccessor();

        // get data
        List<APRSMessage> messages = new ArrayList<>();
        if (ctx.isRetrieveData()) {
            messages.addAll(acc.getAPRSMessages(ctx));
        }

        generateAPRSNetParticipantList(ctx, messages, true);
    }


    /**
     * generate documents from CSV file
     *
     * @param ctx application context
     * @throws Exception
     */
    private static void generateAPRSNetParticipantListCSV(ApplicationContext ctx) throws Exception {
        CSVFileAccessor acc = new CSVFileAccessor();

        // get data
        List<APRSMessage> messages = new ArrayList<>();
        if (ctx.isRetrieveData()) {
            messages.addAll(acc.getAPRSMessages(ctx));
        }

        generateAPRSNetParticipantList(ctx, messages, false);
    }


    /**
     * generate documents from APRS.FI screen scrape txt file
     *
     * @param ctx application context
     * @throws Exception
     */
    private static void generateAPRSNetParticipantListAPRSFIScrape(ApplicationContext ctx) throws Exception {
        APRSFISCRAPEAccessor acc = new APRSFISCRAPEAccessor();

        // get data
        List<APRSMessage> messages = new ArrayList<>();
        if (ctx.isRetrieveData()) {
            messages.addAll(acc.getAPRSMessages(ctx));
        }

        generateAPRSNetParticipantList(ctx, messages, false);
    }

    /**
     * generate documents
     *
     * @param ctx application context
     * @param messages list of APRS messages
     * @param createNetDataFile create the net participant text file
     * @throws Exception
     */
    private static void generateAPRSNetParticipantList(ApplicationContext ctx, List<APRSMessage> messages, boolean createNetDataFile) throws Exception {
        APRSFIAccessor accfi = new APRSFIAccessor();

        if (ctx.isVerbose()) {
           APRSMessageDisplay.displayMessages(messages);
        }

        Map<String, List<APRSLocation>> callsignLocations = new HashMap<>();
        if (!messages.isEmpty()) {
            // enumerate all call signs for locations, add to map
            for (APRSMessage message : messages) {
                if (ctx.isRetrieveData()) {
                    List<APRSLocation> locations = accfi.getAPRSLocations(ctx, message.getSrcCallsign());
                    callsignLocations.put(message.getSrcCallsign(), locations);
                }
            }
            if (ctx.isVerbose()) {
                APRSMessageDisplay.displayCallsignLocations(callsignLocations);
             }
        }

        if (ctx.isGenerateDataFiles()) {
            if (createNetDataFile) {
                // have content - write to file
                NetParticipantFile.createParticipantFile(ctx, messages);
            }
            // have map content - write to file
            NetParticipantLocationFile.createParticipantMapFile(ctx, callsignLocations);
        }

        if (ctx.isGenerateReportFiles()) {
            // have content - write to file
            NetParticipantReport.createParticipantReport(ctx, messages);
            // have content - write to file
            NetParticipantLocationReport.createParticipantMapReport(ctx, callsignLocations);
        }
    }
}
