describe("Gilded Rose", function() {

  it("conjured items should degrade in quality twice as fast as normal items", function() {
    items = [ new Item("conjured cake", 2, 10) ];
    update_quality();
    expect(items[0].name).toEqual("conjured cake");
    expect(items[0].quality).toEqual(8);
    update_quality();
    expect(items[0].quality).toEqual(6);
    expect(items[0].sell_in).toEqual(0);
    update_quality();
    //depreciation further doubled since sell_in date has past
    expect(items[0].quality).toEqual(2);
  });

  it("backstage passes should degrade in Quality to 0 after sell date", function() {
    items = [ new Item("Backstage passes for some cool thing", 6, 10) ];
    update_quality();
    // improves in quality by 2 when sell_in less than 11
    expect(items[0].quality).toEqual(12);

    update_quality();
    // improves in quality by 3 when sell_in less than 6
    expect(items[0].quality).toEqual(15);

    // after sellin date passes quality drops to 0 (similate 5 days passing)
    for (var i = 0; i < 5; i++) {
      update_quality();
    }

    expect(items[0].quality).toEqual(0);
  });

  it("should degrade in Quality twice as fast when sell_in date past", function() {
    items = [ new Item("Some random item", 1, 9) ];
    update_quality();
    expect(items[0].quality).toEqual(8);
    expect(items[0].sell_in).toEqual(0);
    
    update_quality();
    expect(items[0].quality).toEqual(6);
    expect(items[0].sell_in).toEqual(-1);
  });

  it("legendary should never depreciate in quality", function() {
    items = [ new Item("Sulfuras, Back Bone of Ragnaros", 10, 80) ];
    update_quality();
    expect(items[0].quality).toEqual(80);
    update_quality();
    expect(items[0].quality).toEqual(80);
  });

});
