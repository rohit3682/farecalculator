# Getting Started

### Running the application
* Clone the source code either via SSH or HTTPS
* Goto the folder farecalculator
* Clean the code build with `./gradlew clean`
* Build the code build with `./gradlew build`
* Run the test cases with `./gradlew test`
* Run the application `./gradlew bootRun`

### Input Files
The application takes 2 files as inputs
* taps.csv - list of taps details
* fares - a matrix of fare data for every stop to every other stop. See below for more details.

### Route Fares
The application reads the fares from a flat file called "fares" under the resources folder. The content of the file is in the form of a matrix. See below.

|        | Stop 1 | Stop 2 | Stop 3 |
|--------|--------|--------|--------|
| Stop 1 | 0      | 3.25   | 7.3    |
| Stop 2 | 3.25   | 0      | 5.5    |
| Stop 3 | 7.3    | 5.5    | 0      |

This structure gives flexibility to add more stops and route fare between them without any code change. Sample fare data for 10 stops is added to `src/main/test/resources/fares` file. This data is used by the test cases.

### Output Files
The application produces summary of trips in a file called trips.csv which is created at `/tmp` location by default. The location can be changed by 
* Exporting an environment variable `TRIPS_CSV_PATH=/Users/littlepayuser` or 
* Updating the `trips.csv.path` property in the application.properties file. 

The user executing the application need write permission to the mentioned location.

### Assumptions
* The input files - taps.csv & fares are always wellformed & have valid data.
* The customer will always use the same credit card to TAP ON and TAP OFF
* If the customer forgets to TAP ON and only TAPS OFF, it is considered as an incomplete trip and will incur maximum charges possible from the stop where TAP OFF occurred.
* The output file - trips.csv is not ordered with respect to the date & time of the trips. It is ordered by PAN number.

### Further Enhancements
* The maximum gap between TAP ON & TAP OFF is 4 hours, post that, the TAP ON & TAP OFF should be considered two individual trips.
* Refactor the code to detangle FareService & TripSummaryService.

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.0/gradle-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.0/gradle-plugin/packaging-oci-image.html)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

