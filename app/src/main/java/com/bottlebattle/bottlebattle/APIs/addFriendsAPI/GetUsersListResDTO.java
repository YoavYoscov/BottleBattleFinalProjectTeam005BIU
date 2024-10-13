package com.bottlebattle.bottlebattle.APIs.addFriendsAPI;

import com.bottlebattle.bottlebattle.APIs.LeaderboardsAPI.LeaderboardsServerResDTO;
import com.bottlebattle.bottlebattle.APIs.UserAPI.UserDetailsDTO;

import java.util.List;

public class GetUsersListResDTO extends LeaderboardsServerResDTO {
    public GetUsersListResDTO(boolean success, String message, int status, List<UserDetailsDTO> data) {
        super(success, message, status, data);
    }
}
