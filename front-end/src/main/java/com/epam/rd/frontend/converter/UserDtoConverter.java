package com.epam.rd.frontend.converter;

import com.epam.rd.dto.RoleDto;
import com.epam.rd.dto.UserDto;
import com.epam.rd.frontend.model.CurrentUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.Set;

@Component
public class UserDtoConverter {

    public CurrentUser convert(UserDto userDto) {
        return new CurrentUser(
                userDto.getPassword(),
                userDto.getEmail(),
                createGrantedAuthorities(userDto.getRoles()));
    }
    private Collection<? extends GrantedAuthority> createGrantedAuthorities(Set<RoleDto> rolesDto) {
        String[] roles = new String[rolesDto.size()];
        int index = 0;
        for (RoleDto role:rolesDto) {
            roles[index++] = "ROLE_" + role.toString();
        }
        return AuthorityUtils.createAuthorityList(roles);
    }
}
