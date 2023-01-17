package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    // /basic/items/add로 Get 요청이 올 때 동작
    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    // /basic/items/add로 Post 요청이 올 때 동작
    // @PostMapping ("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam Integer price,
                       @RequestParam Integer quantity,
                       Model model) {

        // Item item = new Item(itemName, price, quantity);
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);

        return "basic/item";
    }

    // @ModelAttribute 적용
    // @PostMapping ("/add")
    public String addItemV2(@ModelAttribute("item") Item item, Model model) {
        itemRepository.save(item);

        // @ModelAttribute는 아래의 코드도 자동으로 추가, 생략 가능
        // model.addAttribute("item", item);

        return "basic/item";
    }

    // 제일 권장되는 방법
    // @PostMapping ("/add")
    public String addItemV3(@ModelAttribute Item item) {
        // @ModelAttribute의 이름을 생략하면...
        // Item -> item / HelloData -> helloData 로 인식

        itemRepository.save(item);

        return "basic/item";
    }

    // @ModelAttribute도 생략 가능
    // @PostMapping ("/add")
    public String addItemV4(Item item) {
        itemRepository.save(item);
        return "basic/item";
    }

    // 제일 권장되는 방법 + PRG
    // @PostMapping ("/add")
    public String addItemV5(@ModelAttribute Item item) {
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }

    // 제일 권장되는 방법 + PRG + RedirectAttributes
    @PostMapping ("/add")
    public String addItemV6(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
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
