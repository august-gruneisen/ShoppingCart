package com.augustg.shoppingcart.startup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.augustg.shoppingcart.R
import com.augustg.shoppingcart.databinding.FragmentStartupBinding

class StartupFragment : Fragment(R.layout.fragment_startup) {

    private var _binding: FragmentStartupBinding? = null
    private val binding get() = _binding!!

    private fun initViews() {
        binding.startButton.setOnClickListener {
            findNavController().navigate(R.id.action_startupFragment_to_shoppingFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentStartupBinding.bind(view)
        initViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}