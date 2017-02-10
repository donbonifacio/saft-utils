# SAF-T Utilities

[![Build Status](https://travis-ci.org/donbonifacio/saft-utils.svg?branch=master)](https://travis-ci.org/donbonifacio/saft-utils) [![Coverage Status](https://coveralls.io/repos/github/donbonifacio/saft-utils/badge.svg?branch=master)](https://coveralls.io/github/donbonifacio/saft-utils?branch=master) [![Technical debt ratio](https://sonarqube.com/api/badges/measure?key=code.donbonifacio:saft-utils&metric=sqale_debt_ratio)](https://sonarqube.com/dashboard?id=code.donbonifacio%3Asaft-utils) [![Lines of Code](https://sonarqube.com/api/badges/measure?key=code.donbonifacio:saft-utils&metric=ncloc)](https://sonarqube.com/dashboard?id=code.donbonifacio%3Asaft-utils) [![Vulnerabilities](https://sonarqube.com/api/badges/measure?key=code.donbonifacio:saft-utils&metric=vulnerabilities)](https://sonarqube.com/dashboard?id=code.donbonifacio%3Asaft-utils) [![Code Smells](https://sonarqube.com/api/badges/measure?key=code.donbonifacio:saft-utils&metric=code_smells)](https://sonarqube.com/dashboard?id=code.donbonifacio%3Asaft-utils)

### Dev Tools

* `make test` - runs the tests
* `make ci` - runs the continuous integration suite
* `make uberjar` - generates the standalone uberjar file

## CLI

With the project jar you can run the utilities at the command line.

### SAF-T Diff

The diff compares two SAF-T files and outputs its differences. It's meant for
comparing different SAF-T generator implementations, and make sure that
they are generating the same content.

```
java -jar saft-utilities.jar -op diff -file1 file1.xml -file2 file2.xml
```

## Java Lib

You can use this lib programatically with Java. It requires Java8. You can
use the [SaftLoader](https://github.com/donbonifacio/saft-utils/blob/master/src/main/java/code/donbonifacio/saft/SaftLoader.java)
class to load the SAF-T from a file (or String) into
a [AuditFile](https://github.com/donbonifacio/saft-utils/blob/master/src/main/java/code/donbonifacio/saft/elements/AuditFile.java)
class. This class has all the SAF-T elements.


## SAF-T Diff

With two `AuditFile`s ready, you
can use [SaftDiff](https://github.com/donbonifacio/saft-utils/blob/master/src/main/java/code/donbonifacio/saft/SaftDiff.java)
to generate the differences between the SAF-Ts.

```java
AuditFile f1 = SaftLoader.loadFromFile("file1.xml");
AuditFile f2 = SaftLoader.loadFromFile("file2.xml");

SaftDiff diff = new SaftDiff(f1, f2);
Result result = diff.process();

System.out.println(result);
```
