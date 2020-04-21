# Log parser

## Prerequisites
* Java 8
* Maven

## Building
To run all unit tests and build the application run:

```
mvn clean package
```

## Testing
To run the unit tests execute:
```
mvn test
```

## Running
The application can be run in two modes:
* Interval mode
* Unlimited mode

### Interval mode:
This mode will return all hosts that connect to or receive a connection from a specific host within a specified time interval.

Command arguments:
* --mode="interval"
* --file="absolute path to logfile"
* --hostname="specified hostname"
* --start="start time epoch millis" (ex: 1565647204351)
* --end="end time epoch millis" (ex: 1565647204352)

All arguments are mandatory.

Example:
```
./target/log-parser-1.0.0.jar --mode=interval --file=/tmp/logfile.txt --hostname=Aadvik --start=1565647204351 --end=1565647204352
```
### Unlimited mode:
This mode will return all hosts that connect to a given hostname, all hosts that receive a connection from a given hostname, 
and the host that created the most connections in the last hour.

The first output will parse and output the current logfile, all subsequent outputs will recap the last 60 minutes.

Command arguments:
* --mode="unlimited"
* --file="absolute path to logfile"
* --hostname="specified hostname"
* --follow (optional). This command specifies that the program should run indefinitely, printing every 60 minutes.

Example(s):
```
./target/log-parser-1.0.0.jar --mode=unlimited --file=/tmp/logfile.txt --hostname=Aadvik
./target/log-parser-1.0.0.jar --mode=unlimited --file=/tmp/logfile.txt --hostname=Aadvik --follow
```

## Additional notes
This program was written using the spring boot framework. 

Although stated that frameworks should be avoided, spring boot helps to maintain clean dependency injection and provides
a succinct abstraction to the args[] parameters without overshadowing the design decisions taken to implement this solution.

## Authors

* **conhic** - [github](https://github.com/conhic)
