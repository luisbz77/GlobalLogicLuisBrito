

# ReadMe First
User Creation
# Packaging executable jar and war files
./gradlew build

# Build the Project Artifact
java -jar build/libs/GlobalLogicApplicationTests-0.0.1-SNAPSHOT.jar

# To clean Project, build and Rebuild
./gradlew clean
./gradlew bootRun
./gradlew --refresh-dependencies

# Pasos importantes para probar la aplicación
En postman:
1. tenemos que utilizar el siguiente endpoint: 
Método: POST 
URL: http://localhost:8080/sign-up para crear un usuario.
En el Body tener esta estructura en formato Json:
{
  "name": "Usuario Ejemplo",
  "email": "usuario@example.com",
  "password": "Abc1defg",
  "phones": [
    {
      "number": 123456789,
      "citycode": 1,
      "countrycode": "57"
    }
  ]
}

Respetando los requerimientos y reglas de negocio para crear dicho usuario y la generación del token
2. Para visualizar todos los usuarios tenemos el siguiente endpoint: GET http://localhost:8080/user
3. El endpoint para realizar el login y obtener la información del usuario podría quedar así:
Método: POST
URL: http://localhost:8080/login para logear
Body:
{
  "email": "usuario@example.com",
  "password": "Abc1defg"
}