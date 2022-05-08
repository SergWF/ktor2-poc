#! /bin/bash

/opt/keycloak/bin/kc.sh import --file $KEYCLOAK_IMPORT
/opt/keycloak/bin/kc.sh start-dev --http-relative-path /auth
