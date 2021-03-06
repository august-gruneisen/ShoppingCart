/*
 * Created by August Gruneisen on 4/2/21 1:49 PM
 * Copyright (c) 2021 . All rights reserved.
 */

package com.augustg.shoppingcart.shopping

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.augustg.shoppingcart.items.Item
import com.augustg.shoppingcart.items.Store
import com.augustg.shoppingcart.util.Formats.getAsCurrency
import com.augustg.shoppingcart.util.Formats.getLocale
import java.math.BigDecimal

class ShoppingViewModel : ViewModel() {

    companion object {
        const val MAX_QUANTITY = 3
    }

    private var quantity = 1
    var observableQuantity = ObservableInt(quantity)

    /**
     * @return false once max quantity is reached
     */
    fun incrementQuantity(): Boolean {
        return if (quantity < MAX_QUANTITY) {
            quantity++
            observableQuantity.set(quantity)
            true
        } else false
    }

    fun decrementQuantity() {
        if (quantity > 1) {
            quantity--
            observableQuantity.set(quantity)
        }
    }

    fun resetQuantity() {
        quantity = 1
        observableQuantity.set(quantity)
    }

    /**
     * @return false if the item was not found
     */
    fun onEnterItemButtonClicked(textEntered: String): Boolean {
        return when {
            textEntered.startsWith("0") -> { // user entered an item id
                try {
                    val itemName = Store.getItemName(textEntered)
                    addItemsToCart(itemName, quantity)
                    true
                } catch (e: Store.ItemNotFoundException) {
                    false
                }
            }
            Store.hasItem(textEntered) -> { // user entered an item name
                addItemsToCart(textEntered, quantity)
                true
            }
            else -> false
        }
    }

    private val itemsInCartLiveData = MutableLiveData<MutableList<Item>>(mutableListOf())
    fun itemsInCartLiveData(): LiveData<MutableList<Item>> = itemsInCartLiveData

    private var totalPriceInCart: BigDecimal = BigDecimal.ZERO
    var observableTotalPrice = ObservableField(getAsCurrency(totalPriceInCart))

    /**
     * Should only be called after validating that the store has this item
     * @throws Store.ItemNotFoundException
     */
    private fun addItemsToCart(validItemName: String, quantity: Int) {
        val newList = itemsInCartLiveData().value?.toMutableList()
        newList?.let { items ->
            val price = Store.getPrice(validItemName)
            totalPriceInCart += (price * BigDecimal(quantity))
            observableTotalPrice.set(getAsCurrency(totalPriceInCart))

            for (i in 1..quantity) {
                items.add(
                    Item(
                        id = Store.getItemId(validItemName),
                        name = validItemName.capitalize(getLocale()),
                        price = price
                    )
                )
            }
        }
        itemsInCartLiveData.value = newList
    }

    fun removeItemFromCart(position: Int) {
        val item = itemsInCartLiveData.value?.get(position)
        totalPriceInCart -= item?.price ?: BigDecimal.ZERO
        observableTotalPrice.set(getAsCurrency(totalPriceInCart))
        itemsInCartLiveData.value?.removeAt(position)
    }
}