compile:
	gradle build -x test

jar: compile
	gradle bootJar

docker-build: jar
	docker build -t starbucks-api .
	docker images

docker-run: docker-build
	docker run --network starbucks -e "MYSQL_HOST=mysql" --name starbucks-api -td -p 8080:8080 starbucks-api	
	docker ps

sql:
	docker run -d --network starbucks --name mysql -td -p 3306:3306 -e MYSQL_ROOT_PASSWORD=cmpe172 mysql:8.0
