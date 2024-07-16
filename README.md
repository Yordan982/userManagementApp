# User Management Application

A website with Java backend using the REST API architecture allowing to create, read, update and delete users.
The used technologies are Java, Spring Data, MySQL database, HTML and CSS templates using Thymeleaf for a more dynamic look.

### Required software

* MySQL Workbench
* IntelliJ IDEA
* Postman (optional: for REST API requests)

### Before you begin
* Change the credentials in the **application.properties** file to match your MySQL username and password.
* Run the **UserManagementApplication.java** file.
* Open http://localhost:8080 from your browser to access the user-friendly website.
* Or open Postman to use REST API calls.

## How it works

The program has both frontend and backend implemented. The frontend website is available at http://localhost:8080.

* The program automatically executes a SQL query from the **'data.sql'** file that adds 23 users in the database upon the first run. They are added in a **'users'** schema in MySQL Workbench. If you rerun the application and the ids are already present in the SQL table, they will not be updated from this query again.
* The passwords in the database are encrypted using **BCryptPasswordEncoder**. By default, each of the 23 users has an encrypted password: ```adminadmin```

### Accepted user data

The id of the user is automatically assigned by the MySQL, incrementing the last numeric value. The register/update methods are using DTO classes.

A user consists of the below attributes, each of them required:
* First name *(String: between 5 and 30 characters)*
* Last name *(String: between 5 and 30 characters)*
* Date of birth *(LocalDate: at least 18 years old; the unified format is yyyy-MM-dd)*
* Phone number *(String: between 10 and 15 characters)*
* Email *(String: a valid email that contains '@' character; unique - not used by another user)*
* Password *(String: 10 or more characters)*

## REST API Documentation

### 1. Register a user

* **POST** query to http://localhost:8080/api/user/register

Example of a valid raw Body:
```
{
    "firstName": "Luchezar",
    "lastName": "Balev",
    "dateOfBirth": "1973-04-25",
    "phoneNumber": "0883697859",
    "email": "lucho.b73@mail.bg",
    "password": "luCHO_5656"
}
```
Example of the expected response:
``` User registered successfully ```

If the user does not meet the required DTO requirements, the response will contain the data that has to be modified, each on a new line. For example:

```
User must be at least 18 years old
Last name must be between 5 and 30 characters
Password must be at least 10 characters
```

### 2. Read a user

* **GET** query to http://localhost:8080/api/user/get/3

The user id number at the end of the URL can be changed.

Example of the expected response:

``` 3. Name: Petko Aleksiev, birthday: 2005-06-15, phone: 0880284739, email: petko6@abv.bg ```

If you enter a user id that does not exist, a response message is received: ```User id: 93 does not exist```

### 3. Update a user

* **PUT** query to http://localhost:8080/api/user/update/3

The user id number at the end of the URL can be changed.

Example of a valid raw Body:
```
{
    "firstName": "Denica",
    "lastName": "Velcheva",
    "dateOfBirth": "1973-04-25",
    "phoneNumber": "0875948590",
    "email": "denica_velcheva@gmail.com"
}
```
Example of the expected response:
``` User 3 updated successfully ```

If the user does not meet the required DTO requirements, the response will contain the data that has to be modified, each on a new line. For example:

```
This email address is already used by another user
```

### 4. Delete a user

* **DELETE** query to http://localhost:8080/api/user/delete/3

The user id number at the end of the URL can be changed.

Example of the expected response:
``` User id: 3 was deleted successfully ```

If you enter a user id that does not exist, a response message is received: ``` User id: 93 does not exist ```

### 5. Read all users
* **GET** query to http://localhost:8080/api/user/list

The results are ordered by last name ascending and then by birth date - older to newer dates.

```
11. Name: Nikolai Aleksiev, birthday: 1975-12-29, phone: 0894768322, email: nikooo_7@abv.bg
3. Name: Petko Aleksiev, birthday: 2005-06-15, phone: 0880284739, email: petko6@abv.bg
22. Name: Leila Bogdanska, birthday: 2001-09-11, phone: 0893779480, email: dani35@mail.com
...
```

If the database does not have any users, the response is:
``` No users found ```

### 5.1 Read all users by keyword

* **GET** query to http://localhost:8080/api/user/list/Nik
  The ``` Nik ``` keyword at the end of the URL can be changed with the text/number to search for. The keyword is not case sensitive.

The backend finds all matches from the first name, last name, phone number or email and adds them to a List of users.

The results are ordered by last name ascending and then by birth date - older to newer dates.

Example of the response:

```
11. Name: Nikolai Aleksiev, birthday: 1975-12-29, phone: 0894768322, email: nikooo_7@abv.bg
9. Name: Nikoleta Georgieva, birthday: 2005-04-10, phone: 359887397869, email: nikityy20@abv.bg
20. Name: Nikolinka Petrova, birthday: 1977-06-18, phone: 0884350127, email: niki_p@abv.bg
...
```
If no users match the keyword, the response is:
``` No users found for keyword: Boris ```

### Sample of the web interface

To interact with the web version of the program, go to http://localhost:8080

![image](https://github.com/user-attachments/assets/cc3f0f0d-7b80-434e-ae2a-2c974467906e)