package com.hailian.ylwmall.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class WSDLConfig {
    @Value("${mdm.customer.wsdl}")
    String customerWSDL;
    @Value("${mdm.supplier.wsdl}")
    String supplierWSDL;
}
