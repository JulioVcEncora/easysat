package com.encora.easysat;

import com.encora.easysat.domain.model.AcuseResponseDTO;
import com.encora.easysat.domain.model.ValidationRequest;
import com.encora.easysat.infrastructure.SatClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import com.example.consumingwebservice.wsdl.ConsultaResponse;
import org.springframework.stereotype.Component;

@Component
public class QueueProcessor {
    private final SatClient satClient;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final static Logger logger = LoggerFactory.getLogger(QueueProcessor.class);

    public QueueProcessor(ObjectMapper objectMapper, RabbitTemplate rabbitTemplate, SatClient satClient) {
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
        this.satClient = satClient;
    }

    @RabbitListener(queues = "easysat-request")
    public void receiveMessage(String message) {
        logger.info("Received message: " + message);
        try {
            ValidationRequest validationRequest = objectMapper.readValue(message, ValidationRequest.class);
            ConsultaResponse response = satClient.getConsulta(validationRequest.getRfc_emitter(), validationRequest.getRfc_receiver(), validationRequest.getTotal(), validationRequest.getUuid());

            // Convert the response to a valid JSON
            AcuseResponseDTO acuseResponse = satClient.toAcuseResponse(response);
            String formattedResponse = objectMapper.writeValueAsString(acuseResponse);
            logger.info("Response ready: " + formattedResponse);

            // Send Response to response queue
            rabbitTemplate.convertAndSend("easysat-response", formattedResponse);
        } catch (Exception e) {
            logger.error("Error processing message: " + message, e);
        }
    }

}
