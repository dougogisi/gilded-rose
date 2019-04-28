package com.gildedrose;

import static org.junit.Assert.*;

import org.junit.Test;

public class GildedRoseTest {

    @Test
    public void foo() {
        Item[] items = new Item[] { new Item("foo", 0, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals("foo", app.items[0].name);
    }

    @Test
    public void testConjuredItemsDepreciateTwiceAsFast() {
        Item[] items = new Item[] { new Item("conjured super item", 5, 8) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(6, app.items[0].quality);
        assertEquals(4, app.items[0].sellIn);

        items = new Item[] { app.items[0] };
        app = new GildedRose(items);

        app.updateQuality();
        assertEquals(4, app.items[0].quality);
        assertEquals(3, app.items[0].sellIn);
    }

    @Test
    public void testItemQualityCannotRiseAbove50() {
        Item[] items = new Item[] { new Item("backstage passes to some where", 5, 50) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(50, app.items[0].quality);
    }

}
