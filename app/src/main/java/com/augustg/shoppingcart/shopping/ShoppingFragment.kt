/*
 * Created by August Gruneisen on 4/2/21 1:49 PM
 * Copyright (c) 2021 . All rights reserved.
 */

package com.augustg.shoppingcart.shopping

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.augustg.shoppingcart.R
import com.augustg.shoppingcart.databinding.FragmentShoppingBinding

class ShoppingFragment : Fragment(R.layout.fragment_shopping) {

    private val viewModel by navGraphViewModels<ShoppingViewModel>(R.id.nav_graph_xml)

    private var _binding: FragmentShoppingBinding? = null
    private val binding get() = _binding!!

    private val itemListAdapter = ItemListAdapter()

    private fun initViews() {
        binding.addItemFab.setOnClickListener {
            findNavController().navigate(R.id.action_shoppingFragment_to_addItemDialogFragment)
        }

        binding.itemsInCartList.adapter = itemListAdapter

        SwipeToDelete { position ->
            viewModel.removeItemFromCart(position)
            itemListAdapter.notifyItemRemoved(position)
        }.attachToRecyclerView(binding.itemsInCartList)

        binding.totalPriceInCart = viewModel.observableTotalPrice

        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun initObservers() {
        viewModel.itemsInCartLiveData().observe(viewLifecycleOwner) {
            itemListAdapter.submitList(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentShoppingBinding.bind(view)
        initViews()
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}