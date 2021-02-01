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



## Web Development

### Using SpringBoot:
1. Create a spring boot project, celect modules we need;
2. SpringBoot will auto configure all the modules we need;
3. Do little modification as we need.

### Auto Configuration:
```xml
xxxxxxxxxAutoConfiguration
xxxxxxxxxProperties:
```

### SpringBoot对静态资源的映射规则：

```java
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
            super.addResourceHandlers(registry);
            if (!this.resourceProperties.isAddMappings()) {
                logger.debug("Default resource handling disabled");
            } else {
                ServletContext servletContext = this.getServletContext();
                this.addResourceHandler(registry, "/webjars/**", "classpath:/META-INF/resources/webjars/");
                this.addResourceHandler(registry, this.mvcProperties.getStaticPathPattern(), (registration) -> {
                    registration.addResourceLocations(this.resourceProperties.getStaticLocations());
                    if (servletContext != null) {
                        registration.addResourceLocations(new Resource[]{new ServletContextResource(servletContext, "/")});
                    }

                });
            }
        }
```

#### 1. For all '''/webjars/xx''' , find resource in '''classpath:/META-INF/resources/webjars/'''

webajrs: import static resourcs in the format of a jar package.

https://www.webjars.org/

![webjarStructure](https://github.com/DaiJiChen/Spring/blob/main/images/webjar-structure.png?raw=true)

http://localhost:8080/webjars/jquery/3.5.1/jquery.js

```xml
		<!--	add dependency for jquery-->
		<dependency>
			<groupId>org.webjars</groupId>
			<artifactId>jquery</artifactId>
			<version>3.5.1</version>
		</dependency>
```

#### 2. for static resource directory "/**", find in 4 directories below.

```java
CLASSPATH_RESOURCE_LOCATIONS = new String[]{
	"classpath:/META-INF/resources/", 
	"classpath:/resources/", 
	"classpath:/static/", 
	"classpath:/public/"};
```

Example: localhost:8080/asserts/js/Chart.min.js

![static_res_structure](static-res.jpg)


#### 3. welcome page: find index.html under static resources directory

localhost:8080/

```java
private Resource getWelcomePage() {
            String[] var1 = this.resourceProperties.getStaticLocations();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                String location = var1[var3];
                Resource indexHtml = this.getIndexHtml(location);
                if (indexHtml != null) {
                    return indexHtml;
                }
            }

            ServletContext servletContext = this.getServletContext();
            if (servletContext != null) {
                return this.getIndexHtml((Resource)(new ServletContextResource(servletContext, "/")));
            } else {
                return null;
            }
        }
```

## thymeleaf

#### Improt starter

```xml
<properties>
        <!--	change default thymeleaf version	-->
	<thymeleaf.version>3.0.9.RELEASE</thymeleaf.version>
	<thymeleaf-layout-dialect.version>2.2.2</thymeleaf-layout-dialect.version>
</properties>

<dependencies>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-thymeleaf</artifactId>
	</dependency>
</dependencies>
```

#### expressions

```properties
Simple expressions:
    Variable Expressions: ${...}：gt variable；OGNL；
    		1）、get property or call method of an object
    		2）、使用内置的基本对象：
    			#ctx : the context object.
    			#vars: the context variables.
                #locale : the context locale.
                #request : (only in Web Contexts) the HttpServletRequest object.
                #response : (only in Web Contexts) the HttpServletResponse object.
                #session : (only in Web Contexts) the HttpSession object.
                #servletContext : (only in Web Contexts) the ServletContext object.
                
                ${session.foo}
            3）、内置的一些工具对象：
#execInfo : information about the template being processed.
#messages : methods for obtaining externalized messages inside variables expressions, in the same way as they would be obtained using #{…} syntax.
#uris : methods for escaping parts of URLs/URIs
#conversions : methods for executing the configured conversion service (if any).
#dates : methods for java.util.Date objects: formatting, component extraction, etc.
#calendars : analogous to #dates , but for java.util.Calendar objects.
#numbers : methods for formatting numeric objects.
#strings : methods for String objects: contains, startsWith, prepending/appending, etc.
#objects : methods for objects in general.
#bools : methods for boolean evaluation.
#arrays : methods for arrays.
#lists : methods for lists.
#sets : methods for sets.
#maps : methods for maps.
#aggregates : methods for creating aggregates on arrays or collections.
#ids : methods for dealing with id attributes that might be repeated (for example, as a result of an iteration).

    Selection Variable Expressions: *{...}：
    	补充：配合 th:object="${session.user}：
   <div th:object="${session.user}">
    <p>Name: <span th:text="*{firstName}">Sebastian</span>.</p>
    <p>Surname: <span th:text="*{lastName}">Pepper</span>.</p>
    <p>Nationality: <span th:text="*{nationality}">Saturn</span>.</p>
    </div>
    
    Message Expressions: #{...}：
    Link URL Expressions: @{...}：
    		@{/order/process(execId=${execId},execType='FAST')}
    Fragment Expressions: ~{...}：
    		<div th:insert="~{commons :: main}">...</div>
    		
Literals
      Text literals: 'one text' , 'Another one!' ,…
      Number literals: 0 , 34 , 3.0 , 12.3 ,…
      Boolean literals: true , false
      Null literal: null
      Literal tokens: one , sometext , main ,…
Text operations:
    String concatenation: +
    Literal substitutions: |The name is ${name}|
Arithmetic operations:
    Binary operators: + , - , * , / , %
    Minus sign (unary operator): -
Boolean operations:
    Binary operators: and , or
    Boolean negation (unary operator): ! , not
Comparisons and equality:
    Comparators: > , < , >= , <= ( gt , lt , ge , le )
    Equality operators: == , != ( eq , ne )
Conditional operators:
    If-then: (if) ? (then)
    If-then-else: (if) ? (then) : (else)
    Default: (value) ?: (defaultvalue)
Special tokens:
    No-Operation: _ 
```


## Spring MVC configuration

https://docs.spring.io/spring-boot/docs/1.5.10.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications

### 1. Spring MVC Auto-Configuration

Spring Boot provides auto-configuration for Spring MVC that works well with most applications.

#### auto-configuration includes:

- Inclusion of ViewResolver: `ContentNegotiatingViewResolver` and `BeanNameViewResolver` beans.

	- `ContentNegotiatingViewResolver` combines all viewResolver
	
	- we can add a viewResolver to the container and it will be automatically integrted.

- Support for serving static resources, including support for WebJars.

	- static resource directory and webjars

- Automatic registration of `Converter`, `GenericConverter`, `Formatter` beans.

	- Converter: 转换器; data from page is text, Converter translate it into format we need such as int, boolean, object, etc.
	
	- Formatter: 格式化器; string "2017-12-17" --> Data object
```java
	@Bean
        public FormattingConversionService mvcConversionService() {
            Format format = this.mvcProperties.getFormat();
            WebConversionService conversionService = new WebConversionService((new DateTimeFormatters())
	    .dateFormat(format.getDate())
	    .timeFormat(format.getTime())
	    .dateTimeFormat(format.getDateTime()));
	    
            this.addFormatters(conversionService);
            return conversionService;
        }
	
	public void addFormatters(FormatterRegistry registry) {
            ApplicationConversionService.addBeans(registry, this.beanFactory);
        }
```

- Support for `HttpMessageConverters`.

	- `HttpMessageConverters` is used by springMVC to convert Http request and response; User -> json; 
	
	- `HttpMessageConverters` get all HttpMessageConverter from container. we can add our own HttpMessageConverter to container（@Bean, @Component

- Automatic registration of `MessageCodesResolver` (see below).

- Static `index.html` support.

- Custom `Favicon` support.

- Automatic use of a `ConfigurableWebBindingInitializer` bean.

**org.springframework.boot.autoconfigure**  : all auto configuration for web

If you want to keep Spring Boot MVC features, and you just want to add additional MVC configuration (interceptors, formatters, view controllers etc.) you can add your own @Configuration class of type WebMvcConfigurerAdapter, but without @EnableWebMvc. If you wish to provide custom instances of RequestMappingHandlerMapping, RequestMappingHandlerAdapter or ExceptionHandlerExceptionResolver you can declare a WebMvcRegistrationsAdapter instance providing such components.

If you want to take complete control of Spring MVC, you can add your own @Configuration annotated with @EnableWebMvc.

```java
@Configuration
public class myMvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/jichen").setViewName("Hello");
    }
}
```
