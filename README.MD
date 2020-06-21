Coding task for the SoftIndustry
============

## Implemented functionality:

* Authorization with email/password secured by JWT
* Create user with roles: "ROLE_ADMIN" or "ROLE_USER" (Run as "ROLE_ADMIN")
* Create deposits for users in currencies (EUR, USD, UAH, RUR)
* Exchange rates from "Privat Bank"
* Transactions: Withdraw, Deposit (with exchange rates)
* All endpoint in Swagger documentation : http://localhost:8080/swagger-ui.html

Default admin username / password: <b>root@default.com</b> / <b>toor</b>
 
## <a id="anchor"></a> Running the application locally by JVM

The application can be started locally using the following command:

~~~
$ ./gradlew clean build
$ java -jar build/libs/*.jar
~~~