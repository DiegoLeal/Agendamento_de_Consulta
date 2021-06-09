package com.agendamentodeconsulta.service;

import com.agendamentodeconsulta.model.Perfil;
import com.agendamentodeconsulta.model.Usuario;
import com.agendamentodeconsulta.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository repository;

    private final PasswordEncoder encoder;

    @Transactional(readOnly = true)
    public Usuario buscarPorEmail(String email) {

        return repository.findByEmail(email);
    }

    @Override @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = buscarPorEmail(username);
        return new User(
                usuario.getEmail(),
                usuario.getSenha(),
                AuthorityUtils.createAuthorityList(getAtuthorities(usuario.getPerfis()))
        );
    }

    private String[] getAtuthorities(List<Perfil> perfis) {
        String[] authorities = new String[perfis.size()];
        for (int i = 0; i < perfis.size(); i++) {
            authorities[i] = perfis.get(i).getDesc();
        }
        return authorities;
    }

    @Transactional(readOnly = false)
    public Usuario adicionarUsuario(Usuario usuario) {

        String encodedPassword = encoder.encode(usuario.getSenha());
        usuario.setSenha(encodedPassword);

        return repository.save(usuario);
    }
}
