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

## Examples

You can invoke the parser from the command line:

$ java -jar aprsnetreporter-1.0-SNAPSHOT-jar-with-dependencies.jar  --help

Usage:

 --verbose - display debug information

 --help - display usage information

 --generateDataFiles - generate data files

 --generateReportFiles - generate PDF report files

 --messagePrefix "prefix" - prefix on APRS messages to include

 --callsign "callsign" - operator callsign

 --operatorName "Operator Name" - Name of the operator

 --taskId "task id" - Task Id for the Net

 --taskName "task name" - Task name for the Net

 --netParticipantFileName "filename" - Filename for Participant data file

 --netParticipantMapFileName "filename" - Filename for Participant location data file

 --netParticipantReportName "filename" - Filename for Participant report

 --netParticipantMapReportName "filename" - Filename for Participant location report

 --dataSource "APRSFI" - Data source to retrieve APRS information from (APRSFI only useful value so far)

 --password "password" - Password to retrieve APRS information from data source

 --start "time in YYYY-MM-DDTHH:MM format" - Start time for Net

 --end "time in YYYY-MM-DDTHH:MM format" - End time for Net


## Features

You can gather APRS messages sent to a callsign that have a specific prefix over a specific time period from web sites making APRS information available, such as https://aprs.fi.

You can create a CSV file containing the participants and their messages, as well as participants and their location.

You can create a PDF file containing the similar information in a prettier format.

