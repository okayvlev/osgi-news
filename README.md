# News Statistics Services
## Overview
These are services gathering statistics using latest news from several media sources. 
This particular implementation shows top 10 most popular words in recent news headlines, 
but is easy to be extended to do more complicated things.

The services are made using Apache Felix implementation of OSGi framework.
The `NewsClient` module is the main bundle providing basic functionality for
other services of the system. It also supplies Gogo shell with `news:stats` command, 
which starts the process of statistics gathering. 

Child services can be dynamically supplied at runtime to expand the pool of news sources.

## Build
Each bundle can be assembled from its root directory with Maven using the following command:

`mvn clean install`

## Run Environment
Your environment should support `UTF-8` charset for all Unicode characters from headlines to be correctly displayed.