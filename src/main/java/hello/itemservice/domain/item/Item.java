package hello.itemservice.domain.item;

import lombok.Data;

// @Getter @Setter // @Getter와 @Setter만 필요한 경우 이렇게만 쓰는 것이 권장됨
@Data // @Data는 @Getter와 @Setter 등 여러 애노테이션이 포함되어 있는데, 데이터 관련해서 사용할 땐 주의해서 사용해야 함
public class Item {

    private Long id;
    private String itemName;
    private Integer price; // null인 경우도 있기 때문에 Integer 사용
    private Integer quantity; // null인 경우도 있기 때문에 Integer 사용


    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
