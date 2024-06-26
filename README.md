# Technical test
# Rick and Morty test

Este proyecto es una implementación de un sistema que interactúa con la API de Rick and Morty para obtener información sobre personajes y sus ubicaciones.
Está desarrollado usando Spring Boot y sigue una arquitectura hexagonal.

## Requisitos

- Java 11 o superior
- Maven 3.6 o superior

## Instalación

1. Clona el repositorio:
    ```bash
    git clone https://github.com/iLeoMonsalve/rick-and-morty-test.git
    ```
2. Navega al directorio del proyecto:
    ```bash
    cd rick-and-morty-test
    ```
3. Compila el proyecto usando Maven:
    ```bash
    mvn clean install
    ```

## Ejecución

Para ejecutar la aplicación localmente, puedes usar el siguiente comando:
```bash
mvn spring-boot:run
```
- La aplicacion va a correr en la siguiente url 'http://localhost:8080'
- Para obtener la informacion de algun personaje tienes que hacer un metodo GET a la siguiente URL '/api/v1/character/{id}'
