# Test yoga application

### Requirements

- Git
- MySQL
- Node
- OpenJDK 11

## Database

### Setup
The first step to run this full-stack application, is to set-up the database.
You'll need a MySQL server running to store the data, enter the following commands.

To make sure you're using the `3306` port :

```sql
SHOW VARIABLES LIKE 'port';
```

Create and use the new database `test` :

```sql
CREATE DATABASE test;
USE test;
```

And finally run the `sql` script :

```sql
SOURCE /path_to_file/ressources/sql/script.sql;
```

Now you should have a working database with all tables needed, let's move on the newt step.

## Front-end

### Setup

To set-up the front-end you'll need node.js installed, in the `/front` folder run the following commands :

```bash
npm install
npm run start
```

You should now having a working front, navigate to [http://localhost:4200](http://localhost:4200) to see the web application.

## Test

### Backend

To run the unit and integration tests on the back-end API you'll need openJDK with java version 11, and maven installed.

In the `/back` folder run the tests with the following command :

```bash
mvn clean test
```

Then you'll be able to find the report in HTML at this path : `/path_to_file/back/target/site/jacoco/index.html`

### Frontend

To run the unit and integrations tests on the frontend, go to the directory `/path_to_file/front/`

```bash
npm run test:coverage
```

This should output a coverage report in the terminal, you can also have a better way to read it with the html page located at : `/path_to_file/front/coverage/jest/lcov-report/index.html`

Then for the e2e tests in the `/front` folder run those following commands :

```bash
npm run e2e
```

And window should open up, choose your fav navigator to proceed to the e2e tests. Run the `allTests.cy.js` file to run all e2e tests at once. Then run the following command to get the coverage report.

```bash
npm run e2e:coverage
```

You'll find an html version of it located at : `/path_to_file/front/coverage/lcov-report/index.html`

All the tests layers should be done right now, the tests are done.
