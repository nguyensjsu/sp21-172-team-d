# Team D Journal (Team Project)

## Development - local docker
Note: you need to `cd spring-starbucks-api` to use the make commands below
### Create the docker network (only needed once)
- Network name is `starbucks-api-network`
```
make create-network
```

### Create/launch mysql and starbucks-api containers
- When MySQL is launched, it also creates the database, root password, and api user/password
```
make docker-run-all
```

### Create/Launch mysql container only
- When MySQL is launched, it also creates the database, root password, and api user/password
```
make docker-run-mysql
```

### Launch api container only
- Will crashloop if mysql isn't running
```
make docker-run
```

###  Delete containers - run these as needed after making a change / before re-launching the commands above
- Clean/delete all containers
```
make docker-clean-all
```
- Clean/delete starbucks-api container
```
make docker-clean
```
- Clean/delete mysql container
```
make docker-clean-mysql
```
- Clean/delete kong container  (not currently used)
```
make docker-clean-kong
```

## Deployment to GKE
TBD
- ~~Copy mysql-deployment.yaml to cloud shell~~
- ~~kubectl create -f mysql-deployment.yaml~~
kubectl create -f service-api.yaml
kubectl apply -f ingress-api.yaml


## Team Journals should include
- Overall Architecture Diagram of your Cloud Deployment
- A section for each of the following discussion the features implemented
  - Cashier's App
    - What features were implemented?
      - Cashier can select the type, size of the drink but also select if the customer wants to add a type of milk and/or add any toppings like whipped cream, drizzle, etc.
    - UI was based on the needs of a cashier when receiving an order and customize it based on what the customer wants.
    - Spring Boot was used to develop the cashier's app
  - Backoffice Help Desk App
    - What features were implemented?
  - Online Store
    - What features were implemented?
  - REST API 
    - Final design with sample request/response
  - Integrations
    - Which integrations were selected?
  - Cloud Deployments
    - Design Notes on GitHub an Architecture Diagram of the overall Deployment.
    - How does your Team's System Scale?  Can it handle > 1 Million Mobile Devices?
- Technical Requirements
  - Discussion with screenshot evidence of how each technical requirement is meet.
