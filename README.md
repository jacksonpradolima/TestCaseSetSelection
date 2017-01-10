# TestCaseSetSelection
## A test case set selection using multiobjective optimazation

[![Gittip](https://img.shields.io/badge/Latest%20stable-1.0-green.svg?style=flat-squared)]()
[![Gittip](https://img.shields.io/badge/build-passing-brightgreen.svg)]()

# Requirements

- Java 1.7 or above to run
- Maven >= 3.3.9

# How do I start?

1. Dowload the project;
2. Unzip in some directory of your choise;
3. Make the command:

```
mvn install
```

4. The target folder will be created. 
5. Go to the target folder;
6. Run the jar with dependencies using the command below to see the "help":

```
java -jar TestCaseSetSelection-*version*-jar-with-dependencies -help
```

7. Or run the command below:

```
java -jar TestCaseSetSelection-*version*-jar-with-dependencies -p=src\\main\\resources\\matrixState.csv -ds=; -sh=true -r=1
```

8. Examples are available in the project.

# Tips
## About the parameters

**-help** or *-h*: Display the commands available.
**--path** or *-p*: The path to the problem file. (required)
**--dataSeparator** or *-ds*: Data separator. (required)
**--skipHeader** or *-sh*: Skip header? (required)
**--runs** or *-r*: The number of runs to average the results. (optional, default 1)
