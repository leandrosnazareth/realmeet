package br.com.sw2you.realmeet.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class ResponseEntityUtils {

    public ResponseEntityUtils() {
    }


    //return um responseentity do tipo ok, método utilitário
    public static <T> ResponseEntity<T> ok(T body) {
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    //return um responseentity do tipo created, método utilitário
    public static <T> ResponseEntity<T> created(T body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    public static <T> ResponseEntity<T> notFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public static <T> ResponseEntity<T> noContent(Void aVoid) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
