package Test.sbs.converters;

import Test.sbs.dto.MyOrderGoodDTO;
import Test.sbs.tables.MyOrderGood;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MyOrderGoodConverter {
    private final ModelMapper modelMapper;

    public MyOrderGoodConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public MyOrderGoodDTO toDTO(MyOrderGood myOrderGood){
        return modelMapper.map(myOrderGood, MyOrderGoodDTO.class);
    }

    public MyOrderGood toEntity(MyOrderGoodDTO myOrderGoodDTO){
        return modelMapper.map(myOrderGoodDTO, MyOrderGood.class);
    }
}
