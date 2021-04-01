package com.augustg.shoppingcart.shopping

import android.os.Bundle
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.navigation.navGraphViewModels
import com.augustg.shoppingcart.R
import com.augustg.shoppingcart.databinding.DialogFragmentAddItemBinding
import com.augustg.shoppingcart.items.Store
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddItemDialogFragment : BottomSheetDialogFragment() {

    private val viewModel by navGraphViewModels<ShoppingViewModel>(R.id.nav_graph_xml)

    private var _binding: DialogFragmentAddItemBinding? = null
    private val binding get() = _binding!!

    var quantity = 1 // TODO: move to view model

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
            if (quantity > 1) {
                quantity--
                binding.quantityField.text = quantity.toString()
            }
        }

        binding.enterItemButton.setOnClickListener {
            val textEntered = binding.enterItemNameField.text.toString()
            if (viewModel.onEnterItemButtonClicked(textEntered, quantity)) {
                dismiss()
            } else {
                Toast.makeText(requireContext(), "Item not found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initCamera() {
        ProcessCameraProvider.getInstance(requireContext()).let { cameraProviderFuture ->
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()

                val preview: Preview = Preview.Builder()
                    .build()

                val cameraSelector: CameraSelector = CameraSelector.Builder()
                    .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                    .build()

                val imageAnalysis = ImageAnalysis.Builder()
                    .setTargetResolution(Size(1280, 720))
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()

                imageAnalysis.setAnalyzer(
                    ContextCompat.getMainExecutor(requireContext()),
                    QRAnalyzer(this::onDetectedQRCode)
                )

                preview.setSurfaceProvider(binding.cameraPreview.surfaceProvider)

                cameraProvider.bindToLifecycle(
                    viewLifecycleOwner,
                    cameraSelector,
                    imageAnalysis,
                    preview
                )

            }, ContextCompat.getMainExecutor(requireContext()))
        }
    }

    private fun onDetectedQRCode(itemId: String) {
        if (view != null) binding.enterItemNameField.setText(itemId)
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
        initCamera()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}