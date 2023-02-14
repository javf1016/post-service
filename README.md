# post-service
Post service eureka


EndPoints

CrearPost
* http://localhost:9002/posts/createpost
* Body: {
    "userId": 551,
    "title": "Título del post nuevo1",
    "body": "Cuerpo del post nuevo1"
}

Visitar Post (Gnera un numero aleatorio y va a ese post)
* http://localhost:9002/posts/visit

Listar Post por Usuario
* http://localhost:9002/posts/listbyuser/551?page=0&size=10
* Al buscar por Usuario se agrega restTemplate add findUserById para que consuma http://USER-SERVICE/user/{userId}
* Usa CircuitBreaker cuando USER-SERVICE se encuentra abajo
