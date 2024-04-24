package com.encora.easysat.domain.service;

public interface ValidationService {
    String validate(String rfc_emitter, String rfc_receiver, String total, String uuid);
}
