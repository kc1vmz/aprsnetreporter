package com.kc1vmz;

import java.time.LocalDateTime;

import com.kc1vmz.enums.APRSInformationSources;
import com.kc1vmz.object.ApplicationContext;
import com.kc1vmz.object.ApplicationVersion;
import com.kc1vmz.util.UserPrompt;
import com.kc1vmz.workflow.APRSNetParticipants;

public class Main {

    /**
     * main entry point
     * @param args command line arguments
     */
    public static void main(String[] args) {
        displayBanner();

        ApplicationContext ctx = processArguments(args);

        try {
            APRSNetParticipants.generateAPRSNetParticipantList(ctx);
        } catch (Exception e) {
        }
    }

    /**
     * display application usage
     */
    private static void displayUsage() {
        System.out.println("Usage:");
        System.out.println(" --verbose - display debug information");
        System.out.println(" --help - display usage information");
        System.out.println(" --generateDataFiles - generate data files");
        System.out.println(" --generateReportFiles - generate PDF report files");
        System.out.println(" --messagePrefix \"prefix\" - prefix on APRS messages to include");
        System.out.println(" --callsign \"callsign\" - operator callsign");
        System.out.println(" --operatorName \"Operator Name\" - Name of the operator");
        System.out.println(" --taskId \"task id\" - Task Id for the Net");
        System.out.println(" --taskName \"task name\" - Task name for the Net");
        System.out.println(" --netParticipantFileName \"filename\" - Filename for Participant data file");
        System.out.println(" --netParticipantMapFileName \"filename\" - Filename for Participant location data file");
        System.out.println(" --netParticipantReportName \"filename\" - Filename for Participant report");
        System.out.println(" --netParticipantMapReportName \"filename\" - Filename for Participant location report");
        System.out.println(" --dataSource \"APRSFI\" - Data source to retrieve APRS information from (APRSFI only useful value so far)");
        System.out.println(" --password \"password\" - Password to retrieve APRS information from data source");
        System.out.println(" --start \"time in YYYY-MM-DDTHH:MM format\" - Start time for Net");
        System.out.println(" --end \"time in YYYY-MM-DDTHH:MM format\" - End time for Net");
    }

    /**
     * display opening banner
     */
    private static void displayBanner() {
        System.out.println(String.format("%s %s - %s", ApplicationVersion.getApplicationName(), ApplicationVersion.getVersion(), ApplicationVersion.getCopyright()));
    }

