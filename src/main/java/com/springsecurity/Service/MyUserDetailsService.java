package com.springsecurity.Service;

import com.springsecurity.Entity.Role;
import com.springsecurity.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user =this.userService.findUserByUserName(userName);
        List<GrantedAuthority> authorities=getUserAuthority(user.getRoles());
        return new org.springframework.security.core.userdetails.
                User(user.getUserName(),user.getPassword(),user.getActive(),
                true,true,true,
                authorities);
    }

    //return list of authorities that user have
    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles){
        Set<GrantedAuthority> roles=new HashSet<>();
        for (Role role:userRoles){
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        }

        //convert set of roles into list and return
        List<GrantedAuthority> grantedAuthorities=new ArrayList<>(roles);
        return grantedAuthorities;
    }

}
