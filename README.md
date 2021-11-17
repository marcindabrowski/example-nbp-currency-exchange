example-nbp-currency-exchange
==============================

Service with example REST API for returning client account balance exchanged
from PLN to EUR using newest NBP exchange rate obtained using http://api.nbp.pl/.

## Prerequisites

Java 17

## Building

```./gradlew build```

## Running

```./gradlew bootRun```

## Running units tests

```./gradlew unitTest```

## Running units tests

```./gradlew integrationTest```

## Running all tests

```./gradlew test```

## Running API tests on locally available application

From InteliJ run all tests in file [account-balance-in-euro.http](http/account-balance-in-euro.http).
