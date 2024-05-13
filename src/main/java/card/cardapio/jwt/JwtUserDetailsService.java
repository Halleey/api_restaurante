package card.cardapio.jwt;

import card.cardapio.entitie.Users;
import card.cardapio.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService usuarioService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users usuario = usuarioService.buscarPorNome(username);
        return new JwtUserDetails(usuario);
    }

    public JwtToken getTokenAuthenticated(String username) {
        Users.Role role = usuarioService.buscarPorRoleUser(username);
        Long userId = usuarioService.buscarPorNome(username).getId();
        return JwtUtils.createToken(username, userId, role.name().substring("ROLE_".length()));
    }
}
