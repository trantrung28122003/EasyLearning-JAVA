package com.hutech.easylearning.mapper;

import com.hutech.easylearning.dto.reponse.RoleResponse;
import com.hutech.easylearning.dto.reponse.UserResponse;
import com.hutech.easylearning.dto.request.UserCreationRequest;
import com.hutech.easylearning.dto.request.UserUpdateRequest;
import com.hutech.easylearning.entity.Role;
import com.hutech.easylearning.entity.User;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-30T09:51:58+0700",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(UserCreationRequest request) {
        if ( request == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.userName( request.getUserName() );
        user.email( request.getEmail() );
        user.fullName( request.getFullName() );
        user.dayOfBirth( request.getDayOfBirth() );
        user.password( request.getPassword() );
        user.dateCreate( request.getDateCreate() );
        user.dateChange( request.getDateChange() );
        user.changedBy( request.getChangedBy() );

        return user.build();
    }

    @Override
    public UserResponse toUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.id( user.getId() );
        userResponse.userName( user.getUserName() );
        userResponse.email( user.getEmail() );
        userResponse.fullName( user.getFullName() );
        userResponse.dayOfBirth( user.getDayOfBirth() );
        userResponse.roles( roleSetToRoleResponseSet( user.getRoles() ) );
        userResponse.dateCreate( user.getDateCreate() );
        userResponse.dateChange( user.getDateChange() );
        userResponse.changedBy( user.getChangedBy() );

        return userResponse.build();
    }

    @Override
    public void updateUser(User user, UserUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        user.setEmail( request.getEmail() );
        user.setFullName( request.getFullName() );
        user.setDayOfBirth( request.getDayOfBirth() );
        user.setPassword( request.getPassword() );
        user.setDateCreate( request.getDateCreate() );
        user.setDateChange( request.getDateChange() );
        user.setChangedBy( request.getChangedBy() );
        user.setDeleted( request.isDeleted() );
    }

    protected RoleResponse roleToRoleResponse(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleResponse.RoleResponseBuilder roleResponse = RoleResponse.builder();

        roleResponse.id( role.getId() );
        roleResponse.name( role.getName() );

        return roleResponse.build();
    }

    protected Set<RoleResponse> roleSetToRoleResponseSet(Set<Role> set) {
        if ( set == null ) {
            return null;
        }

        Set<RoleResponse> set1 = new LinkedHashSet<RoleResponse>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Role role : set ) {
            set1.add( roleToRoleResponse( role ) );
        }

        return set1;
    }
}