    /**
     * process command line arguments
     *
     * @param args command line arguments
     * @return application context
     */
    private static ApplicationContext processArguments(String[] args) {
        boolean verboseMode = false;
        boolean unknown = false;
        boolean useAPRSFI = false;
        String dataSource = null;
        String callsign = null;
        String messagePrefix = null;
        String netParticipantFileName = null;
        String netParticipantReportName = null;
        String netParticipantMapFileName = null;
        String netParticipantMapReportName = null;
        String password = null;
        String operatorName = null;
        String taskId = null;
        String taskName = null;
        String startTime = null;
        String endTime = null;
        Boolean generateDataFiles = null;
        Boolean generateReportFiles = null;


        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.equalsIgnoreCase("--verbose")) {
                verboseMode = true;
            } else if (arg.equalsIgnoreCase("--help")) {
                displayUsage();
                return null;
            } else if (arg.equalsIgnoreCase("--generateDataFiles")) {
                generateDataFiles = true;
            } else if (arg.equalsIgnoreCase("--generateReportFiles")) {
                generateReportFiles = true;
            } else if (arg.equalsIgnoreCase("--callsign")) {
                callsign = args[i + 1];
                i++;
            } else if (arg.equalsIgnoreCase("--messagePrefix")) {
                messagePrefix = args[i + 1];
                i++;
            } else if (arg.equalsIgnoreCase("--netParticipantFileName")) {
                netParticipantFileName = args[i + 1];
                i++;
            } else if (arg.equalsIgnoreCase("--netParticipantReportName")) {
                netParticipantReportName = args[i + 1];
                i++;
            } else if (arg.equalsIgnoreCase("--dataSource")) {
                dataSource = args[i + 1];
                i++;
            } else if (arg.equalsIgnoreCase("--netParticipantMapFileName")) {
                netParticipantMapFileName = args[i + 1];
                i++;
            } else if (arg.equalsIgnoreCase("--netParticipantMapReportName")) {
                netParticipantMapReportName = args[i + 1];
                i++;
            } else if (arg.equalsIgnoreCase("--password")) {
                password = args[i + 1];
                i++;
            } else if (arg.equalsIgnoreCase("--operatorName")) {
                operatorName = args[i + 1];
                i++;
            } else if (arg.equalsIgnoreCase("--taskId")) {
                taskId = args[i + 1];
                i++;
            } else if (arg.equalsIgnoreCase("--taskName")) {
                taskName = args[i + 1];
                i++;
            } else if (arg.equalsIgnoreCase("--start")) {
                startTime = args[i + 1];
                i++;
            } else if (arg.equalsIgnoreCase("--end")) {
                endTime = args[i + 1];
                i++;
            } else {
                System.out.println("Unknown argument: " + arg);
                unknown = true;
            }
        }
        if (unknown) {
            displayUsage();
            throw new RuntimeException("Unknown arguments");
        }

        if ((callsign == null) || (callsign.isBlank())) {
            callsign = UserPrompt.promptUser("Callsign: ", false);
        }
        if ((operatorName == null) || (operatorName.isBlank())) {
            operatorName = UserPrompt.promptUser("Operator name: ", false);
        }
        if ((taskId == null) || (taskId.isBlank())) {
            taskId = UserPrompt.promptUser("Task Id: ", false);
        }
        if ((taskName == null) || (taskName.isBlank())) {
            taskName = UserPrompt.promptUser("Task name: ", false);
        }
        if ((messagePrefix == null) || (messagePrefix.isBlank())) {
            messagePrefix = UserPrompt.promptUser("Message prefix: ", false);
        }
        if ((startTime == null) || (startTime.isBlank())) {
            startTime = UserPrompt.promptUser("Start time (YYYY-MM-DDTHH:MM): ", false);
        }
        if ((endTime == null) || (endTime.isBlank())) {
            endTime = UserPrompt.promptUser("End time (YYYY-MM-DDTHH:MM): ", false);
        }
        if ((dataSource == null) || (dataSource.isBlank())) {
            dataSource = UserPrompt.promptUser("Data source (ex: APRSFI): ", false);
        }
        if (generateDataFiles == null) {
            String prompt = UserPrompt.promptUser("Generate data files (Y/N): ", false);
            if ((prompt.startsWith("Y")) || (prompt.startsWith("y"))) {
                generateDataFiles = true;
            } else {
                generateDataFiles = false;
            }
        }
        if (generateReportFiles == null) {
            String prompt = UserPrompt.promptUser("Generate report files (Y/N): ", false);
            if ((prompt.startsWith("Y")) || (prompt.startsWith("y"))) {
                generateReportFiles = true;
            } else {
                generateReportFiles = false;
            }
        }

        if (generateDataFiles) {
            if ((netParticipantFileName == null) || (netParticipantFileName.isBlank())) {
                netParticipantFileName = UserPrompt.promptUser("Participant file name: ", false);
            }
            if ((netParticipantMapFileName == null) || (netParticipantMapFileName.isBlank())) {
                netParticipantMapFileName = UserPrompt.promptUser("Participant Location file name: ", false);
            }
            }
        if (generateReportFiles) {
            if ((netParticipantReportName == null) || (netParticipantReportName.isBlank())) {
                netParticipantReportName = UserPrompt.promptUser("Participant report name: ", false);
            }
            if ((netParticipantMapReportName == null) || (netParticipantMapReportName.isBlank())) {
                netParticipantMapReportName = UserPrompt.promptUser("Participant Location report name: ", false);
            }
        }

        if ((password == null) || (password.isBlank())) {
            password = UserPrompt.promptUser("Data source password: ", true);
        }


        // validate answers
        if ((callsign == null) || (callsign.isBlank())) {
            displayUsage();
            throw new RuntimeException("Missing required information - callsign");
        }
        if ((operatorName == null) || (operatorName.isBlank())) {
            displayUsage();
            throw new RuntimeException("Missing required information - operator name");
        }
        if ((taskId == null) || (taskId.isBlank())) {
            displayUsage();
            throw new RuntimeException("Missing required information - task id");
        }
        if ((taskName == null) || (taskName.isBlank())) {
            displayUsage();
            throw new RuntimeException("Missing required information - task name");
        }
        if ((messagePrefix == null) || (messagePrefix.isBlank())) {
            displayUsage();
            throw new RuntimeException("Missing required information - message prefix");
        }
        if ((startTime == null) || (startTime.isBlank())) {
            displayUsage();
            throw new RuntimeException("Missing required information - start time");
        }
        if ((endTime == null) || (endTime.isBlank())) {
            displayUsage();
            throw new RuntimeException("Missing required information - end time");
        }
        if ((dataSource == null) || (dataSource.isBlank())) {
            displayUsage();
            throw new RuntimeException("Missing required information - data source");
        } else {
            if (!dataSource.equalsIgnoreCase("APRSFI")) {
                displayUsage();
                throw new RuntimeException("Inbvalid information - data source must be APRSFI");
            } else {
                useAPRSFI = true;
            }
        }
        if ((password == null) || (password.isBlank())) {
            displayUsage();
            throw new RuntimeException("Missing required information - password");
        }
        if (generateDataFiles) {
            if ((netParticipantFileName == null) || (netParticipantFileName.isBlank())) {
                throw new RuntimeException("Missing required information - participant data file name");
            }
            if ((netParticipantMapFileName == null) || (netParticipantMapFileName.isBlank())) {
                throw new RuntimeException("Missing required information - participant location file name");
            }
            }
        if (generateReportFiles) {
            if ((netParticipantReportName == null) || (netParticipantReportName.isBlank())) {
                throw new RuntimeException("Missing required information - participant location data report name");
            }
            if ((netParticipantMapReportName == null) || (netParticipantMapReportName.isBlank())) {
                throw new RuntimeException("Missing required information - participant location data report name");
            }
        }

        // set up application context
        ApplicationContext ctx = new ApplicationContext();
        ctx.setAPRSprovider(APRSInformationSources.APRSFI);
        ctx.setCallsign(callsign);
        ctx.setMessagePrefix(messagePrefix);
        ctx.setVerbose(verboseMode);
        ctx.setNetParticipantFileName(netParticipantFileName);
        ctx.setNetParticipantMapFileName(netParticipantMapFileName);
        ctx.setApikey(password); // "206829.HJuFqPV6fcpqTKIa");
        ctx.setOperatorName(operatorName);
        ctx.setTaskId(taskId);
        ctx.setTaskName(taskName);
        ctx.setStartTime(LocalDateTime.parse(startTime));
        ctx.setEndTime(LocalDateTime.parse(endTime));
        ctx.setNetParticipantReportName(netParticipantReportName);
        ctx.setNetParticipantMapReportName(netParticipantMapReportName);
        ctx.setGenerateDataFiles(generateDataFiles);
        ctx.setGenerateReportFiles(generateReportFiles);
        ctx.setRetrieveData(true);
        if (useAPRSFI) {
            ctx.setAPRSprovider(APRSInformationSources.APRSFI);
        }

        return ctx;
    }
}