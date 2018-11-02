# ODM Decision Service Insight Solution Implementation.

The entities are:
* Application has a status and a name
* Application has one to many services
* A service has a status and a name.
* A user is uniquely identified by his email address.

When one of the service is degraded the application is degraded.

Events:
* service degraded: with unique service name
* service is back on line with unique service name
* user y is impacted by application x 
