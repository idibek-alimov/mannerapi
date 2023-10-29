package shopapi.shopapi.controller.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import shopapi.shopapi.dto.category.CategoryCreateDto;
import shopapi.shopapi.dto.color.ColorCreateDto;
import shopapi.shopapi.dto.color.ColorDto;
import shopapi.shopapi.service.product.ColorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/color")
public class ColorController {
    private final ColorService colorService;

    @PostMapping(path="/manager/create",consumes = {MediaType.APPLICATION_JSON_VALUE})//,consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin("*")
    public void createCategory(@RequestBody ColorCreateDto color){
        colorService.createColor(color);
    }
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    public List<ColorDto> getColors(){
        return colorService.getColors();
    }
}
