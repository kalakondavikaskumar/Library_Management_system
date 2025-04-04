# Library_Management_system

1. Create a new Java project folder (e.g., "Library_Management")

2. Copy and paste the given java code (Library_management.java) in created folder with the same name as class name and click on save

3. Setup MySQL Database 
---->
     * CREATE DATABASE Library_dbs;
     * USE Library_dbs;

   * Create a table for storing books:
     ---->    
     * CREATE TABLE books (
     * bookID VARCHAR(10) PRIMARY KEY,
     * title VARCHAR(255),
     * author VARCHAR(255),
     * publisher VARCHAR(255),
     * year INT,
     * isbn VARCHAR(20),
     * copies INT  );

  4. Now Add MySQL Connector to Your Project

     * Download MySQL Connector from - https://dev.mysql.com/downloads/connector/j/
     * Download the latest Platform Independent version (ZIP file).
     * Extract the ZIP file and Copy mysql-connector-j-9.2.0.jar file into your Library_Management project folder.
     * Make sure mysql-connector-j-9.2.0.jar file is there in the same folder as LibraryManagement.java

  5.  Compile & Run Your Java Program
      Open VS Code Terminal and Run
         * Compile Your Java Code : java -cp ".;mysql-connector-j-9.2.0.jar" Library_management.java
         * Run Your Java Code     : java -cp ".;mysql-connector-j-9.2.0.jar" Library_management
         * if you use command prompt open your terminal in project folder then compile and run the program <br>

         OUTPUT - 


      <img src="https://github.com/user-attachments/assets/3dde45ab-5a2d-40fa-83f2-3b48220633d2" width="400" height="250"  />  
  
      <img src="https://github.com/user-attachments/assets/002bf0f8-b299-4742-ba11-fd8116557624" width="400" height="250"  />
      
      <img src="https://github.com/user-attachments/assets/5ebb64f7-ca9a-4772-bddd-214e1876cc44" width="400" height="250"  /> 
    
      <img src="https://github.com/user-attachments/assets/c20733e1-f077-4aaa-b8d4-ab7f59934583" width="400" height="250"  />
     
   




           



  





