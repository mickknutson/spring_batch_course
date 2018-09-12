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


Circle-CI
    [Circle-ci blog](https://medium.com/@ayltai/all-you-need-to-know-about-circleci-2-0-with-firebase-test-lab-2a66785ff3c2)
Need o investigate CCI workflows:
https://circleci.com/docs/2.0/workflows/

--- the end...