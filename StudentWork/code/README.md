General README details for Spring Batch Master Class
----------------------------------------------------

[H2 Embedded Database admin console](http://localhost:8080/admin/h2/logout.do?jsessionid=d06adccac6d5f8590f806966ed7272ed)



    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private DataSource dataSource;


Maven
-----

To download sources and javadocs for the project

    mvn dependency:sources dependency:resolve -Dclassifier=javadoc


Gradle
------
To create Gradle scripts from the Maven scripts:

    gradle init


--- the end...