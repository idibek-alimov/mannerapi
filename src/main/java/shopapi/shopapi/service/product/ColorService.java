package shopapi.shopapi.service.product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopapi.shopapi.dto.color.ColorCreateDto;
import shopapi.shopapi.dto.color.ColorDto;
import shopapi.shopapi.models.product.Color;
import shopapi.shopapi.repository.product.ColorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ColorService {
    private final ColorRepository colorRepository;

    public List<ColorDto> getColors(){
        return colorRepository.findAll().stream().map(this::colorToColorDto).collect(Collectors.toList());
    }
    public Color getColorById(Long id){
        return colorRepository.findById(id).get();
    }
    public void createColor(ColorCreateDto colorCreateDto){
        colorRepository.save(createDtoToColor(colorCreateDto));
    }
    private Color createDtoToColor(ColorCreateDto colorCreateDto){
        return Color.builder().name(colorCreateDto.getName()).build();
    }
    private ColorDto colorToColorDto(Color color){
        return ColorDto.builder()
                .id(color.getId())
                .name(color.getName())
                .build();
    }
}

