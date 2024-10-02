#!/bin/bash

curl -X 'POST' \
   'https://demoqa.com/Account/v1/GenerateToken' \
   -H 'accept: application/json' \
   -H 'Content-Type: application/json' \
   -d '{
   "userName": "xavi-test2",
   "password": "Secret1!"
 }'