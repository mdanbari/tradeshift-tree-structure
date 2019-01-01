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
#Why Relational Databases No Longer Measure Up:

Ironically, legacy relational database management systems (RDBMS) are poor at handling relationships between data points. Their tabular data models and rigid schemas make it difficult to add new or different kinds of connections

## Neo4j
According to my resarch and neo4j site :

*  Neo4j delivers the lightning-fast read and write performance you need
*  It is the only enterprise-strength graph database that combines native graph storage, scalable architecture optimized for speed
*  ACID compliance to ensure predictability of relationship-based queries.
*  Index-free adjacency shortens read time and gets even better as data complexity grows. 
*  Using Cypher, the world’s most powerful and productive graph query language.

## Spring Boot and Spring Data Neo4j
Spring Data Neo4j is core part of the Spring Data project which aims to provide convenient data access for NoSQL databases.
It uses Neo4j-OGM (ike Spring Data JPA uses JPA) under the hood and provides functionality known from the Spring Data world, like repositories, derived finders or auditing.
it offers advanced features to map annotated entity classes to the Neo4j Graph Database.
