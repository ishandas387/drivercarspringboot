
#Notes
Car crud operation is defined in CarController.
Manufacturer is a separate entity with one to one mapping to car.
No CRUD for manufacturer is added. However to test, view all manufacturers endpoint is available in car-controller.
DriverController has been extended to select/deselect car. 
Search functionality has been added to DriverController.
Search is flexible to return all non deleted drivers if no parameters are passed.
Search parameters can be driver attributes - username, online status and any combination of car attributes- classification, colour, lisence , engine ,seat count, rating

Security has been added to secure car and driver end points
All GET endpoints are secured by user- user and password - pass
All other HttpMethod endpoints are secured by user- admin and password - admin123
The security is enabled through inmemory authentication which clearly shows the user name and password with role for demo.
h2-console has been permitted to all.
