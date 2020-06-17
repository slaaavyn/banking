package tk.slaaavyn.soft.industry.banking.security.jwt;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import tk.slaaavyn.soft.industry.banking.model.User;

import java.util.Collections;

public class JwtUserFactory {
    private JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                true,
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()))
        );
    }
}
