# Getting Started

### Route Fares
The application reads the fares from a flat file "fares" unders resources folder. The content of the file is in the form of a matrix. 

|        | Stop 1 | Stop 2 | Stop 3 |
|--------|--------|--------|--------|
| Stop 1 | 0      | 3.25   | 7.3    |
| Stop 2 | 3.25   | 0      | 5.5    |
| Stop 3 | 7.3    | 5.5    | 0      |

This structure gives flexibility to add more stops and route fare between them without any code change.


### Assumptions
* The customer will always use the same credit card to TAP ON and TAP OFF
* If the customer forgets to TAP ON and only TAPS OFF, it is considered as an incomplete trip and will incur maximum charges possible from the stop where TAP OFF occurred.
* The output file - trips.csv is not ordered with respect to the date & time of the trips. It is ordered by PAN number.

### Further Enhancements
* The maximum gap between TAP ON & TAP OFF is 4 hours, post that, the TAP ON & TAP OFF will be considered two individual trips.
* 

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.0/gradle-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.0/gradle-plugin/packaging-oci-image.html)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

