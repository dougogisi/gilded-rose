package com.gildedrose;

@FunctionalInterface
interface EffectInterface {
    Item apply(Item n);
}

public class ItemEffects {

  /**
   * @param prev Item.
   * @return Item.
   */
  public static Item cloneItem(Item item) {
    return new Item(item.name, item.sellIn, item.quality);
  }

  /**
   * @param prev Item.
   * @return EffectInterface.
   */
  public static EffectInterface getEffectByItem(Item item) {
    String sanitizedName = item.name.replaceAll("\\s+", "").toLowerCase();

    if (sanitizedName.startsWith("agedbrie")) {
      return agedBrieQualityEffect(item);
    }

    if (sanitizedName.startsWith("backstagepasses")) {
      return backStageQualityEffect(item);
    }

    if (sanitizedName.startsWith("conjured")) {
      return conjuredQualityEffect(item);
    }

    if (sanitizedName.startsWith("sulfuras")) {
      return sulfurasQualityEffect(item);
    }

    return defaultQualityEffect(item);
  }

  /**
   * @param prev Item.
   * @return EffectInterface.
   */
  public static EffectInterface agedBrieQualityEffect(Item prev) {
    EffectInterface agedBrieQualityEffectFunc = (item) -> {
      String sanitizedName = prev.name.replaceAll("\\s+", "").toLowerCase();
      Item next = cloneItem(item);
      int quality_modifier = 1;

      if (sanitizedName.startsWith("agedbrie")) {
        next.quality = next.quality + quality_modifier > 50 ? 50 : next.quality + quality_modifier;
        next.sellIn = next.sellIn - 1;
      }
      
      return next;
    };

    return agedBrieQualityEffectFunc;
  }

  /**
   * @param prev Item.
   * @return EffectInterface.
   */
  public static EffectInterface sulfurasQualityEffect(Item prev) {
    EffectInterface sulfurasQualityFunc = (item) -> cloneItem(item);
    return sulfurasQualityFunc;
  }

  /**
   * @param prev Item.
   * @return EffectInterface.
   */
  public static EffectInterface backStageQualityEffect(Item prev) {
    EffectInterface backStageQualityEffectFunc = (item) -> {
      String sanitizedName = prev.name.replaceAll("\\s+", "").toLowerCase();
      Item next = cloneItem(item);
      int quality_modifier = 1;

      if (sanitizedName.startsWith("backstagepasses")) {
        if (next.sellIn < 6) {
          quality_modifier = 3;
        } else if (next.sellIn < 11) {
          quality_modifier = 2;
        }

        next.quality = next.quality + quality_modifier > 50 ? 50 : next.quality + quality_modifier;
        next.sellIn = next.sellIn - 1;

        if (next.sellIn < 0) {
          next.quality = 0;
        }
      }
      
      return next;
    };

    return backStageQualityEffectFunc;
  }

  /**
   * @param prev Item.
   * @return EffectInterface.
   */
  public static EffectInterface conjuredQualityEffect(Item prev) {
    EffectInterface conjuredQualityEffectFunc = (item) -> {
      String sanitizedName = prev.name.replaceAll("\\s+", "").toLowerCase();
      Item next = cloneItem(item);
      int quality_modifier = 1;

      if (sanitizedName.startsWith("conjured")) {
        quality_modifier = next.sellIn <= 0 ? quality_modifier * 4 : quality_modifier * 2;
        next.quality = next.quality - quality_modifier;
        next.quality = Math.max(0, next.quality);
        next.sellIn = next.sellIn - 1;
      }
      
      return next;
    };

    return conjuredQualityEffectFunc;
  }

  /**
   * @param prev Item.
   * @return EffectInterface.
   */
  public static EffectInterface defaultQualityEffect(Item prev) {
    EffectInterface defaultQualityEffectFunc = (item) -> {
      String sanitizedName = prev.name.replaceAll("\\s+", "").toLowerCase();
      Item next = cloneItem(item);
      int quality_modifier = 1;

      if (!sanitizedName.startsWith("backstagepasses") && !sanitizedName.startsWith("sulfuras") && !sanitizedName.startsWith("agedbrie") && !sanitizedName.startsWith("conjured")) {
        quality_modifier = next.sellIn <= 0 ? quality_modifier * 2 : quality_modifier;
        next.quality = next.quality - quality_modifier;
        next.quality = Math.max(0, next.quality);
        next.sellIn = next.sellIn - 1;
      }
      
      return next;
    };

    return defaultQualityEffectFunc;
  }
}
