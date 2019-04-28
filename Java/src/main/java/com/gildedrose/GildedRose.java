package com.gildedrose;

class GildedRose {
  Item[] items;

  public GildedRose(Item[] items) {
      this.items = items;
  }

  public void updateQuality() {
    for (int i = 0; i < items.length; i++) {
      ItemStateManager itemStateManager = new ItemStateManager(items[i]);

      /**
       * Rather declaring a class hierarchy which may be the natural OOP way to deal with this instead I
       * create a concept of effects and a state manager to takes those effects and apply them to items
       * Effects are functions that implement the EffectInterface interface
       */
      itemStateManager.addStateEffect(ItemEffects.agedBrieQualityEffect(items[i]));
      itemStateManager.addStateEffect(ItemEffects.sulfurasQualityEffect(items[i]));
      itemStateManager.addStateEffect(ItemEffects.backStageQualityEffect(items[i]));
      itemStateManager.addStateEffect(ItemEffects.conjuredQualityEffect(items[i]));
      itemStateManager.addStateEffect(ItemEffects.defaultQualityEffect(items[i]));
      itemStateManager.addStateEffect(ItemEffects.sellInEffect(items[i]));
      items[i] = itemStateManager.getNext();
    }
  }
}
