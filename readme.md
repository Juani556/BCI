# User Microservice

## Endpoints

### POST /sign-up

Recibe los datos del usuario por el body, lo da de alta, genera un token y lo devuelve.

### POST /login

Recibe email y contraseña por body en caso de no tener token válido. Si tiene token solo es  
necesario mandar el email por el body y el token por el header "token".  
Devuelve los datos del usuario y genera un token nuevo.