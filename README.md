# Hopsworks Data Connector
Java client for communication with the Hopsworks API

## Config File for Hopsworks API
Configuration file for the Hopsworks API can be found here
[confg.yml](config.yml)

## JFlow File Upload
JFlow simulates the behaviour of <a href="https://github.com/flowjs/">flow.js </a>client to communicate with a file-upload API.




# Build with Maven
```
  mvn install
```

# Testing
Under development. Basic tests for the Hopsworks API are already included.

## Testing Flow.js File Upload

flow.js provides some sample backend implementations, which can be used additional
for testing the file upload.

### Download Node.js Backend
<a href="https://github.com/flowjs/flow.js/tree/master/samples/Node.js">Node.js Backend </a>


#### Install Node.js 

```
  npm install
```

#### Run Node.js Backend

```
  node app.js
```

#### Run Tests

```
  mvn test
```


## Testing Hopsworks API
Tests are using the API defined in [confg.yml](config.yml)



# Dev
Hopsworks Data Connector is currently under development.


