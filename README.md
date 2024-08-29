# Cron expression parser

This is a gradle project, you will need JDK-17 to run this.

### Building the project

From the project root run

```
./gradlew build
```

### Running the script
From the project root run
```
java -jar ./build/libs/cronparser-1.0-SNAPSHOT.jar "*/15 0 1,15 * 1-5 /usr/bin/find"
```

It should yield the following output
```
minute        0 15 30 45
hour          0
day of month  1 15
month         1 2 3 4 5 6 7 8 9 10 11 12
day of week   1 2 3 4 5
command       /usr/bin/find
```

