package com.inspirit.clinic.VO;

import com.inspirit.clinic.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@Data
public class UserVo extends BaseModel implements UserDetails, Serializable {

    private String id;

    private boolean enabled;

    private String username;
    private String password;

    private Set<RoleVo> authorities;

    public UserVo(String username, String password) {
        this.username = username;
        this.password = password;
        this.enabled = true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }
    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

}
