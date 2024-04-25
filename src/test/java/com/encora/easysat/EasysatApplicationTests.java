package com.encora.easysat;

import com.example.consumingwebservice.wsdl.ObjectFactory;
import jakarta.xml.bind.JAXBElement;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EasysatApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testCreateConsultaExpresionImpresa() {
		ObjectFactory factory = new ObjectFactory();
		String expectedSoapRequest = "expectedSoapRequest";
		JAXBElement<String> result = factory.createConsultaExpresionImpresa(expectedSoapRequest);

		assertNotNull(result);
		assertEquals(expectedSoapRequest, result.getValue());

	}

}
