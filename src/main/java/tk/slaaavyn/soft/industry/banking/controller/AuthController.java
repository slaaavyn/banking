package tk.slaaavyn.soft.industry.banking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tk.slaaavyn.soft.industry.banking.config.EndpointConstants;
import tk.slaaavyn.soft.industry.banking.dto.auth.AuthRequestDto;
import tk.slaaavyn.soft.industry.banking.dto.auth.AuthResponseDto;
import tk.slaaavyn.soft.industry.banking.dto.user.UpdateUserPasswordDto;
import tk.slaaavyn.soft.industry.banking.dto.user.UserResponseDto;
import tk.slaaavyn.soft.industry.banking.model.Role;
import tk.slaaavyn.soft.industry.banking.model.User;
import tk.slaaavyn.soft.industry.banking.security.SecurityConstants;
import tk.slaaavyn.soft.industry.banking.security.jwt.JwtTokenProvider;
import tk.slaaavyn.soft.industry.banking.security.jwt.JwtUser;
import tk.slaaavyn.soft.industry.banking.service.UserService;

import java.util.Collections;
import java.util.Date;

@RestController
@RequestMapping(value = EndpointConstants.AUTH_ENDPOINT)
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(UserService userService, AuthenticationManager authenticationManager,
                          JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping
    protected ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto requestDto) {
        if(requestDto == null || requestDto.getEmail() == null || requestDto.getPassword() == null) {
            return ResponseEntity.badRequest().build();
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword()));

        User user = userService.getUserByEmail(requestDto.getEmail());

        return ResponseEntity.ok(generateTokenResponse(user));
    }

    @PutMapping("/update-password/{id}")
    protected ResponseEntity<Object> updatePassword(@PathVariable(name = "id") Long id,
                                                 @RequestBody UpdateUserPasswordDto passwordDto) {

        JwtUser jwtUser = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if (JwtUser.userHasAuthority(jwtUser.getAuthorities(), Role.ROLE_USER.name()) && !jwtUser.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }

        boolean isSuccess = userService.updatePassword(id, passwordDto);

        if (!isSuccess) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    private AuthResponseDto generateTokenResponse(User user) {
        Date tokenExpired = new Date(new Date().getTime() + 86400000);

        String token = SecurityConstants.TOKEN_PREFIX +
                jwtTokenProvider.createToken(user.getEmail(), Collections.singletonList(user.getRole().name()));

        return AuthResponseDto.toDto(UserResponseDto.toDto(user), token, tokenExpired);
    }
}
