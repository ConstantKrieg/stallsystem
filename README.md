# Stall System
[![Deployment](https://github.com/ConstantKrieg/stallsystem/actions/workflows/deploy.yml/badge.svg?event=deployment_status)](https://github.com/ConstantKrieg/stallsystem/actions/workflows/deploy.yml)

This is a web application built with Micronaut and Kotlin. It is intended for private use. It fetches information from multiple sources, assembles the data into a single structure and provides endpoints for the reshaped data. 

Since some of the API licenses do not allow for a  complete application to be published for public use, all the endpoints are behind an authentication layer. Additionally, the software assumes that a database with specific schema is in place. 