# java_spring-boot_rest_service

This is a free to reuse - basic(CRUD) rest service using "java, spring boot, spring web, spring-data-jpa and h2 database" with maven. 
Please also have a look at the license file.

If you find it is useful for you and the community and more end to end applications should be developed, 
please leave a star on the repo.

I am an independent developer, if you want to support my work/time
consider hitting that sponsorship button on the right!
or
if you feel like, just buy me a coffee!

Feedback welcome!

## How to run it?

**Prerequisites**

- git
- jdk 17
- apache-maven-3.9.8 

**Run following commands in your terminal**

-  cd path_where_you_wantto_keep_the_project/
-  git clone https://github.com/tunnels4u/spring-rest-service.git
-  cd spring-rest-service
-  mvn compile
-  mvn exec:java

Now, you should be able to access the rest service at "http://localhost:8080/crudapi/welcome"

If you would like, Use it in combination with [java app front end](https://github.com/tunnels4u/javapp.git)

**End Points**

- /welcome [GET mapping]
- /create-employee [POST mapping]
- /get-employees [GET mapping]
- /delete-employee/{id} [DELETE mapping]
- /update-employee [PUT mapping]

## How to stop it?

Execute on your terminal,
- Ctrl + C

Now, you won't be able to access the rest service at "http://localhost:8080/crudapi/welcome"



