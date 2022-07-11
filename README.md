# restVehicleService

Challenge:
Containerized CRUD API that persists to a Mongo instance.
General Requirements:
Input can be in JSON and XML
80% test coverage

Language: Java 11+

Datastore: MongoDB

User Story:
As a Vehicles Fleet manager I want to record my vehicles information into the fleet
management system. Please provide a microservice with a CRUD API
* Add new vehicles
* Update existing vehicles
* Delete vehicles
* Fetch one or more vehicles.

I want to store the following details of the vehicle:
* Name
* VIN
* License Plate number
* Properties (any further information such as color, number of doors, number of wheels etc)

Note: The Properties of a vehicle does not have any fixed schema and your end-point
should be able to handle both json and xml as properties.

Deliverables:
Public github / gitlab / other git link with the project
Instructions to run