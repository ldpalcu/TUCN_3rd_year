# Assignment 2

## Objective
The objective of this assignment is to allow students to become familiar with the Model-View-Controller architectural pattern and the Factory Method design pattern.

## Application Description
Use Java or C# to design and implement a desktop application for Disneyland. The application should have two types of users (a regular user and an administrator user) which have to provide a username and a password in order to use the application.

The regular user can perform the following operations:
* View list of activities, and search them by different properties.
* Register for open & available activities. An activity is like an OOP class, so it has multiple "instances" (occurrences). An example of activity is Horse Riding, which can have multiple slots open, at which users register.

The administrator can perform the following operations:
* CRUD on activities (different properties, like location, name, max users, availability).
* CRUD on regular users' information.
* Generate two types of report files, one in pdf format and one in txt or html format, with the activities, and registered users in them. The reports **need** to be saved in a **user-selected location** (not predefined by the application), similar on how one would save a file from Notepad.

## Application Constraints
* The data used in the application will be stored in a database, using an ORM (Object Relational Mapping) framework.
* Use the Model-View-Controller (or Model-View-ViewModel if using WPF) in designing the application. It's **mandatory** that the Controllers/ViewModels don't have direct reference to the View class (e.g., the one that inherits from JFrame or JPanel), but through an interface if really needed.
* Use the Factory Method design pattern for generating the reports (i.e., selecting between the type of report to generate).
* All the inputs of the application will be validated against invalid data before submitting the data and saving it.
* Unit test the **users-related** controller(s)/view model(s) using a testing framework (e.g., JUnit, NUnit). Use a Mocking framework (e.g., Mockito, Moq) to mock dependencies on repositories/views. The tests **should not** need nor have direct access to the database.

## Requirements
* Implement and test the application.
* Commit the work you do on your Git repository. Do it **iteratively** as you progress, not all at a time (this will incur a penalty on your final mark).

## References
### Architecture
* [Factory Method Pattern](https://sourcemaking.com/design_patterns/factory_method)
* [MVC meets Swing](https://www.javaworld.com/article/2076632/core-java/mvc-meets-swing.html)
* [MVVM](https://msdn.microsoft.com/en-us/magazine/dd419663.aspx)
* [UI Architectures](https://martinfowler.com/eaaDev/uiArchs.html)

### .NET Technologies
* [.NET Desktop Applications](https://msdn.microsoft.com/en-us/library/54xbah2z(v=vs.110).aspx)
* [Entity Framework](https://msdn.microsoft.com/en-us/library/aa937723(v=vs.113).aspx)
* [NUnit](https://github.com/nunit/docs/wiki/Installation)
* [NUnit tests](https://github.com/nunit/docs/wiki/TestFixture-Attribute)
* [Moq](https://github.com/Moq/moq4/wiki/Quickstart)

### Java Technologies
* [Java FX](https://www.jetbrains.com/help/idea/javafx.html)
* [Swing](http://docs.oracle.com/javase/tutorial/uiswing/)
* [Hibernate](http://docs.jboss.org/hibernate/orm/current/quickstart/html_single/)
* [Junit](https://junit.org/junit5/docs/current/user-guide/)
* [Mockito](http://site.mockito.org/#how)

# Assignment 3

## Objective
The objective of this assignment is to allow students to become familiar with the client-server architectural style and the Observer design pattern.

## Application Description
Start from the previous application, done as assignment 2.

Transform the application into a client-server one. The layer that did the persistence should be moved to the server, and the client can only retrieve that data by communicating with the server. Most of the business logic needs to be on the server side, unless something is needed to be client-side (e.g., report generator)

At a given time, there can be multiple users connected via multiple clients to the same server.

New features:
* Users can queue-register at activities that are already fully booked and they will get a live notification when a spot is opened up and their place was automatically booked.
* Users can unregister before the event starts
* The admin can change activity's state to one of the following: scheduled (the initial state when the activity is created), in progress, done.

## Application Constraints
* The application should be client-server and the data on the server will be stored in a database. Use the Observer design pattern for the live notifications (usually this is done server-side, because it's there where we know the list of clients). The notifications should **only** be done for the currently connected clients; so if a relevant client is not online, he won't get the live notification
* All the inputs of the application will be validated against invalid data before submitting the data and saving it.
* Unit test the activities-related controller(s)/view model(s) using a testing framework (e.g., JUnit, NUnit). Use a Mocking framework (e.g., Mockito, Moq) to mock dependencies on repositories/views/server connection. The tests **should not** need direct access to the client or the server.

## Requirements
* Implement and test the application.
* Commit the work you do on your Git repository. Do it **iteratively** as you progress, not all at a time (this will incur a penalty on your final mark).

## References
* [Java Sockets](http://download.oracle.com/javase/tutorial/networking/sockets/index.html)
* [.NET Sockets](https://docs.microsoft.com/en-us/dotnet/framework/network-programming/socket-code-examples)

# Final Project

## Objective
This is the final part of the project, which aims at completing the overall application as envisioned by the Vision document.

## Application Description
Start from the previous application, done as assignment 2 and 3.

Final features:
* Regular Users can rate once the activities (between 1-5 stars) and also search by rating. The admin user can edit the ratings.
* Regular users can remove activities from their favourites list
* Administrator can change user's subscription which limits the number of activities he can participate in one day. The subscriptions can be added/changed by the admin.

## Application Constraints
* All the inputs of the application will be validated against invalid data before submitting the data and saving it.
* Ensure that your code doesn't violate any good practices/conventions nor has any issues as detected by the following tools:
  * Java code: [PMD](https://pmd.github.io/#windows)
  * C# code: [ReSharper Command Line Tools](https://www.jetbrains.com/resharper/download/index.html#section=resharper-clt)
* Unit test most of the functionality of the application, so it reaches at least **60%** code coverage. Use a Mocking framework (e.g., Mockito, Moq) to mock dependencies where needed.

## Requirements
* Implement and test the application.
* Commit the work you do on your Git repository. Do it **iteratively** as you progress, not all at a time (this will incur a penalty on your final mark).
* Put the report of running those validation applications in the project's directory.
* Complete "Iteration 2" and "Construction and Transition" sections of the project_analysis_and_design.md document with the final data, updating the diagrams where it seems necessary.
* Make sure the repository is clean of irrelevant files, and respects the structure.

## References
* [IntelliJ IDEA Code Coverage](https://www.jetbrains.com/help/idea/code-coverage.html)
* [Eclipse Code Coverage](https://www.eclemma.org/)
* [Visual Studio Code Coverage - if you don't own the Enterprise version](https://marketplace.visualstudio.com/items?itemName=axodox1.AxoCover&showQnADialog=true)
* [Visual Studio Code Coverage - **only** for the Enterprise version](https://msdn.microsoft.com/en-us/library/dd537628.aspx)
