# Notebook-server

A simple notebook server that can execute pieces of code in an interpreter using Spring Boot and GraalVM.
# Pre-requisite
- *GraalVM must be installed in order to be able to build the project, follow the [Getting Started with GraalVM](https://www.graalvm.org/docs/getting-started/).*
- *You can use the Graal Updater to install language packs for Python, R, and Ruby.*
  ```
  gu install python  
  ```
 
- *Install latest version of Maven.*

# Usage
The Interpreter API is available via http POST method at:
/execute
## Interpreter request body
The /execute interpreter End-Point accepts JSON as request body. The json object have the following format
  ```
{
  "code": "string",
  "sessionId": "string"
}
  ```

Here is a small description of the request body fields:

- code: the code to be interpreted, it must have the format:

  ```
  %language code
  ```

where language is one of the supported languages, and code is the code to be interpreted. 

- sessionId: is the id of the session we are using. this field is used to differentiate between users and also to allow users to continue their interaction with the interpreter in the same execution context (example declare variable and reuse them). The sessionId is not mandatory in the first request and the API will provide you with a sessionId in case not specified but it must be used to remembre the previous declaration

## Interpreter response body
The /execute returns a json object as response. The response have the following format:
  ```
{
  "result": "string",
  "errors": "string",
  "sessionId": "string"
}
  ```
- *result: the output of the code interpretation*
- *errors: errors information of the code interpretation*
- *sessionId: the sessionId used during the interpretation for future usage*

## Server response codes
- **200** OK:

The interpreter API returns a 200 OK response code when the interpreter successfully execute the code.
The response will have the format described above and might containg erros in case of execution failure.

- **400** BAD_REQUEST: The APi might return BAD REQUEST as response code in the following cases :
    - The request doesnt follow the correct format
    - The  language is not supported
    - The interpreter takes a long time to finish

- **500** INTERNAL_SERVER_ERROR:

If this happens, it means that something went wrong with the server, just wait for a while and try again.


## Api Usage Example
Here are some example of requests and responses :

Example 1 simple js log

•	Request Body :
  ```
{
  "code": "%js console.log(1)", 
  "sessionId": "mySessionId"
}
  ```
•	Response Body:
  ```
{
  "result": "1\n",
  "errors": "", 
  "sessionId": "mySessionId"
}
  ```
Example 2: simple js variable reuse

•	request 1
  ```
{
  "code": "%js var a = 5", 
  "sessionId": "mySessionId"
}
  ```
•	response for request 1
  ```
{
  "result": "5\n ",
  "errors": "", 
  "sessionId": "mySessionId"
}
  ```
•	request 2 
  ```
{
  "code": "%js console.log(a)", 
  "sessionId": "mySessionId"
}
  ```
•	response for request 2
  ```
{
  "response": "5\n",
  "errors": "", 
  "sessionId": "mySessionId"
}
  ```
