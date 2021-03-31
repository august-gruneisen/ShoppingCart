package com.augustg.shoppingcart.shopping

import com.augustg.shoppingcart.util.Formats.getAsCurrency
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.augustg.shoppingcart.databinding.ItemViewHolderBinding
import com.augustg.shoppingcart.items.Item

class ItemListAdapter : ListAdapter<Item, ItemListAdapter.ItemViewHolder>(DiffCallback()) {

    class ItemViewHolder(private val binding: ItemViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.itemName.text = item.name
            binding.itemPrice.text = getAsCurrency(item.price)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class DiffCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.name == newItem.name
    }
}

class SwipeToDelete(delete: (position: Int) -> Unit) :
    ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, LEFT or RIGHT) {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
            delete(viewHolder.adapterPosition)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            viewHolder1: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }
    })