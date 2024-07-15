# User Management Application

A website with Java backend using the REST API architecture allowing to create, read, update and delete users.
The used technologies are Java, Spring Data, MySQL database, HTML and CSS templates using Thymeleaf for a more dynamic look.

### Required software

* MySQL Workbench
* IntelliJ IDEA (or another Java supported IDE)

### Before you begin
* Change the credentials in the **application.properties** file to match your MySQL username and password.
* Run the **UserManagementApplication.java** file.
* Open http://localhost:8080 from your web browser to access the full web functionality.

## How it works

The program has both frontend and backend implemented. The full functionality is available by filling the data in the browser (http://localhost:8080) and clicking on the buttons that send commands to the database. Alternatively, some REST API commands can also be executed from a tool such as Postman.

* The program automatically executes a SQL query from the **'data.sql'** file that adds 23 users in the database upon the first run. They are added in a **'users'** schema in MySQL Workbench. If you rerun the application and the ids are already present in the SQL table, they will not be updated from this query again.
* The passwords in the database are encrypted using **BCryptPasswordEncoder**. By default, each of the 23 users has an encrypted password: ```adminadmin```

## Constraints for register and update a user

The id of the user is automatically assigned by the MySQL, incrementing the last numeric value. The register/update methods are using a DTO class.

A user consists of the below attributes, each of them required:
* First name *(String: between 5 and 30 characters)*
* Last name *(String: between 5 and 30 characters)*
* Date of birth *(LocalDate: at least 18 years old; the unified format is yyyy-MM-dd)*
* Phone number *(String: between 10 and 15 characters)*
* Email *(String: a valid email that contains '@' character; also must be unique - not used by another user)*
* Password *(String: must be 10 or more characters)*

### 1. Register a user

The registration form is available at http://localhost:8080/user/register using a **GET** query.

Once the boxes with the data are filled in the frontend, the program uses a **POST** request to sent the data from the form to the backend.

### 2. Update a user by id

The update form is available at ```http://localhost:8080/user/update/{id}``` using a **GET** query, where {id} is the number of the user.
* Example: http://localhost:8080/user/update/3

Once the boxes with the data are filled in the frontend, the program uses a **PUT** request to sent the data from the form to the backend and thus updates the current used.

### 3. Delete a user by id

* **DELETE** query to ```http://localhost:8080/user/delete/{id}```
Change the *{id}* inumber with the id to delete. Example: http://localhost:8080/user/delete/3

### 4. View a user by id
* **GET** query to ```http://localhost:8080/user/id?id={number}```
Change *{number}* with the id to search for. Example: http://localhost:8080/user/id?id=10

If a user with this id is not present, you see a *'No users found'* message.

If you enter a non-numeric id it will redirect you to the page where all users from the database are returned.

### 4.1 View a list with all users
* **GET** query to http://localhost:8080/user/list
The results are ordered by last name ascending and then by birth date - older to newer dates.

### 4.2 View a list with all users matching by keyword
* **GET** query to ```http://localhost:8080/user/list?keyword={text}```
Change *{text}* with the keyword to search for. **The query is not case sensitive.** Example: http://localhost:8080/user/list?keyword=Nik

- If you leave the keyword blank, it will return all users from the database.

- The backend searches for maches from the *'firstName', 'lastName', 'phoneNumber' or 'email'* fields using a native MySQL query.

- The results are ordered by last name ascending and then by birth date - older to newer dates.

### Sample of the interface

![image](https://github.com/user-attachments/assets/cc3f0f0d-7b80-434e-ae2a-2c974467906e)
