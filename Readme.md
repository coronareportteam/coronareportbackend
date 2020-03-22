# epitrack - Epidemiemanagement für Gesundheitsbehörden

Epidemiemanagement für Gesundheitsbehörden durch Digitalisierung von Selbstauskünften bestätigter Quarantänefälle und Verdachtspersonen (inkl. Informationen zu Symptomen und Kontakten)

See our [intro video](https://www.youtube.com/watch?time_continue=120&v=z__mJRP8O0w&feature=emb_logo)!

## Status
 This is a project in development. It has been created during the [WirVsVirusHackathon](https://wirvsvirushackathon.org/).
  

## Development Setup
Initial DB setup
* run docker-compose up
* connect to the database container `docker exec -it <CONTAINER_ID> bash`
* `su - postgres`
* `createuser --interactive --pwprompt`
    * rolename : `corona-report-app`
	* password: `corona`
	* superuser: yes
* `createdb -O corona-report-app corona-report`