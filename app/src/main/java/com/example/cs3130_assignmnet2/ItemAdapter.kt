package com.example.cs3130_assignmnet2

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.cs3130_assignmnet2.databinding.ProductItemBinding

class ItemAdapter(private val allItems: ArrayList<Item>)
    : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    class ItemViewHolder(val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root)

    private lateinit var context: Context
    private var items : ArrayList<Item> = ArrayList(allItems)
    private var sort = R.id.default_sort
    private var filter = R.id.all


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        context = parent.context
        return ItemViewHolder(
            ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = items[position]
        holder.binding.apply {
            quantityText.text = currentItem.quantity.toString()
            nameText.text = currentItem.product.name.replaceFirstChar { it.uppercaseChar() }
            descriptionText.text = currentItem.product.description.replaceFirstChar { it.uppercaseChar() }
            skuText.text = currentItem.product.sku
            val price = "$" + String.format("%.2f", currentItem.product.price)
            priceText.text = price
            imageImage.setImageResource(context.resources.getIdentifier(currentItem.product.image,"drawable", context.packageName))
        }

        holder.binding.addButton.setOnClickListener {
            currentItem.quantity++
            notifyItemChanged(position)
        }

        holder.binding.removeButton.setOnClickListener {
            if(currentItem.quantity > 0) {
                currentItem.quantity--
                notifyItemChanged(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItems(): ArrayList<Item> {
        return allItems
    }

    fun sort(high: Boolean) {
        sort = if(high) {
            items.sortByDescending { it.product.price }
            R.id.high
        } else {
            items.sortBy {it.product.price }
            R.id.low
        }
        notifyDataSetChanged()
    }

    fun reset() {
        items = ArrayList(allItems)
        sort = R.id.default_sort
        filter(filter)
        notifyDataSetChanged()
    }

    fun filter(id: Int) {
        when(id) {
            R.id.all -> {
                items = ArrayList(allItems)
                filter = R.id.all
            }
            R.id.beverage -> {
                items = ArrayList(allItems).filter { it.product::class.simpleName == "Beverage" } as ArrayList<Item>
                filter = R.id.beverage
            }
            R.id.canned -> {
                items = ArrayList(allItems).filter { it.product::class.simpleName == "Canned" } as ArrayList<Item>
                filter = R.id.canned
            }
            R.id.condiment -> {
                items = ArrayList(allItems).filter { it.product::class.simpleName == "Condiment" } as ArrayList<Item>
                filter = R.id.condiment
            }
            R.id.grocery -> {
                items = ArrayList(allItems).filter { it.product::class.simpleName == "Grocery" } as ArrayList<Item>
                filter = R.id.grocery
            }
            R.id.snack -> {
                items = ArrayList(allItems).filter { it.product::class.simpleName == "Snack" } as ArrayList<Item>
                filter = R.id.snack
            }
        }

        when(sort) {
            R.id.high -> sort(true)
            R.id.low -> sort(false)
        }

        notifyDataSetChanged()
    }

}