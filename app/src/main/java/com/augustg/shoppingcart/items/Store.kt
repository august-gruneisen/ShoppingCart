package com.augustg.shoppingcart.items

import java.lang.Exception
import java.math.BigDecimal
import java.util.*

object Store {

    // can extend to support quantity-in-stock, category, etc.
    data class ItemInformation(
        val id: String,
        val price: Double
    )

    /**
     * This is a sample of items that a store may have in stock
     * Item names should be lowercase
     */
    val sampleInventory = mapOf(
        "apple" to ItemInformation(id = "0001", price = 1.99),
        "swordfish" to ItemInformation(id = "0002", price = 21.99),
        "blueberries" to ItemInformation(id = "0003", price = 5.99),
        "cashews" to ItemInformation(id = "0004", price = 10.99)
    )

    /**
     * Determines whether or not a store has a particular item
     */
    fun hasItem(itemName: String): Boolean {
        val formattedItemName = itemName.toLowerCase(Locale.US)
        return sampleInventory.contains(formattedItemName)
    }

    class ItemNotFoundException : Exception("This item does not exist in the sample inventory")

    /**
     * @return the name of an item
     */
    fun getItemName(itemId: String): String {
        return sampleInventory.entries.find {
            it.value.id == itemId
        }?.key ?: throw ItemNotFoundException()
    }

    /**
     * Returns the id of an item
     */
    fun getItemId(itemName: String): String {
        return sampleInventory[itemName]?.id ?: throw ItemNotFoundException()
    }

    /**
     * Returns the price of an item
     */
    fun getPrice(itemName: String): BigDecimal {
        return sampleInventory[itemName]?.price?.toBigDecimal() ?: throw ItemNotFoundException()
    }
}