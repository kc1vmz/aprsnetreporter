package com.kc1vmz.accessor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.kc1vmz.object.APRSMessage;
import com.kc1vmz.object.ApplicationContext;

public class APRSFISCRAPEAccessor {
    /**
     * get list of APRS messages from APRSFI scrape file
     * 
     * 2025-02-19 21:01:28 EST: K2EJT-10>ANSRVR: cq hotg happy APRS Thursday from NY
     * 
     * @param ctx
     * @return
     */
    public List<APRSMessage> getAPRSMessages(ApplicationContext ctx) {
        List<APRSMessage> messages = new ArrayList<>();

        // read file line by line
        // for each line, read to third colon - that is date
        // read to next colon to get send>receive
        // the rest is a message
        // only keep those with the prefix

        try (BufferedReader br = new BufferedReader(new FileReader(ctx.getNetParticipantFileName()))) {
            String line;
            while ((line = br.readLine()) != null) {
            // process the line.
                try {
                    APRSMessage msg = createMessage(line, ctx);
                    if (msg != null) {
                        messages.add(msg);
                    }
                } catch (Exception e) {
                    System.out.println("Exception caught reading line" + e.getLocalizedMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("Exception caught opening input file "  + e.getLocalizedMessage());
        }

        return messages;
    }

    /**
     * convert string to object
     *
     *fmt =  2025-02-19 21:01:28 EST: K2EJT-10>ANSRVR: cq hotg happy APRS Thursday from NY
     * @param line text in format above
     * @return
     */
    public APRSMessage createMessage(String line, ApplicationContext ctx) {
        APRSMessage msg = null;

        // this is hella ugly, but it works
        // get the time without the time zone
        String time = line.substring(0, 19);
        
        // get the time zone
        // String [] fields = line.substring(20).split(":");
        // String timezone = fields[0];

        // get the send and receive
        String postDate = line.substring(20);
        String [] fields2 = postDate.split(":");  // TZ / to>from / message
        String [] toFrom = fields2[1].split(">");
        String from = toFrom[0].substring(1);  // skip space
        String to = toFrom[1];

        if (from.contains("-")) {
            // remove station id
            from = from.substring(0, from.indexOf("-"));
        }
        if (to.contains("-")) {
            // remove station id
            to = to.substring(0, to.indexOf("-"));
        }
        // get the message
        String msgtext = line.substring(line.indexOf(to)+to.length()+2);
        String allcaps = msgtext.toUpperCase();
        LocalDateTime ldt = LocalDateTime.of(LocalDate.of(
            Integer.parseInt(time.substring(0,4)),
            Integer.parseInt(time.substring(5,7)),
            Integer.parseInt(time.substring(8,10))), 
            LocalTime.of(Integer.parseInt(time.substring(11,13)),
            Integer.parseInt(time.substring(14,16)),
            Integer.parseInt(time.substring(17,19))));
        //  ZonedDateTime zdt = ldt.atZone(ZoneId.systemDefault());

        if ((allcaps.startsWith(ctx.getMessagePrefix().toUpperCase())) && 
                ((ldt.isAfter(ctx.getStartTime()) || ldt.isEqual(ctx.getStartTime())) && 
                (ldt.isBefore(ctx.getEndTime()) || ldt.isEqual(ctx.getEndTime())))){
            // we have a message
            if (msgtext.length() == ctx.getMessagePrefix().length()) {
                msgtext = "";
            } else {
                msgtext = msgtext.substring(ctx.getMessagePrefix().length()+1);  // remove prefix
            }
            msg = new APRSMessage();
            msg.setDestCallsign(to);
            msg.setMessage(msgtext);
            msg.setMessageId("");
            msg.setSrcCallsign(from);

            msg.setTime(ldt);
        } else {
            if (ctx.isVerbose()) {
                System.out.println("Rejected " + from + " at " + ldt.toString() + " message " + allcaps);
            }
        }

        return msg;
    }
}
