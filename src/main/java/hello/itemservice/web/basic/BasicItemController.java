package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor // final이 붙은 필드들의 생성자를 자동으로 만들어 줌
public class BasicItemController {
    // 생성자가 딱 1개만 있으면, 스프링이 해당 생성자에 자동으로 @Autowired를 적용하고, 의존관계를 주입해줌
    // 따라서 final 키워드를 빼면 안되며, 만일 final 키워드를 빼면 ItemRepository 의존관계 주입이 안된다.
    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct // 초기화
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }

}
