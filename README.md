# Notes

## 2. Logging 

### Logging Framework

An abstract logging Framework and a concrete Framework
- Abstract Logging Layer: ~~JCL(too old)~~, ~~jboss-logging~~, SLF4J
- Concrete Implementation: ~~Log4j(LogBack is an improvement from Log4J)~~, Log4j2, LogBack

Spring use **JCL**

SpringBoot use **SLF4j** and **LogBack**


![Image of SLF4J](http://www.slf4j.org/images/concrete-bindings.png)




### SLF4j usage

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorld {
  public static void main(String[] args) {
    Logger logger = LoggerFactory.getLogger(HelloWorld.class);
    logger.info("Hello World");
  }
}
```



### problem

How to keep a unified logging output when there are many kinds of different logging frameworks in our project？

For example: If a Project A (slf4j + logback) : Spring(Commoms-logging), Hibernate(jboss-logging), MyBatic, XXXX.

**Answer**
- 1. remove the original logging framework, replace with another framework
- 2. bridging modules which redirect calls made to log4j, JCL and java.util.logging APIs to behave as if they were made to the SLF4J API instead
- 3. import the framewrok we need.


![slf4j-legacy](http://www.slf4j.org/images/legacy.png)


#### Legacy example in Spring-boot
![legacy example in springboot](https://github.com/DaiJiChen/Spring/blob/main/images/logging-bridge-module-example-in%20spring-boot.jpg)

**If we import another framework, we only need to remove the default framework, spring-boot already configured SLF4j bridging modules for us**

```xml
<dependency>
  <groudId>org.springframework</groupId>
  <artifactId>spring-core</artifactId>
  <exclusions>
    <exclusion>
      <groupId>commons-logging</groupId>
      <artifactId>common-logging</artifactId>
    </exclusion>
  </exclusions>
</dependency>
```

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class SpringBoot03LoggingApplicationTests {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	void contextLoads() {
  
    // priority level from low to high
		logger.trace("trace");
		logger.debug("debug");
		// springboot default use info level, we can modify level in configuration file
		logger.info("info");
		logger.warn("warnning");
		logger.error("error");
	}
}
```


### Spring boot custom log config
See: [spring boot official manual](https://docs.spring.io/spring-boot/docs/2.1.1.RELEASE/reference/html/boot-features-logging.html#boot-features-custom-log-configuration)

## switch logging framework

