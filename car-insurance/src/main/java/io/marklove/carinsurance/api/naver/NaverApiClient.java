package io.marklove.carinsurance.api.naver;

import java.util.Optional;

public interface NaverApiClient {
    Optional<UserInfo> getUserInfo(String naverAccessToken);
}
