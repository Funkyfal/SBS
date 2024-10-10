package Test.sbs.rest_controllers;

import Test.sbs.dto.GoodDTO;
import Test.sbs.services_impl.GoodServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class GoodRestController {
    private final GoodServiceImpl goodService;

    GoodRestController(GoodServiceImpl goodService) {
        this.goodService = goodService;
    }

    @GetMapping("/good")
    public ResponseEntity<List<GoodDTO>> getAllGoods() {
        return ResponseEntity.ok(goodService.getAllGoods());
    }

    @GetMapping("/good/{id}")
    public ResponseEntity<GoodDTO> getOneGood(@PathVariable Long id) {
        return ResponseEntity.ok(goodService.getOneGood(id));
    }

    @PostMapping("/good")
    public ResponseEntity<GoodDTO> addNewGood(@RequestBody GoodDTO newGoodDTO) {
        return ResponseEntity.created(URI.create("/good/"))
                .body(goodService.addNewGood(newGoodDTO));
    }

    @PutMapping("/good/{id}")
    public ResponseEntity<GoodDTO> replaceGood(@RequestBody GoodDTO newGoodDTO, @PathVariable Long id) {
        return ResponseEntity.ok(goodService.replaceGood(newGoodDTO, id));
    }

    @DeleteMapping("good/{id}")
    void deleteGood(@PathVariable Long id) {
        goodService.deleteGood(id);
    }
}
