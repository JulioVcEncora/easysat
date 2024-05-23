package com.encora.easysat.application;

import com.encora.easysat.domain.model.AcuseResponseDTO;
import com.encora.easysat.domain.model.ValidationRequest;
import com.encora.easysat.infrastructure.SatClient;
import com.example.consumingwebservice.wsdl.ConsultaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/easysat/api/v0.1")
public class ValidationController {
    private final SatClient satClient;

    @Autowired
    public ValidationController(SatClient satClient) {
        this.satClient = satClient;
    }

    @PostMapping("/validate")
    public AcuseResponseDTO validate(@RequestBody ValidationRequest request) {

    ConsultaResponse response = satClient
        .getConsulta(
            request.getRfc_emitter(),
            request.getRfc_receiver(),
            request.getTotal(),
            request.getUuid());

    return satClient.toAcuseResponse(response);
    }
}
