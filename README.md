# 2020-JDBC-ORM

Lab: Workshop – MiniORM 
This document defines the exercise assignments for the "Spring Data" course @ Software University.
By following the guides of this document you will be able to create your custom ORM with basic functionality (insert, update and retrieve single or set of objects). It will have options to work with already created tables in a database or create new tables if such are not present yet.
1.	Setup
Create a new Maven project in IntelliJ.

2.	Create Entities
In the means of ORMs, database objects are mapped to object-oriented implementations called “entities”. They are objects that analogically to database tables, for e.g. users, hold fields containing user’s main characteristics – id, username, first_name, last_name, age and etc. 
In the “java” folder create a package called entities where will every one of our entities be. Now let’s create class User with fields and properties (id, username, password, age, registrationDate). Create a constructor that sets all fields except id – it’s auto incremented on database level. Order of the parameters in the constructor must be the same as the sequence of columns in the table in the database. Add Getters and Setters for all fields.
 
3.	Create Database Connection
Unlike all previous tasks, it’s time to start separating our logic into classes.
Create new package “orm” and  class Connector in it that generates connection with our database. In order to achieve this, we would require the following parameters:
•	Username – database username 
•	Password – database password
•	Database Name – the current database for the project. We need to create one manually. 
 
4.	Create Database Context
It’s time to create an interface that will define the operations we can perform with the database. Name your interface 	 and defined the following methods in it.
•	boolean persist(E entity) – it will insert or update entity depending if it is attached to the context
•	Iterable<E> find(Class<E> table) – returns collection of all entity objects of type E
•	Iterable<E> find(Class<E> table, String where) – returns collection of all entity objects of type T matching the criteria given in “where”
•	E findFirst(Class<E> table) – returns the first entity object of type E
•	E findFirst(Class<E> table, String where) – returns the first entity object of type E matching the criteria given in “where”
 
5.	Create Entity Manager
Enough with the preparation it’s time to write the core of our Mini ORM. That would be the EntityManager class that will be responsible for inserting, updating and retrieving entity objects. That class would implement methods of the DbContext interface. It will require a Connection object that would be initialized with a given connection string. 
 
6.	Persist Object in the Database
The logic behind the persist method is pretty simple. First the method should check if the given object to be persisted is not contained in the database and if not add it, otherwise update its properties with the new values. The method returns whether the object was successfully persisted in the database or not.
But how can we check if the user that we are trying to persist is new to the database or have to update it? We can do that by checking the value of it’s id field and if it’s not empty that means we’re trying to insert it. But the method works with a generic type – E and we don’t have direct access to it’s getter methods – for example getId. 
In order to minimize concretics and work with other entities in the future(not only User) we have to access the field some other way.  One thing that can help us is Annotations. 
Create 3 annotations Entity, Column and Id.

Annotate entities and their corresponding fields. In the Entity annotation specify the name of the database table you want to be mapped – users and in Column – the corresponding table column name to the java field.
We are going to need a new additional method getId(Class entity) in the EntityManager class. It will return the id field via reflection:
 
If returned value is null we need to do an insert, otherwise update. So far the persist method should look like this:
 
We need to implement 2 more methods:
•	private boolean doInsert(E entity, Field primary)
•	private boolean doUpdate(E entity, Field primary)
Both methods would prepare query statements and execute them.
The difference between them is when you insert new entity you should set its id. The Id is generated on database level. Both methods return whether the entity was successfully persisted.
Here are some tips for the Insert method: 
•	Get the table name you will be inserting into
•	Start joining the components of your query – INSERT clause, table name + fields, VALUES and the corresponding values for the insertion 
HINT: Iterate over entity’s fields 
•	Prepare and execute statement via the connection
 
And some tips for the update method:
•	Get the table name you will be updating into
•	Start joining the components of your query – UPDATE clause, table name, SET, WHERE and the given predicate
HINT: Iterate over entity’s fields and add “id = {entity’s id value}” to WHERE clause
•	Prepare and execute statement via the connection
 
7.	Fetching Results
Finally, when we have persisted our entities (objects) in the database let ‘s implement functionality to get them out of the database and persist them in the operating memory. We would implement just several methods. That would be all Find methods from the DbContext.
Here are some tips of how to implement public E findFirst(Class<E> table, String where) the other ones are similar and they would be on you. 
 
Here you can see that we used a new method fillEntity. That method receives a ResultSet object, retrieves information from the current row of the reader and fills the data. Create two methods:
 
Both methods cooperate closely. FillEntity calls for fillField and passes the entity which fields have to be filled, current field, the ResultSet object from which we will get information and field’s Column annotation name, which will give us access to the value in the ResultSet.
8.	Test Framework
If you came to this point you are done with building the first part of our MiniORM. Now let’s test it to make sure it works as expected. Create several users and persist them in the database. Then update some of the properties of the users (e.g., change password or increase age or some other change). Remember you need to use the persist method to commit changes of the object to the database. Make sure the data is always updated in the database. Here is some example of usage:
 
9.	Fetch Users
Insert several users in the database and print the usernames and passwords of those who are registered after 2010 year and are at least 18 years old.
