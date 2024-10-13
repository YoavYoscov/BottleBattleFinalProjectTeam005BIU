package com.bottlebattle.bottlebattle.APIs.SettingsAPI;

public class ProfilePicDTO {
    private String profilePic;

    public ProfilePicDTO(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}
