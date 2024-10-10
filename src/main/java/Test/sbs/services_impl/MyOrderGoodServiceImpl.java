package Test.sbs.services_impl;

import Test.sbs.converters.MyOrderGoodConverter;
import Test.sbs.dto.MyOrderGoodDTO;
import Test.sbs.exceptions.CustomException;
import Test.sbs.exceptions.ErrorCode;
import Test.sbs.i_repositories.IMyOrderGoodRepository;
import Test.sbs.i_services.IMyOrderGoodService;
import Test.sbs.tables.MyOrderGood;
import Test.sbs.tables.MyOrderGoodKey;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class MyOrderGoodServiceImpl implements IMyOrderGoodService {
    private final IMyOrderGoodRepository myOrderGoodRepository;
    private final MyOrderGoodConverter myOrderGoodConverter;
    private final MyOrderServiceImpl myOrderService;
    private final GoodServiceImpl goodService;
    private static final Logger LOGGER = Logger.getLogger(MyOrderGoodServiceImpl.class.getName());

    public MyOrderGoodServiceImpl(IMyOrderGoodRepository myOrderGoodRepository,
                                  MyOrderGoodConverter myOrderGoodConverter,
                                  MyOrderServiceImpl myOrderService,
                                  GoodServiceImpl goodService) {
        this.myOrderGoodRepository = myOrderGoodRepository;
        this.myOrderGoodConverter = myOrderGoodConverter;
        this.myOrderService = myOrderService;
        this.goodService = goodService;
    }

    public List<MyOrderGoodDTO> getAllMyOrderGoods() {
        LOGGER.info("Getting all orders and goods in them.");
        return myOrderGoodRepository.findAll()
                .stream()
                .map(myOrderGoodConverter::toDTO)
                .collect(Collectors.toList());
    }


    public List<MyOrderGoodDTO> findByMyOrderId(Long myOrderId) {
        LOGGER.info("Getting all goods for order with ID " + myOrderId);
        return myOrderGoodRepository.findByMyOrderId(myOrderId)
                .stream()
                .map(myOrderGoodConverter::toDTO)
                .collect(Collectors.toList());
    }


    public List<MyOrderGoodDTO> findByGoodId(Long goodId) {
        LOGGER.info("Getting orders which contain good with ID " + goodId);
        return myOrderGoodRepository.findByGoodId(goodId)
                .stream()
                .map(myOrderGoodConverter::toDTO)
                .collect(Collectors.toList());
    }


    public MyOrderGoodDTO findByMyGoodIdAndOrderId(Long goodId, Long myOrderId) {
        LOGGER.info("Getting an order with ID " + myOrderId + " and checking if it contains good with ID " + goodId);
        return myOrderGoodConverter.toDTO(myOrderGoodRepository
                .findById(new MyOrderGoodKey(goodId, myOrderId))
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_GOOD_NOT_FOUND)));
    }


    public MyOrderGoodDTO addNewMyOrderGood(MyOrderGoodDTO newMyOrderGoodDTO) {
        LOGGER.info("Adding new good with ID "
                + newMyOrderGoodDTO.getGoodId() + " for the order with ID "
                + newMyOrderGoodDTO.getMyOrderId());
        MyOrderGood myOrderGood = myOrderGoodConverter.toEntity(newMyOrderGoodDTO);
        myOrderGood.setId(new MyOrderGoodKey(
                goodService.getOneGoodEntity(newMyOrderGoodDTO.getGoodId()).getId(),
                myOrderService.getOneOrderEntity(newMyOrderGoodDTO.getMyOrderId()).getId()));
        myOrderGood.setGood(goodService.getOneGoodEntity(newMyOrderGoodDTO.getGoodId()));
        myOrderGood.setMyOrder(myOrderService.getOneOrderEntity(newMyOrderGoodDTO.getMyOrderId()));
        MyOrderGood createdMyOrderGood = myOrderGoodRepository.save(myOrderGood);
        return myOrderGoodConverter.toDTO(createdMyOrderGood);
    }


    public MyOrderGoodDTO replaceMyOrderGood(MyOrderGoodDTO newMyOrderGoodDTO,
                                             Long myOrderId,
                                             Long goodId) {
        LOGGER.info("Replacing the good with ID "
                + newMyOrderGoodDTO.getGoodId() + " for the order with ID "
                + newMyOrderGoodDTO.getMyOrderId());
        MyOrderGood myOrderGood = myOrderGoodRepository
                .findById(new MyOrderGoodKey(goodId, myOrderId))
                .orElseGet(() -> {
                    LOGGER.info("Adding new good with ID "
                            + newMyOrderGoodDTO.getGoodId() + " for the order with ID "
                            + newMyOrderGoodDTO.getMyOrderId() + " instead of replacing.");
                    MyOrderGood myOrderGoodTemp = new MyOrderGood();
                    myOrderGoodTemp.setMyOrder(myOrderService.getOneOrderEntity(myOrderId));
                    myOrderGoodTemp.setGood(goodService.getOneGoodEntity(goodId));
                    myOrderGoodTemp.setId(new MyOrderGoodKey(goodId, myOrderId));
                    return myOrderGoodTemp;
                });
        myOrderGood.setQuantity(newMyOrderGoodDTO.getQuantity());
        return myOrderGoodConverter.toDTO(myOrderGoodRepository.save(myOrderGood));
    }


    public void deleteMyOrderGood(Long goodId, Long myOrderId) {
        myOrderGoodRepository.deleteById(new MyOrderGoodKey(goodId, myOrderId));
    }
}
