package registroProjetos.registroProjetos.security;

import registroProjetos.registroProjetos.exception.CredenciaisInvalidasException;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TentativaLoginService {

    private static final int MAX_TENTATIVAS = 5;
    private static final long JANELA_BLOQUEIO_MS = 5 * 60 * 1000;

    private final Map<String, Tentativas> registros = new ConcurrentHashMap<>();

    public void validarNaoBloqueado(String login) {
        Tentativas t = registros.get(login);
        if (t != null && t.bloqueadoAte != null && t.bloqueadoAte.isAfter(Instant.now())) {
            throw new CredenciaisInvalidasException("Muitas tentativas de login. Tente novamente em alguns minutos.");
        }
    }

    public void registrarFalha(String login) {
        Tentativas t = registros.computeIfAbsent(login, k -> new Tentativas());
        t.contador++;
        if (t.contador >= MAX_TENTATIVAS) {
            t.bloqueadoAte = Instant.now().plusMillis(JANELA_BLOQUEIO_MS);
        }
    }

    public void registrarSucesso(String login) {
        registros.remove(login);
    }

    private static class Tentativas {
        int contador = 0;
        Instant bloqueadoAte;
    }
}