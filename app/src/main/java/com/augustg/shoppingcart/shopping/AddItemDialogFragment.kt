package com.augustg.shoppingcart.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.navGraphViewModels
import com.augustg.shoppingcart.R
import com.augustg.shoppingcart.databinding.DialogFragmentAddItemBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddItemDialogFragment : BottomSheetDialogFragment() {

    private val viewModel by navGraphViewModels<ShoppingViewModel>(R.id.nav_graph_xml)

    private var _binding: DialogFragmentAddItemBinding? = null
    private val binding get() = _binding!!

    private fun initViews() {
        binding.enterItemButton.setOnClickListener {
            val textEntered = binding.enterItemNameField.text.toString()
            viewModel.onEnterItemButtonClicked(textEntered)
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