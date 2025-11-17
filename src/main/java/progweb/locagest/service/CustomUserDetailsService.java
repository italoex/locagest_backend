package progweb.locagest.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import progweb.locagest.model.Usuario;
import progweb.locagest.repository.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        return org.springframework.security.core.userdetails.User
                .withUsername(usuario.getEmail())
                .password(usuario.getSenha())
                .authorities("USER") 
                .build();
    }
}