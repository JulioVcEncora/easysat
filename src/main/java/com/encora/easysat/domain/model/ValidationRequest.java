package com.encora.easysat.domain.model;

import lombok.Data;

@Data
public class ValidationRequest {
    private String rfc_emitter;
    private String rfc_receiver;
    private String total;
    private String uuid;
}
