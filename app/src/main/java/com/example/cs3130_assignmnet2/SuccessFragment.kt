package com.example.cs3130_assignmnet2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.cs3130_assignmnet2.databinding.FragmentSuccessBinding


class SuccessFragment : Fragment() {
    private var fragmentSuccessBinding: FragmentSuccessBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSuccessBinding.inflate(inflater, container, false)
        fragmentSuccessBinding = binding

        binding.finishButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_successFragment_to_homeFragment)
        }

        return binding.root
    }

}