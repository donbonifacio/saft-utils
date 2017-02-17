
test:
	mvn test

ci:
	mvn -Dcobertura.report.format=xml \
			-DsourceEncoding=utf8 \
			clean \
			cobertura:cobertura \
			sonar:sonar \
			coveralls:report

uberjar:
	mvn clean assembly:assembly -DdescriptorId=jar-with-dependencies

fh: uberjar
	java -Dorg.slf4j.simpleLogger.defaultLogLevel=trace -jar target/saft-utils-0.1.0-jar-with-dependencies.jar -op diff -file1 resources/tests/fh.xml -file2 resources/tests/fh2.xml
