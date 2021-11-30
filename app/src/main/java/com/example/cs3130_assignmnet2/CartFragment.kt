package com.example.cs3130_assignmnet2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cs3130_assignmnet2.databinding.FragmentCartBinding

class CartFragment : Fragment() {
    private var fragmentCartBinding: FragmentCartBinding? = null
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCartBinding.inflate(inflater, container, false)
        fragmentCartBinding = binding

        binding.cartToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.cartToolbar.setNavigationOnClickListener { Navigation.findNavController(it).navigateUp() }

        setFragmentResultListener("requestKey") {
                requestKey, bundle ->
            val allItems = bundle.get("bundleKey") as ArrayList<Item>
            val items = allItems.filter { it.quantity > 0 } as ArrayList<Item>
            cartAdapter = CartAdapter(items, binding.totalNumber)
            cartAdapter.setItems(items)
            binding.cartRecycler.adapter = cartAdapter
            binding.cartRecycler.layoutManager = LinearLayoutManager(activity)
        }

        binding.fab.setOnClickListener {
            if(binding.totalNumber.text.toString().toFloat() > 0) {
                Navigation.findNavController(it)
                    .navigate(R.id.action_cartFragment_to_successFragment)
            }
            else {
                Toast.makeText(context, "Cart is empty!", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

}