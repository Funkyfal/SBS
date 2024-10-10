package Test.sbs.utils;

import Test.sbs.converters.GoodToLongConverter;
import Test.sbs.converters.MyOrderToLongConverter;
import Test.sbs.converters.PersonToLongConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new PersonToLongConverter());
        modelMapper.addConverter(new MyOrderToLongConverter());
        modelMapper.addConverter(new GoodToLongConverter());
        return modelMapper;
    }
}
