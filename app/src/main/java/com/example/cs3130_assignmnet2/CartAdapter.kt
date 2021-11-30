package com.example.cs3130_assignmnet2

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cs3130_assignmnet2.databinding.ProductItemCartBinding

class CartAdapter(private var items: ArrayList<Item>, val total: TextView) :
    RecyclerView.Adapter<CartAdapter.ItemCartViewHolder>() {
    class ItemCartViewHolder(val binding: ProductItemCartBinding) : RecyclerView.ViewHolder(binding.root)

    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCartViewHolder {
        context = parent.context
        return ItemCartViewHolder(
            ProductItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemCartViewHolder, position: Int) {
        val currentItem = items[position]
        holder.binding.apply {
            quantityText.text = currentItem.quantity.toString()
            nameText.text = currentItem.product.name.replaceFirstChar { it.uppercaseChar() }
            descriptionText.text =
                currentItem.product.description.replaceFirstChar { it.uppercaseChar() }
            skuText.text = currentItem.product.sku
            val price = "$" + String.format("%.2f", currentItem.product.price)
            priceText.text = price
            imageImage.setImageResource(
                context.resources.getIdentifier(
                    currentItem.product.image,
                    "drawable",
                    context.packageName
                )
            )
        }

        setTotal()

        holder.binding.addButton.setOnClickListener {
            currentItem.quantity++
            notifyItemChanged(position)
            setTotal()
        }

        holder.binding.removeButton.setOnClickListener {
            if (currentItem.quantity > 1) {
                currentItem.quantity--
                notifyItemChanged(position)
                setTotal()
            }
            else if (currentItem.quantity == 1) {
                items.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position,items.size)
                setTotal()
            }
        }

        holder.binding.deleteButton.setOnClickListener {
            items.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,items.size)
            setTotal()
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: ArrayList<Item>) {
        this.items = items
        notifyDataSetChanged()
    }

    private fun setTotal() {
        var value = 0.0

        for(currentItem in items) {
            value += currentItem.product.price * currentItem.quantity
        }

        total.text = String.format("%.2f", value)
    }

}