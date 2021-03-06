package tk.slaaavyn.soft.industry.banking.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
import tk.slaaavyn.soft.industry.banking.model.User;
import tk.slaaavyn.soft.industry.banking.security.SecurityConstants;
import tk.slaaavyn.soft.industry.banking.security.jwt.JwtTokenProvider;
import tk.slaaavyn.soft.industry.banking.security.jwt.JwtUser;
import tk.slaaavyn.soft.industry.banking.service.UserService;

import javax.validation.Valid;
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
    @ApiOperation(value = "User authorization with email/password (token generation)")
    protected ResponseEntity<AuthResponseDto> login(@Valid @RequestBody AuthRequestDto requestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword()));

        User user = userService.getUserByEmail(requestDto.getEmail());

        return ResponseEntity.ok(generateTokenResponse(user));
    }

    @PutMapping("/update-password")
    @ApiOperation(value = "Update user password")
    @ApiImplicitParam(name = "Authorization", value = "token", required = true, dataType = "string", paramType = "header")
    protected ResponseEntity<Object> updatePassword(@Valid @RequestBody UpdateUserPasswordDto passwordDto) {
        JwtUser jwtUser = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        userService.updatePassword(jwtUser.getId(), passwordDto);

        return ResponseEntity.ok().build();
    }

    private AuthResponseDto generateTokenResponse(User user) {
        Date tokenExpired = new Date(new Date().getTime() + SecurityConstants.TOKEN_VALIDITY_TIME);

        String token = SecurityConstants.TOKEN_PREFIX +
                jwtTokenProvider.createToken(user.getEmail(), Collections.singletonList(user.getRole().name()));

        return AuthResponseDto.toDto(UserResponseDto.toDto(user), token, tokenExpired);
    }
}
