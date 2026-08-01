package registroProjetos.registroProjetos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErroResponse> handleNotFound(ResourceNotFoundException ex) {
        ErroResponse erro = new ErroResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();
        for (FieldError erro : ex.getBindingResult().getFieldErrors()) {
            erros.put(erro.getField(), erro.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(erros);
    }
    @ExceptionHandler(CredenciaisInvalidasException.class)
    public ResponseEntity<ErroResponse> handleCredenciaisInvalidas(CredenciaisInvalidasException ex) {
        ErroResponse erro = new ErroResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
    }
    @ExceptionHandler(RegraNegocioException.class)
public ResponseEntity<ErroResponse> handleRegraNegocio(RegraNegocioException ex) {
    ErroResponse erro = new ErroResponse(HttpStatus.CONFLICT.value(), ex.getMessage(), LocalDateTime.now());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
}

@ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
public ResponseEntity<ErroResponse> handleIntegridade(org.springframework.dao.DataIntegrityViolationException ex) {
    ErroResponse erro = new ErroResponse(
            HttpStatus.CONFLICT.value(),
            "Operação viola uma restrição de integridade dos dados (registro em uso ou duplicado)",
            LocalDateTime.now()
    );
    return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
}

@ExceptionHandler(Exception.class)
public ResponseEntity<ErroResponse> handleGenerico(Exception ex) {
    ErroResponse erro = new ErroResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Erro interno inesperado",
            LocalDateTime.now()
    );
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
}
    public record ErroResponse(int status, String mensagem, LocalDateTime timestamp) {}
}