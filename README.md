# devnet

[![<ORG_NAME>](https://circleci.com/gh/gauravghongde/devnet.svg?style=shield)](https://circleci.com/gh/gauravghongde/devnet)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/0971a962e6b74e19bf11f9740610cb9e)](https://app.codacy.com/manual/gauravghongde/devnet?utm_source=github.com&utm_medium=referral&utm_content=gauravghongde/devnet&utm_campaign=Badge_Grade_Dashboard)
[![Join the chat at https://gitter.im/devnet_chat/community](https://badges.gitter.im/devnet_chat/community.svg)](https://gitter.im/devnet_chat/community?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

## Branches & Environments

### devnet 
- Env: Prod
- Swagger: https://rstack-devnet.herokuapp.com/swagger-ui.html
- desc: Containes stable code (beta)

### devnet-pp
- Env: Pre-prod
- Swagger: https://devnet-pp.herokuapp.com/swagger-ui.html
- desc: Containes deployable code, unsatble (alpha) 

### master
- Env: No deployed env
- Swagger: N/A
- desc: Code going to be merged into devnet/devnet-pp has to be merged into master first.

## Requirements for local setup 
- Gradle > 4.0.1
- Java 8
- Mongo Client
