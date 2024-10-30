package com.vts.ims.login;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

//@Service
public class LoginDetailsServiceImpl implements UserDetailsService{
	
	
	
	
	@Autowired
    private LoginRepository loginRepository;

    @Override
    @Transactional(readOnly = false)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
    	Login login = loginRepository.findByUsername(username);
        if(login != null && login.getIsActive()==1) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : login.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
       
     
        return new org.springframework.security.core.userdetails.User(login.getUsername(), login.getPassword(), grantedAuthorities);
    }
        else {
        	   throw new UsernameNotFoundException("username not found");
        }
    }
}
