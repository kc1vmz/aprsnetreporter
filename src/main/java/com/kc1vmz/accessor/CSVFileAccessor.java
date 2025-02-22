package com.kc1vmz.accessor;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.kc1vmz.object.APRSMessage;
import com.kc1vmz.object.ApplicationContext;
import com.opencsv.CSVReader;

public class CSVFileAccessor {
    /**
     * get list of APRS messages from CSV file
     * 
     * "MessageID","Time","From","To","Message"
     * 2025-02-19 21:01:28 EST: K2EJT-10>ANSRVR: cq hotg happy APRS Thursday from NY
     * 
     * @param ctx
     * @return
     */
    public List<APRSMessage> getAPRSMessages(ApplicationContext ctx) {
        List<APRSMessage> messages = new ArrayList<>();

        

        return messages;
    }

    // https://www.baeldung.com/opencsv

    public List<String[]> readAllLines(Path filePath) throws Exception {
    try (Reader reader = Files.newBufferedReader(filePath)) {
        try (CSVReader csvReader = new CSVReader(reader)) {
            return csvReader.readAll();
        }
    }
}
}
