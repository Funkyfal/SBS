package Test.sbs.converters;

import Test.sbs.tables.MyOrder;
import org.modelmapper.AbstractConverter;

public class MyOrderToLongConverter extends AbstractConverter<MyOrder, Long> {
    @Override
    protected Long convert(MyOrder myOrder){
        return myOrder.getId();
    }
}
