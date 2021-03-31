package com.augustg.shoppingcart.shopping

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.augustg.shoppingcart.util.Event

class ShoppingViewModel : ViewModel() {

    private val itemEnteredLiveData = MutableLiveData<Event<String>>()
    fun itemEnteredLiveData(): LiveData<Event<String>> = itemEnteredLiveData

    fun onEnterItemButtonClicked(textEntered: String) {
        itemEnteredLiveData.value = Event(textEntered)
    }
}