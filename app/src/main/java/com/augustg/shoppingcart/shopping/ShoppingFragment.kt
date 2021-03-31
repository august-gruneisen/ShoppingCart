package com.augustg.shoppingcart.shopping

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.augustg.shoppingcart.R
import com.augustg.shoppingcart.databinding.FragmentShoppingBinding

class ShoppingFragment : Fragment(R.layout.fragment_shopping) {

    private var _binding: FragmentShoppingBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentShoppingBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}