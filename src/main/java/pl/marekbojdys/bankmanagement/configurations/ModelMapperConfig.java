package pl.marekbojdys.bankmanagement.configurations;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ModelMapperConfig {

    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
