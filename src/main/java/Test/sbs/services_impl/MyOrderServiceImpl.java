package Test.sbs.services_impl;

import Test.sbs.converters.MyOrderConverter;
import Test.sbs.dto.MyOrderDTO;
import Test.sbs.exceptions.CustomException;
import Test.sbs.exceptions.ErrorCode;
import Test.sbs.i_repositories.IMyOrderRepository;
import Test.sbs.i_services.IMyOrderService;
import Test.sbs.tables.MyOrder;
import Test.sbs.tables.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class MyOrderServiceImpl implements IMyOrderService {
    private final IMyOrderRepository myOrderRepository;
    private final MyOrderConverter myOrderConverter;
    private final PersonServiceImpl personService;
    private static final Logger LOGGER = Logger.getLogger(MyOrderServiceImpl.class.getName());

    @Autowired
    public MyOrderServiceImpl(IMyOrderRepository myOrderRepository, MyOrderConverter myOrderConverter, PersonServiceImpl personService) {
        this.myOrderRepository = myOrderRepository;
        this.myOrderConverter = myOrderConverter;
        this.personService = personService;
    }

    public List<MyOrderDTO> getAllOrders() {
        LOGGER.info("Getting all orders.");
        return myOrderRepository.findAll()
                .stream()
                .map(myOrderConverter::toDTO)
                .collect(Collectors.toList());
    }

    public MyOrderDTO getOneOrder(Long id) {
        LOGGER.info("Getting an order with id " + id);
        return myOrderConverter.toDTO(myOrderRepository
                .findById(id)
                .orElseThrow(() -> {
                    LOGGER.warning("Failed to find an order with id " + id);
                    return new CustomException(ErrorCode.ORDER_NOT_FOUND);
                }));
    }
    public MyOrder getOneOrderEntity(Long id) {
        return myOrderRepository
                .findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
    }

    public MyOrderDTO addNewOrder(MyOrderDTO myOrderDTO) {
        LOGGER.info("Adding new order.");
        MyOrder order = myOrderConverter.toEntity(myOrderDTO);
        order.setPerson(personService.getOnePersonForAnOrder(myOrderDTO.getPersonId()));
        MyOrder createdOrder = myOrderRepository.save(order);
        return myOrderConverter.toDTO(createdOrder);
    }

    public MyOrderDTO replaceOrder(MyOrderDTO myOrderDTO, Long id) {
        MyOrder newMyOrder = myOrderConverter.toEntity(myOrderDTO);
        LOGGER.info("Replacing or adding a new order with id " + id);
        return myOrderRepository.findById(id).map(order -> {
            order.setStatus(myOrderDTO.getStatus());
            order.setPerson(personService.getOnePersonForAnOrder(myOrderDTO.getPersonId()));
            order.setDelivery(myOrderDTO.isDelivery());
            MyOrder createdOrder = myOrderRepository.save(order);
            return myOrderConverter.toDTO(createdOrder);
        }).orElseGet(() -> {
            newMyOrder.setId(id);
            newMyOrder.setPerson(personService.getOnePersonForAnOrder(myOrderDTO.getPersonId()));
            MyOrder createdOrder = myOrderRepository.save(newMyOrder);
            return myOrderConverter.toDTO(createdOrder);
        });
    }

    public void deleteOrder(Long id) {
        myOrderRepository.deleteById(id);
        LOGGER.info("Deleting order with id " + id);
    }

    public Long getIdForTheTests(StatusEnum status, boolean delivery){
        return myOrderRepository.findByStatusAndDelivery(status, delivery).getId();
    }
}
