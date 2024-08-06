# Spring REST API And Spring Security (Authentication and Authorization)

## Configuration 

1. Clone the repository: `git clone https://github.com/yourusername/Techezy-Assessment`
2. Configure h2 Database: 
3. Navigate to the project folder: `AssignmentApplication.java` and Run as spring



## API Endpoints 

### Student and Subject Api 
#### 1. Register Student :
+ Endpoint : `http://localhost:8080/api/students`
+ Method : POST
+ Request Body :
```
{
    "firstName": "John",
    "lastName": "Doe",
    "address": "123 Main St",
    "role": "STUDENT",
    "subjectNames": ["Math", "Science"]
}
```
+ Response Body :
```
{
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "address": "123 Main St",
    "role": "STUDENT",
    "subjects": [
        {
            "id": 1,
            "subjectName": "Science"
        },
        {
            "id": 2,
            "subjectName": "Math"
        }
    ]
}
```
#### 2. Get List of All Students
+ Endpoint : `http://localhost:8080/api/students`
+ Method : GET
+ Response Body :
```
[
    {
        "id": 1,
        "firstName": "John",
        "lastName": "Doe",
        "address": "123 Main St",
        "role": "STUDENT",
        "subjects": [
            {
                "id": 1,
                "subjectName": "Science"
            },
            {
                "id": 2,
                "subjectName": "Math"
            }
        ]
    }
]
```

#### 3. Get List of All Subjects
+ Endpoint : `http://localhost:8080/api/subjects`
+ Method : GET
+ Response Body :
```
[
    {
        "id": 1,
        "subjectName": "Science"
    },
    {
        "id": 2,
        "subjectName": "Math"
    }
]
```
### Authentication and Authorization 
#### 1. Register User :
+ Endpoint : `http://localhost:8080/api/auth/register`
+ Method : POST
+ Request Body :
```
 {
  "email": "rkadu9359@gmail.com",
  "password": "password123",
  "role": "STUDENT"
 }
```
+ Response Body :
```
{
    "access_token": "YOUR_ACCESS_TOKEN",
    "refresh_token": "YOUR_REFRESH_TOKEN"
}
```
#### 2. Authenticate User
+ Endpoint : `http://localhost:8080/api/auth/authenticate`
+ Method : POST
+ Request Body :
```
{
  "email": "rkadu9359@gmail.com",
  "password": "password123"
}
```
+ Response Body :
```
{
    "access_token": "YOUR_ACCESS_TOKEN",
    "refresh_token": "YOUR_REFRESH_TOKEN"
}
```
#### 3. Accessing endpoint via Both Role (STUDENT , ADMIN) access_token
+ Endpoint : `http://localhost:8080/api/demo-student`
+ Method : GET
+ Authorization : Bearer Token
+ Response Body :
```
Hello from secured student endpoints
```
#### 4. Accessing endpoint via ADMIN Role access_token
+ Endpoint : `http://localhost:8080/api/demo-admin`
+ Method : GET
+ Authorization : Bearer Token
+ Response Body :
```
Hello from secured admin endpoints
```
