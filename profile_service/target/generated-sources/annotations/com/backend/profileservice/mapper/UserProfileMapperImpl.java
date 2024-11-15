package com.backend.profileservice.mapper;

import com.backend.profileservice.dto.request.ProfileCreationRequest;
import com.backend.profileservice.dto.request.UserProfileUpdateRequest;
import com.backend.profileservice.dto.response.UserProfileResponse;
import com.backend.profileservice.entity.UserProfile;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.4 (Oracle Corporation)"
)
@Component
public class UserProfileMapperImpl implements UserProfileMapper {

    @Override
    public UserProfile toUserProfile(ProfileCreationRequest request) {
        if ( request == null ) {
            return null;
        }

        UserProfile.UserProfileBuilder userProfile = UserProfile.builder();

        userProfile.userId( request.getUserId() );
        userProfile.firstName( request.getFirstName() );
        userProfile.lastName( request.getLastName() );
        userProfile.dateOfBirth( request.getDateOfBirth() );
        userProfile.ward( request.getWard() );
        userProfile.district( request.getDistrict() );
        userProfile.province( request.getProvince() );

        return userProfile.build();
    }

    @Override
    public UserProfileResponse toUserProfileResponse(UserProfile userProfile) {
        if ( userProfile == null ) {
            return null;
        }

        UserProfileResponse userProfileResponse = new UserProfileResponse();

        userProfileResponse.setId( userProfile.getId() );
        userProfileResponse.setFirstName( userProfile.getFirstName() );
        userProfileResponse.setLastName( userProfile.getLastName() );
        userProfileResponse.setDateOfBirth( userProfile.getDateOfBirth() );
        userProfileResponse.setWard( userProfile.getWard() );
        userProfileResponse.setDistrict( userProfile.getDistrict() );
        userProfileResponse.setProvince( userProfile.getProvince() );

        return userProfileResponse;
    }

    @Override
    public void updateUserProfile(UserProfile user, UserProfileUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        user.setFirstName( request.getFirstName() );
        user.setLastName( request.getLastName() );
        user.setDateOfBirth( request.getDateOfBirth() );
        user.setWard( request.getWard() );
        user.setDistrict( request.getDistrict() );
        user.setProvince( request.getProvince() );
    }
}
