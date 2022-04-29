package com.freefood.project.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freefood.project.config.TokenProvider;
import com.freefood.project.dto.UserAuthDto;
import com.freefood.project.dto.UserDto;
import com.freefood.project.model.AuthToken;
import com.freefood.project.model.LoginUser;
import com.freefood.project.model.User;
import com.freefood.project.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenProvider jwtTokenUtil;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping("/all")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<UserDto>> getAll() {
		List<UserDto> result = null;
		try {
			result = this.userService.findAll().stream().map(u -> modelMapper.map(u, UserDto.class)).collect(Collectors.toList());
			
			if(result.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(result, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/findId")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<UserDto> getFindById(@RequestParam Long idUser) {
		UserDto resultDto = null;
		try {
			resultDto = modelMapper.map(this.userService.findById(idUser), UserDto.class);
			
			if(resultDto != null) {
				return new ResponseEntity<>(resultDto, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>(resultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/updateUser")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto user) {
		//TODO
		/*
		 * Implementar algo que faça o usuário atualizar apenas ele próprio
		 * */
		UserDto resultDto = null;
		try {
			User userParam = modelMapper.map(user, User.class);
			resultDto = modelMapper.map(this.userService.updateUser(userParam), UserDto.class);
			return new ResponseEntity<>(resultDto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(resultDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/deleteUser/{idUser}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<UserDto> deleteUser(@PathVariable("idUser") long idUser) {
		try {
			this.userService.deleteUser(idUser);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/authenticate")
	public ResponseEntity<AuthToken> generateToken(@RequestBody LoginUser loginUser) throws AuthenticationException {

		//Token
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        
        //User Info
        User u = this.userService.findOne(loginUser.getUsername());
        UserAuthDto uad = new UserAuthDto(	u.getUsername(), 
        									u.getEmail(), 
        									authentication.getAuthorities().stream().map(String::valueOf).collect(Collectors.toList()));
        
        return ResponseEntity.ok(new AuthToken(token, uad));
    }
	
	@PostMapping("/register")
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto user){
		UserDto result = null;
		try {
			User userParam = modelMapper.map(user, User.class);
			result = modelMapper.map(userService.save(userParam), UserDto.class);
			
			if(result != null) {
				return new ResponseEntity<>(result, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/adminping")
    public String adminPing(){
        return "Only Admins Can Read This";
    }
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/userping")
    public String userPing(){
        return "Any User Can Read This";
    }
	
}
