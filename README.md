# aprsnetreporter

aprsnetreporter is a Java library that reads [APRS](http://www.aprs.org/)
packets from the internet and builds participant and participant
location reports for simple APRS nets.

APRS (Automatic Packet Reporting System) is a digital amateur radio
mode used for broadcasting local tactical information, position tracking and
much more.

APRS nets can be created simply by sending an APRS message to a specific callsign
with a specific prefix.  This form of net does not rely on other internet services but only APRS.

aprsnetreporter is licensed under the GNU General Public License. The aim of
the library is to make APRS nets easier to hold and report.

If you would like to contribute to aprsnetreporter, please let us know and contribute 
patches, fixes and improvements.

## Usage instructions

### When using `Maven`

Executing 'mvn compile' will build the library, executing 'mvn package' will create a JAR package.
All compiled code winds up in the "target" directory. A fat JAR is also created.

## Features

You can gather APRS messages sent to a callsign that have a specific prefix over a specific time period from web sites making APRS information available, such as https://aprs.fi.

You can create a CSV file containing the participants and their messages, as well as participants and their location.

You can create a PDF file containing the similar information in a prettier format.

You can gather APRS messages sent to a callsign other than the operator creating the reports.  Useful when using ANSRVR as a destination for messages.

You can provide a text file that scraped APRS.FI for messages to a callsign as input to the reporting


## Usage

You can invoke the parser from the command line:

$ java -jar aprsnetreporter-1.0.2-SNAPSHOT-jar-with-dependencies.jar  --help

Usage:

 --verbose - display debug information

 --help - display usage information

 --generateDataFiles - generate data files (Yes | No)

 --generateReportFiles - generate PDF report files (Yes | No)

 --messagePrefix "prefix" - prefix on APRS messages to include

 --callsign "callsign" - message receiver callsign

 --operatorCallsign "callsign" - operator callsign

 --operatorName "Operator Name" - Name of the operator

 --taskId "task id" - Task Id for the Net

 --taskName "task name" - Task name for the Net

 --netParticipantFileName "filename" - Filename for Participant data file

 --netParticipantMapFileName "filename" - Filename for Participant location data file

 --netParticipantReportName "filename" - Filename for Participant report

 --netParticipantMapReportName "filename" - Filename for Participant location report

 --dataSource "APRSFI | CSV | APRSFISCRAPE" - Data source to retrieve APRS information from

 --password "password" - Password to retrieve APRS information from data source

 --start "time in YYYY-MM-DDTHH:MM format" - Start time for Net

 --end "time in YYYY-MM-DDTHH:MM format" - End time for Net


## Examples

$ java -jar aprsnetreporter-1.0.2-SNAPSHOT-jar-with-dependencies.jar --operatorCallsign YOURCALLSIGN --callsign RECEIVINGCALLSIGN --dataSource APRSFISCRAPE --messagePrefix "YOURPREFIX" --netParticipantFileName scraped_messages.txt --netParticipantReportName report.pdf --netParticipantMapFileName locations.txt --netParticipantMapReportName locations.pdf --password YOURAPRS.FI_KEY --taskId "Task Id for Report" --taskname "Task name for Report" --start dateYYYY-MM-DDTHH:MM --end dateYYYY-MM-DDTHH:MM --operatorName "Your name for report" --generateReportFiles yes --generateDataFiles no

This will read "scraped_messages.txt" to build a standard 309 form with participants in "report.pdf" and create a file containing locations of participants in "locations.txt" and "locations.pdf".
You need to replace YOURCALLSIGN, RECEIVINGCALLSIGN and YOURAPRS.FI_KEY, as well as the task and operator information.

