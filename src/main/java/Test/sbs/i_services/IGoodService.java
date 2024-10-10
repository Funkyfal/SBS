package Test.sbs.i_services;

import Test.sbs.dto.GoodDTO;

import java.util.List;

public interface IGoodService {
    List<GoodDTO> getAllGoods();
    GoodDTO getOneGood(Long id);
    GoodDTO addNewGood(GoodDTO goodDTO);
    GoodDTO replaceGood(GoodDTO goodDTO, Long id);
    void deleteGood(Long id);
}
