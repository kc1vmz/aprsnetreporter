package com.kc1vmz.interfaces;

import java.util.List;

import com.kc1vmz.object.APRSLocation;
import com.kc1vmz.object.APRSMessage;
import com.kc1vmz.object.ApplicationContext;

public interface APRSInformationSource {
    public List<APRSMessage> getAPRSMessages(ApplicationContext ctx);
    public List<APRSLocation> getAPRSLocations(ApplicationContext ctx, String callsign);
}
