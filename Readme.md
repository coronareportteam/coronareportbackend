# Status
 This is a project in development. Therefore, currently it can only be used for dev purposes. Passwords etc. are used only in that development context. The setting is therefore NOT secure for production.

# Setup
Initial DB setup
* run docker-compose up
* connect to the database container docker exec -it <CONTAINER_ID> bash
* su - postgres
* createuser --interactive --pwpromt
    * rolename : corona-report-app
	* password: corona
	* superuser: yes
* createdb -O corona-report-app corona-report
