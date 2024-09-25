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

    @Deprecated
    public JwtToken getTokenAuthenticated(String username) {
        Users user = usuarioService.buscarPorNome(username); // Busca o usuário pelo nome de usuário
    if (user == null) {
        throw new UsernameNotFoundException("User not found with username: " + username);
    }

    Users.Role role = user.getRole(); // Obtém a função do usuário
    Long userId = user.getId(); // Obtém o ID do usuário
    String email = user.getEmail(); // Obtém o email do usuário

    return JwtUtils.createToken(username, userId, role.name().substring("ROLE_".length()), email);
}
}
