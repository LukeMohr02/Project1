# Project 1 - Custom Object Relational Mapping Framework

## Description

Your first project will be to create a custom object relational mapping (ORM) framework. This framework will allow for a simplified and SQL-free interaction with the relational data source. The requires of the project are purposefully vague, the intention is to allow for you to be creative in your implementation of this framework. There are many ways that this task can be approached, and you are encouraged to explore existing Java ORM implementations in order to get some inspiration. Some suggested features that your ORM can provide are:

1. provide developers the option of file-based and programmatic configuration of entities

2. Programmatic persistence of entities (basic CRUD support)

3. Basic transaction management (begin, commit, savepoint, rollback)

4. Connection pooling

5. Lightweight session creation

6. Session-based caching to minimize calls to the database
	- Make sure cache is not too big, not too small
	- Caching should be inside the framework itself

7. Multithreading support for executing queries

## Tech Stack
- [ ] Java 8
- [ ] JUnit
- [ ] Apache Maven
- [ ] PostGreSQL deployed on AWS RDS
- [ ] Git SCM (on GitHub)

## Init Instructions
- Create a new repository within this organization (naming convention: `orm_name_p1`; with `orm_name` being replaced by the name of your custom library)

## Presentation
- [ ] finalized version of library must be pushed to personal repository within this organization by the presentation date (March 26th, 2021)
- [ ] 10-15 minute live demonstration of the implemented features using a demo application to showcase the ORM's functionality

OTHER:
	70%+ unit testing (use JaCoCo)
	- March 26
	- User should interface with xml
	- Utilize any libraries (including collections)
	- Jackson library is helpful for xml
	- Developer should have all the power of SQL (serial ID, primary key, column names, etc.) but actual SQL implementation is abstracted
	- NOTE: this project does not require a database (it is only a framework), but use a database for testing
	- Requirements are intentionally vague, we should provide our own user stories
		- User logging framework?
	- Look at hibernate for inspiration

USER STORIES:
	As a developer, I can:
		- connect to databases
			- enter endpoint, username, password, and schema
		- perform user-friendly SQL operations
			- persist objects and retrieve them from the database
				- track which object properties should be persisted
		- NOT access the entire database (just what I specifically request)
