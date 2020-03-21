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
