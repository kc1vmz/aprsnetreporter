package com.kc1vmz.workflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kc1vmz.accessor.APRSFIAccessor;
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
    public static String generateAPRSNetParticipantList(ApplicationContext ctx) throws Exception {
        if (ctx.getAPRSprovider() == null) {
            throw new NullPointerException("APRS provider not found");
        }

        if (ctx.getAPRSprovider().equals(APRSInformationSources.APRSFI)) {
            APRSFIAccessor acc = new APRSFIAccessor();

            // get data
            List<APRSMessage> messages = new ArrayList<>();
            if (ctx.isRetrieveData()) {
                messages.addAll(acc.getAPRSMessages(ctx));
            }

            if (ctx.isVerbose()) {
               APRSMessageDisplay.displayMessages(messages);
            }

            Map<String, List<APRSLocation>> callsignLocations = new HashMap<>();
            if (!messages.isEmpty()) {
                // enumerate all call signs for locations, add to map
                for (APRSMessage message : messages) {
                    if (ctx.isRetrieveData()) {
                        List<APRSLocation> locations = acc.getAPRSLocations(ctx, message.getSrcCallsign());
                        callsignLocations.put(message.getSrcCallsign(), locations);
                    }
                }
                if (ctx.isVerbose()) {
                    APRSMessageDisplay.displayCallsignLocations(callsignLocations);
                 }
             }

             if (ctx.isGenerateDataFiles()) {
                // have content - write to file
                NetParticipantFile.createParticipantFile(ctx, messages);

                // have map content - write to file
                NetParticipantLocationFile.createParticipantMapFile(ctx, callsignLocations);
             }

             if (ctx.isGenerateReportFiles()) {
                // have content - write to file
                NetParticipantReport.createParticipantReport(ctx, messages);
                // have content - write to file
                NetParticipantLocationReport.createParticipantMapReport(ctx, callsignLocations);
             }
        } else {
            throw new Exception("Unknown APRS provider");
        }
        return "";  // outut filename
    }
}
