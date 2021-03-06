## To start mysql container

```
docker-compose up -d
```

## To run the project

```
mvn clean
./mvnw spring-boot:run
```

#### To Run test

```
mvn clean test
```

## Check the application's status

```
http://localhost:9696/api/v1/status
```

## All API

```
1. GET:  http://localhost:9696/api/v1/status
2. POST: http://localhost:9696/api/v1/products
body:
{
	"name": "Red Shirt",
	"description": "Red hugo boss shirt",
	"brand": "Hugo Boss",
	"tags": [
		"red", "shirt", "slim fit"
	],
	"category": "apparel"
}
3. GET: http://localhost:9696/api/v1/products/search?category=AppareL&page=0&size=10
```

## To check database entries

```
1. Run command below from terminal
    docker exec -it pms-mysql /bin/sh
2. once we see container prompt, Run the command given below
    mysql -u centric -pcentric
3. choose database
    use products;
4. list tables
    show tables;
```

### Running Single test using maven (https://maven.apache.org/surefire/maven-surefire-plugin/examples/single-test.html)
```
mvn -Dtest=ProductServiceTest test
```
### Troubleshoot error
```
1. Could not find or load main class org.apache.maven.wrapper.MavenWrapperMain
  and reason is folder: .mvn missing
  how to create: 
  mvn -N io.takari:maven:wrapper
```






