package com.lojavirtual.security;

import com.lojavirtual.modelos.Funcionario;
import com.lojavirtual.repositorios.FuncionarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

//@Service
//@Transactional
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    @Autowired
//    private FuncionarioRepositorio funcionarioRepositorio;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        Funcionario funcionario = funcionarioRepositorio.findByEmail(username);
//
//        return new User(funcionario.getUsername(), funcionario.getPassword(),true, true, true,true,funcionario.getAuthorities());
//    }
//
//
//}
