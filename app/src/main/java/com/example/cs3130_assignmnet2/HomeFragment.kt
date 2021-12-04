package com.example.cs3130_assignmnet2

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cs3130_assignmnet2.FirebaseUtils.firebaseAuth
import com.google.gson.Gson

import com.example.cs3130_assignmnet2.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var fragmentHomeBinding: FragmentHomeBinding? = null
    private val gson = Gson()
    private lateinit var itemAdapter: ItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        fragmentHomeBinding = binding

        binding.myToolbar.inflateMenu(R.menu.home_action_bar)
        binding.myToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.myToolbar.setNavigationOnClickListener {
            firebaseAuth.signOut()
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_logInFragment)
        }

        binding.myToolbar.setOnMenuItemClickListener{
            when(it.itemId) {
                R.id.default_sort -> {
                    itemAdapter.reset()
                    it.isChecked = true
                    true
                }
                R.id.high -> {
                    itemAdapter.sort(true)
                    it.isChecked = true
                    true
                }
                R.id.low -> {
                    itemAdapter.sort(false)
                    it.isChecked = true
                    true
                }
                R.id.all -> {
                    itemAdapter.filter(R.id.all)
                    it.isChecked = true
                    true
                }
                R.id.beverage -> {
                    itemAdapter.filter(R.id.beverage)
                    it.isChecked = true
                    true
                }
                R.id.canned -> {
                    itemAdapter.filter(R.id.canned)
                    it.isChecked = true
                    true
                }
                R.id.condiment -> {
                    itemAdapter.filter(R.id.condiment)
                    it.isChecked = true
                    true
                }
                R.id.grocery -> {
                    itemAdapter.filter(R.id.grocery)
                    it.isChecked = true
                    true
                }
                R.id.snack -> {
                    itemAdapter.filter(R.id.snack)
                    it.isChecked = true
                    true
                }
                else -> false
            }
        }

        itemAdapter = ItemAdapter(createItemList())

        binding.itemsRecycler.adapter = itemAdapter
        binding.itemsRecycler.layoutManager = LinearLayoutManager(activity)

        binding.floatingActionButton.setOnClickListener {
            setFragmentResult("requestKey", bundleOf("bundleKey" to itemAdapter.getItems()))
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_cartFragment)
        }

        return binding.root
    }

    private fun readProductsFromJson(): Array<Product> {
        val mutableList : MutableList<Product> = mutableListOf()

        mutableList.addAll(gson.fromJson(this::class.java.getResource("/res/raw/snacks.json").readText(), Array<Snack>::class.java))
        mutableList.addAll(gson.fromJson(this::class.java.getResource("/res/raw/condiments.json").readText(), Array<Condiment>::class.java))
        mutableList.addAll(gson.fromJson(this::class.java.getResource("/res/raw/grocery.json").readText(), Array<Grocery>::class.java))
        mutableList.addAll(gson.fromJson(this::class.java.getResource("/res/raw/canned.json").readText(), Array<Canned>::class.java))
        mutableList.addAll(gson.fromJson(this::class.java.getResource("/res/raw/beverages.json").readText(), Array<Beverage>::class.java))

        return mutableList.toTypedArray()
    }

    private fun createItemList(): ArrayList<Item> {
        val products = readProductsFromJson()
        val items = arrayListOf<Item>()

        for(product in products) {
            items.add(Item(product, 0))
        }

        return items
    }

}