package com.kc1vmz.util;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import com.kc1vmz.object.APRSMessage;
import com.kc1vmz.object.ApplicationContext;

public class NetParticipantFile {
    /**
     * construct content for Net Participant File
     * @param messages list of participant messages
     * @return string with file content
     */
    private static String constructParticipantFileContent(List<APRSMessage> messages) {
        String ret = "";
        if ((messages == null) || (messages.isEmpty())) {
            return ret;
        }

        // print header
        String line = String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"\r\n", "MessageID", "Time", "From", "To", "Message");
        ret += line;

        // print all messages
        for (APRSMessage message : messages) {
            line = String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"\r\n", 
                message.getMessageId(), 
                message.getTime(), 
                message.getSrcCallsign(), 
                message.getDestCallsign(), 
                message.getMessage());
            ret += line;
        }
        return ret;
    }

    /**
     * create the Net Participant File
     * @param ctx application context
     * @param content file content
     * @throws FileNotFoundException 
     */
    public static void createParticipantFile(ApplicationContext ctx, List<APRSMessage> messages) throws FileNotFoundException {
        try {
            // generate files
            String participantFileContent = NetParticipantFile.constructParticipantFileContent(messages);
            if (ctx.isVerbose()) {
                System.out.println("** Participant File content **");
                System.out.println(participantFileContent);
            }

            // have content - write to file
            PrintWriter out = new PrintWriter(ctx.getNetParticipantFileName());
            out.print(participantFileContent);
            out.close();
        } catch (FileNotFoundException e) {
            throw e;
        }
    }


}
