package com.augustg.shoppingcart.items

import java.math.BigDecimal

data class Item(
    val id: String,
    val name: String,
    val price: BigDecimal
)