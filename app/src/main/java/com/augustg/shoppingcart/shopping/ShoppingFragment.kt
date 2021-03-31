package com.augustg.shoppingcart.shopping

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.augustg.shoppingcart.R
import com.augustg.shoppingcart.databinding.FragmentShoppingBinding

class ShoppingFragment : Fragment(R.layout.fragment_shopping) {

    private val viewModel by navGraphViewModels<ShoppingViewModel>(R.id.nav_graph_xml)

    private var _binding: FragmentShoppingBinding? = null
    private val binding get() = _binding!!

    private fun initViews() {
        binding.addItemFab.setOnClickListener {
            findNavController().navigate(R.id.action_shoppingFragment_to_addItemDialogFragment)
        }
    }

    private fun initObservers() {
        viewModel.itemEnteredLiveData().observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { item ->
                Toast.makeText(requireContext(), "Item added: $item", Toast.LENGTH_SHORT).show()
            }
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