package com.kc1vmz.util;

import java.util.List;
import java.util.Map;

import com.kc1vmz.object.APRSLocation;
import com.kc1vmz.object.APRSMessage;

public class APRSMessageDisplay {
    /**
     * display list of messages
     *
     * @param messages list of APRS messages
     */
    public static void displayMessages(List<APRSMessage> messages) {
        System.out.println("** Net Participant Messages **");
        if ((messages == null) || (messages.isEmpty())) {
            System.out.println("-- None");
            return;
        }
        for (APRSMessage message : messages) {
            displayMessage(message);
        }
    }

    /**
     * display single APRS message
     * @param message APRS message
     */
    public static void displayMessage(APRSMessage message) {
        System.out.println("MessageID: " + message.getMessageId());
        System.out.println("  Time: " + message.getTime());
        System.out.println("  Source Callsign: " + message.getSrcCallsign());
        System.out.println("  Dest Callsign: " + message.getDestCallsign());
        System.out.println("  Message: " + message.getMessage());
    }

    /**
     * display list of APRS locations
     * @param locations list of APRS locations
     */
    public static void displayLocations(List<APRSLocation> locations) {
        if ((locations == null) || (locations.isEmpty())) {
            System.out.println("-- None");
            return;
        }
        for (APRSLocation location : locations) {
            displayLocation(location);
        }
    }

    /**
     * display single APRS location
     * @param location APRS location
     */
    public static void displayLocation(APRSLocation location) {
        System.out.println("Name: " + location.getName());
        System.out.println("  Time: " + location.getTime());
        System.out.println("  Last time: " + location.getLastTime());
        System.out.println("  Source Callsign: " + location.getSrcCallsign());
        System.out.println("  Dest Callsign: " + location.getDestCallsign());
        System.out.println("  Latitude: " + location.getLatitude());
        System.out.println("  Longitude: " + location.getLongitude());
        System.out.println("  PHG: " + location.getPhg());
        System.out.println("  Type: " + location.getType());
        System.out.println("  Symbol: " + location.getSymbol());
        System.out.println("  Path: " + location.getPath());
        System.out.println("  Comment: " + location.getComment());
    }

    /**
     * display locations for call signs
     * 
     * @param callsignLocations  map of call signs and recent location data
     */
    public static void displayCallsignLocations(Map<String, List<APRSLocation>> callsignLocations) {
        System.out.println("** Net Participant Locations **");
        if ((callsignLocations == null)  || (callsignLocations.isEmpty())) {
            System.out.println("-- None");
            return;
        }
        for (var entry : callsignLocations.entrySet()) {
            displayCallsignLocations(entry.getKey(), entry.getValue());
        }
    }

    /**
     * display callsign's locations
     * 
     * @param callsign callsign of operator
     * @param locations list of locations
     */
    public static void displayCallsignLocations(String callsign, List<APRSLocation> locations) {
        if ((locations == null)  || (locations.isEmpty())) {
            System.out.println("Callsign: " + callsign + " - no locations found");
            return;
        }

        // print locations for callsign
        System.out.println("Callsign: " + callsign );
        displayLocations(locations);
    }
}
