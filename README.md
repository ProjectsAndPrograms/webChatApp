# WebChatApplication

A web based chat application created in java using Servlet, JSP, MySQL, CSS, JavaScript. It need atleast Apache Tomcat 10 web server to run.<br>
You can chat with your friends if you deploy this. Otherwise you can use a remote database, once you add tables on database you can simply distibute this application with your friends by using war files. 

## Technologies Used 
  1. Servlet 
  2. JSP
  3. HTML, CSS, JavaScript
  4. MySQL
  5. Apache Tomcat-10 web server
  6. Created in Eclipse Enterprise Edition IDE

## Screenshots

<div style="display: flex;flex-direction: column; grid-gap: 10px;">
    <div style="display: flex; grid-gap: 10px;">
        <img src="src/main/webapp/images/ch1.png" alt="screenshots" width="49%" style="border: 2px solid lightgreen"/>
        <img src="src/main/webapp/images/ch2.png" alt="screenshots" width="49%" style="border: 2px solid lightgreen"/>
    </div>
    <div style="display: flex; grid-gap: 10px;">
        <img src="src/main/webapp/images/ch3.png" alt="screenshots" width="49%" style="border: 2px solid lightgreen"/>
        <img src="src/main/webapp/images/ch4.png" alt="screenshots" width="49%" style="border: 2px solid lightgreen"/>
    </div>
</div>
<br>

## How to Use ?
  To run this application you need to set up this project on your Eclipse EE IDE here is how to do that : 

  Step-1 : After downloading, you have to keep this project on your workspace. Check your workspace from Eclipse also your can change your workspace by 
   file->SwitchWorkspace->Other
   and choose your workspace.

  Stop-2 : Download web development tools in your Eclipse IDE.<br>
   Go to  Help->Eclipse Marketplace->on search tab you can search for tools search 'web' on search area and download the tool <br>
          suggested one is : Eclipse Enterprise Java and web developer tools 3.31<br>
          Or you can download anyone who supports servlet, jsp, and others mentioned above.

 Step-3 : Download Apache tomcat server make sure you use tomcat 10 and above. You can download this form official website [tomcat.apache.org](https://tomcat.apache.org/download-10.cgi), also Unzip it.

Step-4: Setup Tocat with Eclipse : you can setup tomcat go to Windows->Show Views->Servers<br>
A new tab will open where console located, with the name of server click on the given link to add new server -> choose your company and version of server you intalled, in my case this is Apache->Tomcat 10 ->click on next-> locate the Apache tomcat directory you downloaded in previous step.-> Hit on next if available otherwise click on finish.

Step-5: Open Your project in eclipse : 
     go to file->Open Project from file system-> locate your workspace and select the project(WebChatApp) folder -> click on open -> finish

Step-6: Database Setup : to setup your database you can use the file database/chat.sql.
  using phpmyadmin = if you are using php myadmin then simpley create a new database with the name of 'chat' and then you can import file database/chat.sql from your import tab.
  
  using mysql command line client OR workbench - 
   you can simple run the given queries to create your database setup: 
    
    creating a database
    ```sql
      create database chat;
    ```
    using the database- 
     ```sql
      use chat;
    ```
    creating a table for messages - 
     ```sql
      CREATE TABLE `messages` (
  `msg_id` int(20) NOT NULL AUTO_INCREMENT,
  `incoming_msg_id` varchar(255) NOT NULL,
  `outgoing_msg_id` varchar(255) NOT NULL,
  `msg` varchar(1000) NOT NULL,
   PRIMARY KEY (`msg_id`)) AUTO_INCREMENT = 100;;
    ``` 
    creating a table for users - 
    
    ```sql
    CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `unique_id` varchar(200) NOT NULL,
  `fname` varchar(255) NOT NULL,
  `lname` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `img` varchar(400) NOT NULL,
  `status` varchar(255) NOT NULL,
 PRIMARY KEY (`msg_id`)) AUTO_INCREMENT = 100;;
    ```
    
  
Step-7: Now you are ready to run the application: Right click on project(WebChatApp)->Run As->Run on Server->it may ask you what applications you want to run choose  WebChatApp and finish
 
Step-8: After Starting the server you can access the application by your browser: open your browser and search for [http://localhost:8080/ChatApp/](/)

Step-9: I hope this will helpful for you.
```bash
pip install foobar
```

## Usage

```python
import foobar

# returns 'words'
foobar.pluralize('word')

# returns 'geese'
foobar.pluralize('goose')

# returns 'phenomenon'
foobar.singularize('phenomena')
```

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License

[MIT](https://choosealicense.com/licenses/mit/)
