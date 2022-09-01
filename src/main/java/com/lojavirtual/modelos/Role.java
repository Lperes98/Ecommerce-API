package com.lojavirtual.modelos;

import com.lojavirtual.enums.RoleNome;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;


//@Entity
//@Data
//@Table(name = "roles")
//public class Role implements GrantedAuthority, Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false, unique = true)
//    private RoleNome roleNome;
//
//
//
//    @Override
//    public String getAuthority() {
//        return this.roleNome.toString();
//    }
//
//
//
//
//}
