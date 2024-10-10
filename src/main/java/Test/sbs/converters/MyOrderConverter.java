package Test.sbs.converters;

import Test.sbs.dto.MyOrderDTO;
import Test.sbs.tables.MyOrder;
import Test.sbs.tables.StatusEnum;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MyOrderConverter {
    private final ModelMapper modelMapper;

    public MyOrderConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public MyOrderDTO toDTO(MyOrder myOrder) {
        return modelMapper.map(myOrder, MyOrderDTO.class);
    }

    public MyOrder toEntity(MyOrderDTO myOrderDTO){
        return modelMapper.map(myOrderDTO, MyOrder.class);
    }
}
