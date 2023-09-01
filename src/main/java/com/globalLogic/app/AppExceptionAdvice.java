package com.globalLogic.app;

import com.globalLogic.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AppExceptionAdvice {

    @ExceptionHandler(value = UserExistException.class)
    public ResponseEntity<Map<String,String>> handlerUserExistException(UserExistException userExistException){

        Map<String,String> map = new HashMap<>();
        map.put("mensaje","Usuario registrado");
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidEmailException.class)
    public  ResponseEntity<Map<String,String>> handlerInvalidEmailException(){

        Map<String,String> map = new HashMap<>();
        map.put("mensaje","Formato de correo incorrecto");
        return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidFormatException.class)
    public ResponseEntity<Map<String,String>> handlerInvalidFormatException(){

        Map<String,String> map = new HashMap<>();
        map.put("mensaje","Formato de password incorrecto");
        return new ResponseEntity<>(map,HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Map<String,String>> handlerUserNotFoundException(){

        Map<String,String> map = new HashMap<>();
        map.put("mensaje","Usuario no encontrado");
        return new ResponseEntity<>(map,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UserIsNullExeption.class)
    public ResponseEntity<Map<String,String>> handlerUserIsNullExeption(){

        Map<String,String> map = new HashMap<>();
        map.put("mensaje","Usuario es Nulo");
        return new ResponseEntity<>(map,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = PasswordNotMatchException.class)
    public ResponseEntity<Map<String,String>> handlerPasswordNotMatchException(){

        Map<String,String> map = new HashMap<>();
        map.put("mensaje","Password incorrecto");
        return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = PhoneException.class)
    public ResponseEntity<Map<String,String>> handlerPhoneException(){

        Map<String,String> map = new HashMap<>();
        map.put("mensaje","No se pudieron procesar los teléfonos");
        return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
    }

}
