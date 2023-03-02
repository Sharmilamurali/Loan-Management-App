package com.cts.loan.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cts.loan.entity.UserEntity;
import com.cts.loan.repository.UserRepository;

@Service

public class AuthService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<UserEntity> custuser = userRepository.findByUsername(username);
		return custuser.map(UserPrincipal::new)
				.orElseThrow(() -> new UsernameNotFoundException(username + "Not Found"));
		// return new
		// UserPrincipal(custuser.getUsername(),custuser.getUserpassword(),authorities);
	}
}
//		List<GrantedAuthority> authorities=buildUserAuthority(custuser.getRoles());
//		return custuser;
// RolesEntity role=custuser.getRoles();
// return new
// UserPrincipal(custuser.getUsername(),custuser.getUserpassword(),role.getRoleName());

//	private UserEntity buildUserAuthentication(custuser,List<GrantedAuthority> authorities) {
//		 return new UserEntity(custuser.getUsername(), custuser.getUserpassword(),
//		            true, true, true, true, authorities);
//	}
//	
//	private List<GrantedAuthority> buildUserAuthority(Set<Roles> userRoles) {
//
//        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
//
//        // add user's authorities
//        for (RolesEntity userRole : userRoles) {
//            setAuths.add(new SimpleGrantedAuthority(userRole.getRoleName()));
//        }
//
//        List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);
//
//        return Result;
//    }
//
//}
