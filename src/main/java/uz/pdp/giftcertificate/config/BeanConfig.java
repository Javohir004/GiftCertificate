package uz.pdp.giftcertificate.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;

@Configuration
public class BeanConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setSkipNullEnabled(true);
        return modelMapper;
    }

    @Bean
    public Base64.Encoder encoder() {
        return Base64.getEncoder();
    }

    @Bean
    public Base64.Decoder decoder() {
        return Base64.getDecoder();
    }
}
