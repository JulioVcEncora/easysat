package com.encora.easysat.infrastructure;

import com.encora.easysat.domain.presentation.AcuseResponseDTO;
import com.encora.easysat.domain.model.AcuseResponse;
import com.example.consumingwebservice.wsdl.Consulta;
import com.example.consumingwebservice.wsdl.ConsultaResponse;
import com.example.consumingwebservice.wsdl.ObjectFactory;
import jakarta.xml.bind.JAXBElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;


public class SatClient extends WebServiceGatewaySupport {

    @Autowired
    private AcuseResponseDTO acuseResponseDTO;

    public ConsultaResponse getConsulta(String rfc_emitter, String rfc_receiver, String total, String uuid) {
        String soapRequest = String.format("?re=%s&rr=%s&tt=%s&id=%s",
                rfc_emitter,
                rfc_receiver,
                total,
                uuid);

        ObjectFactory factory = new ObjectFactory();
        Consulta consulta = factory.createConsulta();
        JAXBElement<String> jaxbElement = factory.createConsultaExpresionImpresa(soapRequest);
        consulta.setExpresionImpresa(jaxbElement);

        return (ConsultaResponse) getWebServiceTemplate()
                .marshalSendAndReceive("https://consultaqr.facturaelectronica.sat.gob.mx/ConsultaCFDIService.svc", consulta,
                        new SoapActionCallback(
                                "http://tempuri.org/IConsultaCFDIService/Consulta"));
    }

    public AcuseResponse toAcuseReponse(ConsultaResponse response) {
        return acuseResponseDTO.toAcuseResponse(response);
    }

}
