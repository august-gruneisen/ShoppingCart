package com.augustg.shoppingcart.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.navGraphViewModels
import com.augustg.shoppingcart.R
import com.augustg.shoppingcart.databinding.DialogFragmentAddItemBinding
import com.augustg.shoppingcart.items.Store
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddItemDialogFragment : BottomSheetDialogFragment() {

    private val viewModel by navGraphViewModels<ShoppingViewModel>(R.id.nav_graph_xml)

    private var _binding: DialogFragmentAddItemBinding? = null
    private val binding get() = _binding!!

    var quantity = 0 // TODO: move to view model

    private fun initViews() {
        binding.enterItemNameField.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.select_dialog_item,
                Store.sampleInventory.keys.toList()
            )
        )

        binding.incrementQuantityButton.setOnClickListener {
            if (quantity < 3) {
                quantity++
                binding.quantityField.text = quantity.toString()
            } else {
                Toast.makeText(requireContext(), "Max 3 of each item", Toast.LENGTH_SHORT).show()
            }
        }

        binding.decrementQuantityButton.setOnClickListener {
            if (quantity > 0) {
                quantity--
                binding.quantityField.text = quantity.toString()
            }
        }

        binding.enterItemButton.setOnClickListener {
            val textEntered = binding.enterItemNameField.text.toString()
            viewModel.onEnterItemButtonClicked(textEntered, quantity)
            dismiss()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFragmentAddItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}