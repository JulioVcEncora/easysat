package com.encora.easysat.domain.presentation;

import com.encora.easysat.domain.model.AcuseResponseDTO;
import com.example.consumingwebservice.wsdl.ConsultaResponse;

public class AcuseResponseMapper {
    public AcuseResponseDTO toAcuseResponse(ConsultaResponse response) {
        AcuseResponseDTO acuseResponse = new AcuseResponseDTO();
        acuseResponse.setCodigoEstatus(response.getConsultaResult().getValue().getCodigoEstatus().getValue());
        acuseResponse.setEsCancelable(response.getConsultaResult().getValue().getEsCancelable().getValue());
        acuseResponse.setEstado(response.getConsultaResult().getValue().getEstado().getValue());
        acuseResponse.setEstatusCancelacion(response.getConsultaResult().getValue().getEstatusCancelacion().getValue());
        acuseResponse.setValidacionEFOS(response.getConsultaResult().getValue().getValidacionEFOS().getValue());
        return acuseResponse;
    }
}
