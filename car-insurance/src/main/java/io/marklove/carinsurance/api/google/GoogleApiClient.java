package io.marklove.carinsurance.api.google;

import io.marklove.carinsurance.api.google.dto.UserInfo;

import java.util.Optional;

public interface GoogleApiClient {
    Optional<UserInfo> getUserInfo(String googleToken);
}
