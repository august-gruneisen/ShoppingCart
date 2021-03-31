package com.augustg.shoppingcart.items

import java.util.*

object Store {

    // can extend to support quantity-in-stock, category, etc.
    data class ItemInformation(
        val price: Double
    )

    /**
     * This is a sample of items that a store may have in stock
     * Item names should be lowercase
     */
    val sampleInventory = mapOf(
        "apple" to ItemInformation(price = 1.99),
        "swordfish" to ItemInformation(price = 21.99),
        "blueberries" to ItemInformation(price = 5.99),
        "cashews" to ItemInformation(price = 10.99)
    )

    /**
     * Determines whether or not a store has a particular item
     */
    fun hasItem(itemName: String): Boolean {
        val formattedItemName = itemName.toLowerCase(Locale.US)
        return sampleInventory.contains(formattedItemName)
    }

    /**
     * Returns the price of an item, or -1 if that item is out of stock
     */
    fun getPriceIfInStock(itemName: String): Double {
        return sampleInventory[itemName]?.price ?: -1.0
    }
}