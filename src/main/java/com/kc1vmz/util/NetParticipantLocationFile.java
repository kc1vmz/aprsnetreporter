package com.kc1vmz.util;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import com.kc1vmz.object.APRSLocation;
import com.kc1vmz.object.ApplicationContext;

public class NetParticipantLocationFile {

    /**
     * construct the participant map file content
     * @param callsignLocations map of callsigns to locations
     * @return string with file content
     */
    public static String constructParticipantMapFileContent(Map<String, List<APRSLocation>> callsignLocations) {
        String ret = "";
        if ((callsignLocations == null) || (callsignLocations.isEmpty())) {
            return ret;
        }

        // print header
        String line = String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"\r\n", "Participant", "Symbol", "Latitude", "Longitude", "Path");
        ret += line;

        for (var entry : callsignLocations.entrySet()) {
            // print all locations
            for (APRSLocation location : entry.getValue()) {
                line = String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"\r\n", 
                    entry.getKey(), 
                    location.getSymbol(), 
                    location.getLatitude(), 
                    location.getLongitude(), 
                    location.getPath());
                ret += line;
            }
        }
        return ret;
    }

    /**
     * create the Net Participant Map file
     * @param ctx application context
     * @param callsignLocations map of callsigns and locations
     */
    public static void createParticipantMapFile(ApplicationContext ctx, Map<String, List<APRSLocation>> callsignLocations) throws FileNotFoundException {
        try {
            String content = constructParticipantMapFileContent(callsignLocations);

            PrintWriter out = new PrintWriter(ctx.getNetParticipantMapFileName());
            out.print(content);
            out.close();
        } catch (FileNotFoundException e) {
            throw e;
        }
    }

}
