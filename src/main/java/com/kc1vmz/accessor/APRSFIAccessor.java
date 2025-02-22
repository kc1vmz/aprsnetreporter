package com.kc1vmz.accessor;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.kc1vmz.interfaces.APRSInformationSource;
import com.kc1vmz.object.APRSFILocation;
import com.kc1vmz.object.APRSFILocationQueryResponse;
import com.kc1vmz.object.APRSFIMessage;
import com.kc1vmz.object.APRSFIMessageQueryResponse;
import com.kc1vmz.object.APRSLocation;
import com.kc1vmz.object.APRSMessage;
import com.kc1vmz.object.ApplicationContext;

public class APRSFIAccessor implements APRSInformationSource {

    /**
     * contact APRS.FI API to get messages
     */
    public List<APRSMessage> getAPRSMessages(ApplicationContext ctx) {
        List<APRSMessage> ret = new ArrayList<>();
        String url = buildMessageUrl(ctx);

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(new URI(url))
                    .header("Content-Type", "application/json")
                    .build();
    
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (isSuccess(response.statusCode())) {
                String responseBody = response.body();
                APRSFIMessageQueryResponse queryResponse = new Gson().fromJson(responseBody, APRSFIMessageQueryResponse.class);
                if (queryResponse.getResult().equalsIgnoreCase("ok")) {
                    // good response
                    String messagePrefix = ctx.getMessagePrefix().toUpperCase();
                    for (APRSFIMessage message : queryResponse.getEntries()) {
                        if (message.getMessage().isBlank()) {
                            continue;
                        }
                        String messageText = message.getMessage().toUpperCase();

                        // filter out other messages
                        if (!messageText.startsWith(messagePrefix)) {
                            continue;
                        }
                        // filter out by date
                        LocalDateTime msgTime = convertDateTimeString(message.getTime());
                        if ((msgTime.isBefore(ctx.getStartTime())) || (msgTime.isAfter(ctx.getEndTime()))) {
                            continue;
                        }

                        APRSMessage msg = new APRSMessage();
                        msg.setDestCallsign(message.getDst());
                        msg.setSrcCallsign(message.getSrccall());
                        msg.setMessage(message.getMessage());
                        msg.setMessageId(message.getMessageid());

                        msg.setTime(msgTime);
                
                        // add to list
                        ret.add(msg);
                    }
                } else {
                    System.out.println("Error requesting message data from APRS.FI - " + queryResponse.getDescription());
                }
            }
        } catch (IOException | InterruptedException | URISyntaxException e) {
            System.out.println("Exception caught requesting message data - " + e.getMessage());
        }

        return ret;
    }


    /**
     * contact APRS.FI API to get locations
     */
    public List<APRSLocation> getAPRSLocations(ApplicationContext ctx, String callsign) {
        List<APRSLocation> ret = new ArrayList<>();
        String url = buildLocationUrl(ctx, callsign);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            // skip 
        }

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(new URI(url))
                    .header("Content-Type", "application/json")
                    .build();
    
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (isSuccess(response.statusCode())) {
                String responseBody = response.body();
                APRSFILocationQueryResponse queryResponse = new Gson().fromJson(responseBody, APRSFILocationQueryResponse.class);
                if (queryResponse.getResult().equalsIgnoreCase("ok")) {
                    // good response
                    for (APRSFILocation location : queryResponse.getEntries()) {

                        // filter out by date
                        LocalDateTime msgTime = convertDateTimeString(location.getLasttime());
                        if ((msgTime.isBefore(ctx.getStartTime()))) {
                            if (ctx.isVerbose()) {
                                System.out.println("-- FILTERED OUT " + callsign + " because location is prior to net - " + msgTime.toString() + " --");
                            }
                            continue;
                        }

                        APRSLocation loc = new APRSLocation();
                        loc.setComment(location.getComment());
                        loc.setDestCallsign(location.getDstcall());
                        loc.setLastTime(convertDateTimeString(location.getLasttime()));
                        loc.setLatitude(Float.parseFloat(location.getLat()));
                        loc.setLongitude(Float.parseFloat(location.getLng()));
                        loc.setName(location.getName());
                        loc.setPath(location.getPath());
                        // phg?  so many others
                        loc.setSrcCallsign(location.getSrccall());
                        loc.setSymbol(location.getSymbol());
                        loc.setTime(convertDateTimeString(location.getTime()));
                        loc.setType(location.getType());
                
                        // add to list
                        ret.add(loc);
                    }
                } else {
                    System.out.println("Error requesting location data from APRS.FI - " + queryResponse.getDescription());
                }
            }
        } catch (IOException | InterruptedException | URISyntaxException e) {
            System.out.println("Exception caught requesting location data - " + e.getMessage());
        }

        return ret;
    }

    /**
     * build the APRS.FI URL to get locations
     *
     * @param ctx application context
     * @return URL for request
     */
    private String buildLocationUrl(ApplicationContext ctx, String callsign) {
        String ret = "https://api.aprs.fi/api/get?format=json" + 
                        "&what=loc" +
                        "&name="+callsign +
                        "&apikey="+ctx.getApikey();
        return ret;
    }

    /**
     * build the APRS.FI URL to get messages
     *
     * @param ctx application context
     * @return URL for request
     */
    private String buildMessageUrl(ApplicationContext ctx) {
        return "https://api.aprs.fi/api/get?format=json" + 
                        "&what=msg" +
                        "&dst="+ctx.getCallsign() +
                        "&apikey="+ctx.getApikey();
    }

    /**
     * determine if HTTP request is successful
     *
     * @param statusCode
     * @return true/false if successful
     */
    private boolean isSuccess(int statusCode) {
        if ((statusCode >= 200) && (statusCode < 300)) {
            return true;
        }
        return false;
    }

    /**
     * convert unix date time string to LocalDateTime
     *
     * @param dateTime UNIX seconds as string
     * @return LocalDateTime in local time zone
     */
    private LocalDateTime convertDateTimeString(String dateTime) {
        // convert string to LocalDateTime
        Instant instant = Instant.ofEpochSecond(Long.parseLong(dateTime)) ;
        ZoneId z = ZoneId.of("America/New_York");  
        ZonedDateTime zdt = instant.atZone( z ) ;  
        return zdt.toLocalDateTime() ;
    }

}
