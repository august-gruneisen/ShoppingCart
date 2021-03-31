package com.augustg.shoppingcart.util

import java.text.NumberFormat
import java.util.*

object Formats {
    private val locale = Locale.US
    fun getLocale(): Locale = locale

    private val currencyFormat = NumberFormat.getCurrencyInstance(locale)
    fun getAsCurrency(amount: Double): String = currencyFormat.format(amount)
}