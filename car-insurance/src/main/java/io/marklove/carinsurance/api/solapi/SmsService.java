package io.marklove.carinsurance.api.solapi;

public interface SmsService {
    SmsResponse sendSingleMessage(SingleMessage message);
}
