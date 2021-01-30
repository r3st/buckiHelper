# buckiHelper

This repo helps to create a docker image for testing microprofile health exporter [bucki_exporter](https://github.com/r3st/bucki_exporter). The UI give u the power to toggle healtchecks up/down or change data of healthcheck. For testing u have three checks with data und three without.

## Build Image
Following command build local docker image r3st/buckihelper:0.0.1:

```bash
curl -s https://raw.githubusercontent.com/r3st/buckiHelper/main/scripts/createBuckiHelper.bash | bash
```
## Testing Info
Tested with following Versions of java, maven, npm and docker:
```bash
➜  ~ java --version 
openjdk 11.0.10 2021-01-19
OpenJDK Runtime Environment AdoptOpenJDK (build 11.0.10+9)
OpenJDK 64-Bit Server VM AdoptOpenJDK (build 11.0.10+9, mixed mode)
➜  ~ mvn --version              
Apache Maven 3.6.3 (cecedd343002696d0abb50b32b541b8a6ba2883f)
Maven home: /opt/maven
Java version: 11.0.10, vendor: AdoptOpenJDK, runtime: /opt/jdk-11.0.10+9
Default locale: en_US, platform encoding: UTF-8
OS name: "linux", version: "5.3.0-62-generic", arch: "amd64", family: "unix"
➜  ~ npm --version   
6.14.11
➜  ~ docker --version
Docker version 20.10.2, build 2291f61
```
## Run Image
Run docker Container:
```bash
➜  ~ docker run --name buckihelper --rm -p 8080:8080 r3st/buckihelper:0.0.1
```

## UI Example
### Example 1 - toggle healthcheck
![Example 1 - toggle healthcheck](screenshots/buckiHelperExample1.png?raw=true "toggle healtcheck")

### Example 2 - add/remove data to/from healtcheck
![Example 2 - add/remove data to/from healtcheck](screenshots/buckiHelperExample2.png?raw=true "add/remove data to/from healtcheck")