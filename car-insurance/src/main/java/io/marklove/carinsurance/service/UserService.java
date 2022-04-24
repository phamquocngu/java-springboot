package io.marklove.carinsurance.service;

import io.marklove.carinsurance.constant.SNSType;
import io.marklove.carinsurance.entity.Member;

public interface UserService {

    public Member createUserWithSNSInfo(String name, String snsId, String email, String mobile, SNSType type, String token);

}
