# GITHUB ISSUE BROWSER
---
---
The **Github Issue Browser (GIB)** is an application that helps users see the issue in the GitHub repository.
## Documentation
---
To obtain Rest API - https://github-issue-browser.herokuapp.com/docs/index.html

## Getting Started
---
To start a project in Development mode, you should to take a few steps.
* ###  Step 1: Clone project from GitHub
    Use one of two methods.
    - First:
        1. Click "Clone or download" button
        2. Download ZIP archive
        3. Unzip archive to folder
    
    - Second:
        1. Install Git on the computer
        2. Open the folder via console
        3. Execute console command
    ```
    $ git clone https://github.com/VadymPolyanski/github-issue-browser.git
    ```

* ###  Step 2: Open with IDE
    
    Your IDE should work with:  
    - [Gradle](https://gradle.org/)
    - [JDK, JRE](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
    
* ###  Step 3: Build a project
    In order to build a project, you have to execute the following command in the console:
    ```
    $ ./gradlew stage
    ```

    This command runs: `clean -> test -> asciidoctor -> build`

 ## Demo
---
You can view the GIB in action by click the button:
[**`GIB demo`**](https://github-issue-browser.herokuapp.com/)

## Built With
---
**_Stack of technologies:_**
- **Spring boot** - building production-ready Spring application;
- **GitHub API** - getting informations from GitHub;
- **Lombok** - annotation processing;
- **Asciidoctor** - creating docs from tests;
- **Angular4** - front-end development;
- **Bootstrap** - for CSS Grids and Flexbox;
- **Gradle** - dependency Management.
