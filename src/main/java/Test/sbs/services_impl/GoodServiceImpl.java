package Test.sbs.services_impl;

import Test.sbs.converters.GoodConverter;
import Test.sbs.dto.GoodDTO;
import Test.sbs.exceptions.CustomException;
import Test.sbs.exceptions.ErrorCode;
import Test.sbs.i_repositories.IGoodRepository;
import Test.sbs.i_services.IGoodService;
import Test.sbs.tables.Good;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class GoodServiceImpl implements IGoodService {
    private final IGoodRepository goodRepository;
    private final GoodConverter goodConverter;
    private static final Logger LOGGER = Logger.getLogger(GoodServiceImpl.class.getName());

    @Autowired
    public GoodServiceImpl(IGoodRepository goodRepository, GoodConverter goodConverter) {
        this.goodRepository = goodRepository;
        this.goodConverter = goodConverter;
    }

    public List<GoodDTO> getAllGoods() {
        LOGGER.info("Getting all goods.");
        return goodRepository.findAll()
                .stream()
                .map(goodConverter::toDTO)
                .collect(Collectors.toList());
    }

    public GoodDTO getOneGood(Long id) {
        LOGGER.info("Finding a good with ID " + id);
        return goodConverter.toDTO(goodRepository
                .findById(id)
                .orElseThrow(() -> {
                    LOGGER.warning("Can't find a good with ID " + id);
                    return new CustomException(ErrorCode.GOOD_NOT_FOUND);
                }));
    }

    public Good getOneGoodEntity(Long id) {
        return goodRepository
                .findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.GOOD_NOT_FOUND));
    }

    public GoodDTO addNewGood(GoodDTO goodDTO) {
        LOGGER.info("Adding a new good.");
        Good createdGood = goodRepository.save(goodConverter.toEntity(goodDTO));
        return goodConverter.toDTO(createdGood);
    }

    public GoodDTO replaceGood(GoodDTO goodDTO, Long id) {
        Good newGood = goodConverter.toEntity(goodDTO);
        return goodRepository.findById(id).map(good -> {
            LOGGER.info("Replacing good.");
            good.setDescription(goodDTO.getDescription());
            good.setPrice(goodDTO.getPrice());
            good.setName(goodDTO.getName());
            good.setShortName(goodDTO.getShortName());
            return goodConverter.toDTO(goodRepository.save(good));
        }).orElseGet(() -> {
            LOGGER.info("Adding good with ID " + id + " instead of replacing.");
            newGood.setId(id);
            return goodConverter.toDTO(goodRepository.save(newGood));
        });
    }

    public void deleteGood(Long id) {
        LOGGER.info("Deleting a good with ID " + id);
        goodRepository.deleteById(id);
    }

    public Long getIdForTheTests(String name, String shortName){
        return goodRepository.findByNameAndShortName(name, shortName).getId();
    }
}
