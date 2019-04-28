package com.gildedrose;

import java.util.ArrayList;

public class ItemStateManager
{
  private Item prev;
  private Item current;

  private ArrayList<EffectInterface> itemStateEffects = new ArrayList<EffectInterface>();

  /**
   * @param current Item.
   * @return Item.
   */
  public ItemStateManager(Item current) {
    this.current = current;
  }

  /**
   * @param current Item.
   * @return Item.
   */
  public Item cloneItem(Item item) {
    return new Item(item.name, item.sellIn, item.quality);
  }

  /**
   * @param stateEffectFunction EffectInterface.
   * @return void.
   */
  public void addStateEffect(EffectInterface stateEffectFunction) {
    this.itemStateEffects.add(stateEffectFunction);
  }

  public Item getNext() {
    this.prev = cloneItem(this.current);
    this.current = applyStateEffects(this.current);
    
    return this.current;
  }

  public Item getPrev() {
    return this.prev;
  }

  /**
   * @param item Item.
   * @return Item.
   */
  public Item applyStateEffects(Item item) {
    Item next = cloneItem(item);

    for (EffectInterface effect: this.itemStateEffects) {
      next = effect.apply(next);
    }

    return next;
  }
}