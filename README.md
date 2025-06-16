# Shell Bank (Java CLI Banking App)

A simple command-line banking application written in Java.

## Preview

The app runs in a loop and shows different menu options depending on whether the user is logged in:

### Main Menu (Not Logged In)

```
---------------
  SHELL BANK
---------------
1. Login
2. SignUp
3. Exit
```

### User Menu (After Login)

```
---------------
  SHELL BANK
---------------
1. Show Balance
2. Deposit
3. Withdraw
4. Logout
```

## Requirements

- Java 17+ (or compatible version)
- MySQL database

## Setup Instructions

1. **Clone the repository or copy the source code**

2. **Set up MySQL database**

```sql
CREATE DATABASE bank_app;

USE bank_app;

CREATE TABLE accounts (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255),
  password VARCHAR(255),
  balance DOUBLE
);
```

3. **Compile and Run**

```bash
javac -cp .:lib/mysql-connector-j-9.1.0.jar App.java Database.java
java -cp .:lib/mysql-connector-j-9.1.0.jar App
```
