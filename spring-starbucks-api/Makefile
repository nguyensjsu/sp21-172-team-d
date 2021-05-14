account = rustypedrosa
app = starbucks-api
network = kong-network
version-app = 4.0
version-docker = v4

all: clean

clean:
	gradle clean

compile:
	gradle build

test:
	gradle test

jar:
	gradle build -x test
	gradle bootJar

run: jar
	echo Starting Spring at:  http://localhost:8080
	java -jar build/libs/$(app)-$(version-app).jar

# Docker

docker-build: jar
	docker build -t $(app) .
	# docker images

docker-push:
	docker login
	docker build -t $(account)/$(app):$(version-docker) .
	docker push $(account)/$(app):$(version-docker)
	
docker-run-standalone: docker-build
	docker run --name $(app) --network $(network) -td -p 8080:8080 $(app)
	docker ps

docker-run: docker-build
	docker run --name $(app) --network $(network) -td $(app)
	docker ps

docker-run-kong:
	docker run -d --name kong \
    --network=kong-network \
    -e "KONG_DATABASE=off" \
    -e "KONG_PROXY_ACCESS_LOG=/dev/stdout" \
    -e "KONG_ADMIN_ACCESS_LOG=/dev/stdout" \
    -e "KONG_PROXY_ERROR_LOG=/dev/stderr" \
    -e "KONG_ADMIN_ERROR_LOG=/dev/stderr" \
    -e "KONG_ADMIN_LISTEN=0.0.0.0:8001, 0.0.0.0:8444 ssl" \
    -p 80:8000 \
    -p 443:8443 \
    -p 8001:8001 \
    -p 8444:8444 \
    kong:2.4.0
	docker ps

#	docker run -d --network gumball --name mysql -td -p 3306:3306 -e MYSQL_ROOT_PASSWORD=cmpe172 mysql:8.0
docker-run-mysql:
	docker run --name starbucks-mysql --network $(network) -td -e MYSQL_ROOT_PASSWORD=cmpe172 mysql:8.0
	docker ps

docker-run-all: docker-run docker-run-kong

docker-clean:
	-docker stop $(app)
	-docker rm $(app)
	-docker rmi $(app)

docker-clean-kong:
	-docker stop kong
	-docker rm kong
	-docker rmi kong

docker-clean-all: docker-clean docker-clean-kong

docker-shell:
	docker exec -it $(app) bash

# Kong

kong-load-config:
	http :8001/config config=@../resources/docker-kong.yaml
	docker exec -it spring-starbucks-api_kong_1 kong reload

# Compose

# network-ls:
# 	docker network ls

network-create:
	docker network create --driver bridge starbucks-api-network

network-prune:
	docker network prune

compose-up:
	docker-compose up --scale starbucks-api=1 -d

compose-down:
	docker-compose down

# lb-stats:
# 	echo "user = admin | password = admin"
# 	open http://localhost:1936

# lb-test:
# 	open http://localhost


# # Pod

# pod-run:
# 	kubectl apply -f pod.yaml

# pod-list:
# 	kubectl get pods

# pod-desc:
# 	kubectl describe pods spring-gumball

# pod-delete:
# 	kubectl delete -f pod.yaml

# pod-shell:
# 	kubectl exec -it spring-gumball -- /bin/bash

# pod-logs:
# 	kubectl logs -f spring-gumball

# # Deployment

# deployment-create:
# 	kubectl create -f deployment.yaml --save-config

# deployment-get:
# 	kubectl get deployments

# deployment-get-pods:
# 	kubectl get pods -l name=spring-gumball

# deployment-pod-shell:
# 	kubectl exec -it $(pod) -- /bin/bash

# deployment-upgrade:
# 	kubectl apply  -f deployment.yaml

# deployment-delete:
# 	kubectl delete deployment spring-gumball-deployment

# # Service

# service-create:
# 	kubectl create -f service.yaml

# service-get:
# 	kubectl get services

# service-get-ip:
# 	kubectl get service spring-gumball -o wide

# service-delete:
# 	kubectl delete service spring-gumball

# # Ingress

# ingress-apply:
# 	kubectl apply -f ingress.yaml

# ingress-ip:
# 	kubectl get ingress spring-gumball-ingress

## Kong

# dbless