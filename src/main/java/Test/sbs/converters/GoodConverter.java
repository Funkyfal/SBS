package Test.sbs.converters;

import Test.sbs.dto.GoodDTO;
import Test.sbs.tables.Good;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class GoodConverter {
    private final ModelMapper modelMapper;

    public GoodConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public GoodDTO toDTO(Good good) {
        return modelMapper.map(good, GoodDTO.class);
    }

    public Good toEntity(GoodDTO goodDTO) {
        return modelMapper.map(goodDTO, Good.class);
    }
}
