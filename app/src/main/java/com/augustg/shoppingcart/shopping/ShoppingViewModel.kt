package com.augustg.shoppingcart.shopping

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.augustg.shoppingcart.items.Item
import com.augustg.shoppingcart.items.Store
import com.augustg.shoppingcart.util.Formats.getAsCurrency
import com.augustg.shoppingcart.util.Formats.getLocale
import java.math.BigDecimal

class ShoppingViewModel : ViewModel() {

    fun onEnterItemButtonClicked(textEntered: String, quantity: Int) {
        if (Store.hasItem(textEntered)) {
            addItemsToCart(textEntered, quantity)
        }
    }

    private val itemsInCartLiveData = MutableLiveData<MutableList<Item>>(mutableListOf())
    fun itemsInCartLiveData(): LiveData<MutableList<Item>> = itemsInCartLiveData

    private var totalPriceInCart: BigDecimal = BigDecimal.ZERO
    var observableTotalPrice = ObservableField(getAsCurrency(totalPriceInCart))

    /**
     * @param validItemName should only be called after validating that the store has this item
     */
    private fun addItemsToCart(validItemName: String, quantity: Int) {
        val newList = itemsInCartLiveData().value?.toMutableList()
        newList?.let { items ->
            val price = Store.getPrice(validItemName)
            if (price < BigDecimal.ZERO) return // happens if the item is unavailable
            totalPriceInCart += (price * BigDecimal(quantity))
            observableTotalPrice.set(getAsCurrency(totalPriceInCart))

            for (i in 1..quantity) {
                items.add(
                    Item(
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