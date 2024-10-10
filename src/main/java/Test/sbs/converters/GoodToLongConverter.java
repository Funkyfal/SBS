package Test.sbs.converters;

import Test.sbs.tables.Good;
import org.modelmapper.AbstractConverter;

public class GoodToLongConverter extends AbstractConverter<Good, Long> {
    @Override
    protected Long convert(Good good){
        return good.getId();
    }
}
