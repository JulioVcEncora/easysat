package com.encora.easysat.infrastructure;

import com.encora.easysat.domain.presentation.AcuseResponseMapper;
import com.encora.easysat.domain.model.AcuseResponseDTO;
import com.example.consumingwebservice.wsdl.Consulta;
import com.example.consumingwebservice.wsdl.ConsultaResponse;
import com.example.consumingwebservice.wsdl.ObjectFactory;
import jakarta.xml.bind.JAXBElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;


public class SatClient extends WebServiceGatewaySupport {
    private final AcuseResponseMapper acuseResponseDTO;
    private static final Logger log = LoggerFactory.getLogger(SatClient.class);

    public SatClient() {
        this.acuseResponseDTO = new AcuseResponseMapper();
    }

    public ConsultaResponse getConsulta(String rfc_emitter, String rfc_receiver, String total, String uuid) {
        String soapRequest = String.format("?re=%s&rr=%s&tt=%s&id=%s",
                rfc_emitter,
                rfc_receiver,
                total,
                uuid);

        log.info("Requesting consulta for: " + soapRequest);

        ObjectFactory factory = new ObjectFactory();
        Consulta consulta = factory.createConsulta();
        JAXBElement<String> jaxbElement = factory.createConsultaExpresionImpresa(soapRequest);
        consulta.setExpresionImpresa(jaxbElement);

        return (ConsultaResponse) getWebServiceTemplate()
                .marshalSendAndReceive("https://consultaqr.facturaelectronica.sat.gob.mx/ConsultaCFDIService.svc", consulta,
                        new SoapActionCallback(
                                "http://tempuri.org/IConsultaCFDIService/Consulta"));
    }

    public AcuseResponseDTO toAcuseResponse(ConsultaResponse response) {
        return acuseResponseDTO.toAcuseResponse(response);
    }

}
