package backcore;

import backcore.items.*;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;


public class BackcoreItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BackcoreMain.MODID);
    // 物品
    public static final DeferredItem<Item> ALMOND_WATER_BOTTLE_ITEM = ITEMS.registerItem("almond_water_bottle", AlmondWaterItem::new, new Item.Properties().food(
            new FoodProperties.Builder()
                    .nutrition(3)
                    .saturationModifier(1f)
                    .alwaysEdible()
                    .usingConvertsTo(Items.GLASS_BOTTLE)
                    .build()
    ));
    public static final DeferredItem<Item> TELEPORT_ITEM_0_1 = ITEMS.registerItem("teleport_item_0_1", TeleportItem_0_1::new);
    public static final DeferredItem<Item> TELEPORT_ITEM_1_BASE = ITEMS.registerItem("teleport_item_1_base", Item::new);
    public static final DeferredItem<Item> TELEPORT_ITEM_1_0 = ITEMS.registerItem("teleport_item_1_0", TeleportItem_1_0::new);
    public static final DeferredItem<Item> TELEPORT_ITEM_1_2 = ITEMS.registerItem("teleport_item_1_2", TeleportItem_1_2::new);
    public static final DeferredItem<Item> TELEPORT_ITEM_2_BASE = ITEMS.registerItem("teleport_item_2_base", Item::new);
    public static final DeferredItem<Item> TELEPORT_ITEM_2_1 = ITEMS.registerItem("teleport_item_2_1", TeleportItem_2_1::new);
    public static final DeferredItem<Item> TELEPORT_ITEM_2_11 = ITEMS.registerItem("teleport_item_2_11", TeleportItem_2_11::new);


    // 自动生成：物品模型
    public static final List<DeferredItem<Item>> ALL_ITEMS = List.of(
            ALMOND_WATER_BOTTLE_ITEM,
            TELEPORT_ITEM_0_1,
            TELEPORT_ITEM_1_BASE,
            TELEPORT_ITEM_1_0,
            TELEPORT_ITEM_1_2,
            TELEPORT_ITEM_2_BASE,
            TELEPORT_ITEM_2_1,
            TELEPORT_ITEM_2_11
    );

    public static final List<DeferredItem<Item>> SIMPLE_MODEL_ITEMS = List.of(
            ALMOND_WATER_BOTTLE_ITEM,
            TELEPORT_ITEM_0_1,
            TELEPORT_ITEM_1_BASE,
            TELEPORT_ITEM_1_0,
            TELEPORT_ITEM_1_2,
            TELEPORT_ITEM_2_BASE,
            TELEPORT_ITEM_2_1,
            TELEPORT_ITEM_2_11
    );
}
