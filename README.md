# UserManagement
RESTful Webservice with IntegrationTest - for one of my coding interviews


This is multi-tier web application with Spring 4 and JPA-Hibernate. 

<b>Create User</b><br>
`curl -H "Content-Type: application/json" -X POST -d '{"userName":"xyz","email":"xyz"}' http://localhost:8080/api/users/`
<br>

<b>Update User</b><br>
`curl -H "Content-Type: application/json" -X PUT -d '{"userName":"userNameToUpdate","email":"emailToUpdate"}' http://localhost:8080/api/users/1`
<br>

<b>Get User</b><br>
`curl -H "Content-Type: application/json" -X GET -d http://localhost:8080/api/users/1`

<b>Delete User</b><br>
`curl -H "Content-Type: application/json" -X DELETE -d http://localhost:8080/api/users/1`
