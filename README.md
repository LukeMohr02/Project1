# Project1
Author: Luke Mohr

## Description
This is a custom ORM framwrokg that allows users to choose between programmatic persistence and file-based persistence.
Users can choose to configure entities either programmatically by persisting JPA-compliant objects or using XML configuration.
All SQL implementation is abstracted away from the user.

## Features
* Programmatic persistence of entities
    * POJO configuration
    * CRUD support
    * Automatic transaction management
* File-based persistence of entities
    * XML configuration
    * CRUD support
* Connection pooling
* 80% test coverage
* Custom exceptions to make user errors easier to diagnose
* Documentation for XML configuration (resources/Tags.txt)

## Technologies Used
* Java 8
* JUnit
* Maven
* PostgreSQL
* AWS RDS

## Setup and Usage
Database connection is configured in database.properties (for file-based persistence) and persistence.xml (for 
programmatic persistence). 
Everything in the user package can be changed by the user to suit their needs. Some examples are provided. Each 
subdirectory is set up as follows:
  - model - entity are defined here, annotated with JPA
  
###Programmatic persistence:
  - utility - UserDriver is where programmatic CRUD operations take place
    1. Instantiate ObjectMapping (om)
    2. Instantiate models
    3. Use om.\<CRUD operation>:
        - om.persistObject(entityPOJO)
        - om.findObject(entity.class, entityPOJO)
        - om.deleteObject(entity.class, entityPOJO)
    4. om.close()
    5. 
###File-based persistence:
  - json - SerializedObjects.txt is the output file for file-based database calls.
    
  - xml - XML documents inside of this directory will be parsed and converted into SQL. Example documents are provided.
    Proper syntax is layed out in the resources/Tags.txt file. Arrange XML and then run orm.utility.Driver to parse
    through and convert to SQL.

## License
https://github.com/LukeMohr02/Project1/blob/main/LICENSE.txt
