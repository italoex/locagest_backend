package progweb.locagest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.validation.Valid;

import progweb.locagest.model.Usuario;
import progweb.locagest.util.JwtUtil;
import progweb.locagest.repository.UsuarioRepository;
import progweb.locagest.controller.dto.LoginRequest;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@Valid @RequestBody Usuario usuario) {
        // checar duplicidade de email/cpf
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já cadastrado");
        }
        if (usuarioRepository.findByCpf(usuario.getCpf()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF já cadastrado");
        }
        Usuario saved = usuarioRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequest body) {
        String identifier = body.getIdentifier();
        String password = body.getPassword();
        Usuario usuario = usuarioRepository.findByEmail(identifier)
                .or(() -> usuarioRepository.findByCpf(identifier))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        if (passwordEncoder.matches(password, usuario.getSenha())) {
            String token = jwtUtil.generateToken(usuario.getEmail());
            return ResponseEntity.ok(Map.of("token", token));
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas");
        }
    }
}