package br.com.mv.APIHealth.service.impl;

import br.com.mv.APIHealth.domain.entity.Usuario;
import br.com.mv.APIHealth.domain.repository.UsuarioRepository;
import br.com.mv.APIHealth.exception.SenhaInvalidaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public UserDetails autenticar(Usuario usuario){
        UserDetails user = loadUserByUsername(usuario.getLogin());
        boolean senhaBatem = passwordEncoder.matches(usuario.getSenha(), user.getPassword());
        if (senhaBatem) {
            return user;
        }
        throw new SenhaInvalidaException();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found user"));

        String roles = null;
        if(usuario.getRoles().equals("NURSE")){
            roles = "NURSE";
        }
        if(usuario.getRoles().equals("ADMIN")){
            roles = "ADMIN";
        }
        if(usuario.getRoles().equals("PATIENT")){
            roles = "PATIENT";
        }
        if(usuario.getRoles().equals("DOCTOR")){
            roles = "DOCTOR";
        }

        return User
                .builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(roles)
                .build();

    }
}
