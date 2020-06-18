package tk.slaaavyn.soft.industry.banking.dto.auth;


import tk.slaaavyn.soft.industry.banking.dto.user.UserResponseDto;

import java.util.Date;

public class AuthResponseDto {
    private UserResponseDto user;
    private String token;
    private Date tokenExpired;

    public static AuthResponseDto toDto(UserResponseDto user, String token, Date tokenExpired) {
        AuthResponseDto responseDto = new AuthResponseDto();
        responseDto.setUser(user);
        responseDto.setToken(token);
        responseDto.setTokenExpired(tokenExpired);

        return responseDto;
    }

    public UserResponseDto getUser() {
        return user;
    }

    public void setUser(UserResponseDto user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getTokenExpired() {
        return tokenExpired;
    }

    public void setTokenExpired(Date tokenExpired) {
        this.tokenExpired = tokenExpired;
    }

    @Override
    public String toString() {
        return "AuthResponseDto{" +
                "user=" + user +
                ", token='" + token + '\'' +
                ", tokenExpired=" + tokenExpired +
                '}';
    }
}