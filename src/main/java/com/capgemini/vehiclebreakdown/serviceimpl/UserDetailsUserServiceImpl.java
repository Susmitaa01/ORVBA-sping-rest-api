package com.capgemini.vehiclebreakdown.serviceimpl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.capgemini.vehiclebreakdown.model.User;
import com.capgemini.vehiclebreakdown.repository.UserRepository;
import com.capgemini.vehiclebreakdown.service.UserDetailsUserService;

@Service
public class UserDetailsUserServiceImpl implements UserDetailsUserService{
		
	@Autowired
	private UserRepository userRepository;

    public UserDetails findUserByUsername(String username) {
        User user = userRepository.findByUserName(username).orElseThrow(()->(new UsernameNotFoundException("")));
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getUserPassword(),
                getAuthorities(user)
        );
        return userDetails;
    }

    private Set getAuthorities(User user) {
        System.out.println("[+] [JwtService] [getAuthorities]: inside getAuthorities");
        Set authorities = new HashSet();
        authorities.add(new SimpleGrantedAuthority(""+user.getRole()));
        System.out.println("[+] [JwtService] [getAuthorities]: getAuthorities() ended");
        return authorities;
    }
}
