# tradeshift-tree-structure

## What is the problem ?

We in Amazing Co need to model how our company is structured so we can do awesome stuff.

We have a root node (only one) and several children nodes,each one with its own children as well. It's a tree-based structure. 

Something like:

         root
        /    \
       a      b
       |
       c

We need two HTTP APIs that will serve the two basic operations:

1) Get all children nodes of a given node (the given node can be anyone in the tree structure).
2) Change the parent node of a given node (the given node can be anyone in the tree structure).
 
They need to answer quickly, even with tons of nodes. Also,we can't afford to lose this information, so some sort of persistence isrequired.

Each node should have the following info:

1) node identification
2) who is the parent node
3) who is the root node
4) the height of the node. In the above example,height(root) = 0 and height(a) == 1.

# My Solution

I have modeled the Co tree structure as a Graph. A Tree is just a restricted form of a Graph.
Trees have direction (parent / child relationships) and don't contain cycles. They fit with in the category of Directed Acyclic Graphs (or a DAG).
So Trees are DAGs with the restriction that a child can only have one parent.

## Why choosing Graph Database ?

One thing that is important to point out :
Graph databases work by storing the relationships along with the data.
Because related nodes are physically linked in the database, accessing those relationships is as immediate as accessing the data itself.
In other words, instead of calculating the relationship as relational databases must do, graph databases simply read the relationship from storage. Satisfying queries is a simple matter of walking, or “traversing,” the graph. 
Graph databases are much faster than relational databases for connected data. A consequence of this is that query latency in a graph database is proportional to how much of the graph you choose to explore in a query, and is not proportional to the amount of data stored

## Whay choosing Neo4j ?

According to my resarch and neo4j site :

*  Neo4j delivers the lightning-fast read and write performance you need
*  It is the only enterprise-strength graph database that combines native graph storage, scalable architecture optimized for speed
*  ACID compliance to ensure predictability of relationship-based queries.
*  Index-free adjacency shortens read time and gets even better as data complexity grows. 
*  Using Cypher, the world’s most powerful and productive graph query language.

## Spring Boot and Spring Data Neo4j features

Spring Data Neo4j is core part of the Spring Data project which aims to provide convenient data access for NoSQL databases.
It uses Neo4j-OGM (ike Spring Data JPA uses JPA) under the hood and provides functionality known from the Spring Data world, like repositories, derived finders or auditing.
it offers advanced features to map annotated entity classes to the Neo4j Graph Database.

## The Stack

* Application Type: Spring-Boot Java Web Application with embedded tomcat
* Web framework: Spring-Boot enabled Spring-WebMVC, Rest Controllers
* Persistence Access: Spring-Data-Neo4j 5.0.5
* Database: Neo4j-Server 3.5.1
* Build tools and Dependency managment: Maven
* Deployment : Docker Container

## Build and Deploy Process
### Step 0 (prerequisite)
install docker, docker-compose, apache maven
### Step 1 (Config Files)
### application.properties parameters for setting neo4j host server and username and password
```
spring.data.neo4j.uri=bolt://neo4j:7687
spring.data.neo4j.username=neo4j
spring.data.neo4j.password=123
```
### docket-compose.yml file for defining containers, exposing port
```
version: '2.0'
services:
  springboot:
    build: .   
    container_name: springboot
    ports:
      - 8085:8080
    links:
      - neo4j
  neo4j:
    image: neo4j:latest
    container_name: neo4j
    ports:
      - 7474:7474
      - 7687:7687
```     
#### Step2 (Build and create jar file)
```
clean package -X
```
#### Step3 (Deploy)
```
docker-compose up --build
```
if everything goes well you can see the following result aftre running docker ps and spring boot container linked to neo4j :
```
CONTAINER ID          NAMES              PORTS                                                      
6fdb6775e688          springboot 	 0.0.0.0:8085->8080/tcp                                     
a9d097b48676          neo4j              0.0.0.0:7474->7474/tcp, 7473/tcp, 0.0.0.0:7687->7687/tcp   

```
##Endpoints
```
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET  http://192.168.52.130:8085/populateDB?nodeNum=15
```

## Code Walkthrough

### Node and NodeInfo Data Models:
```java
@NodeEntity
public class Node {	
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	@Relationship(type = "child_rel", direction = Relationship.OUTGOING)
	private List<Node> children;
}
```

### Repository and queries:
```SQL
Node findByName(@Param("name") String title);

@Query("MATCH (p)-[r:child_rel]->(n) WHERE n.name = {name} RETURN p")
Collection<Node> getParent(@Param("name") String name);

@Query("MATCH (p)-[r:child_rel]->(n) WHERE p.name = {name} RETURN n")
Collection<Node> getChildren(@Param("name") String name);

@Query("MATCH ()-[r:child_rel]-(n) WHERE n.name = {name} DELETE r")
Collection<Node> deleteRelation(@Param("name") String name);
```
