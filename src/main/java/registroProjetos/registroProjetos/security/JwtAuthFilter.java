package registroProjetos.registroProjetos.security;

import registroProjetos.registroProjetos.repository.ResponsavelRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final ResponsavelRepository responsavelRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            if (jwtService.tokenValido(token)) {
                String login = jwtService.extrairClaims(token).getSubject();

                // Cargo e status "ativo" vêm do banco, não do token -- se o usuário
                // foi excluído ou teve o cargo alterado, isso reflete na próxima
                // requisição, mesmo com o token ainda dentro da validade.
                responsavelRepository.findByLoginAndAtivoTrue(login).ifPresent(responsavel -> {
                    var authToken = new UsernamePasswordAuthenticationToken(
                            login, null,
                            List.of(new SimpleGrantedAuthority("ROLE_" + responsavel.getCargo().name()))
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                });
            }
        }

        filterChain.doFilter(request, response);
    }
}