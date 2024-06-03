package com.example.trashure.ui.banksampah

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trashure.databinding.ItemTempatBinding

class BankSampahAdapter(private val items: List<BankSampahDataTempat>) :
    RecyclerView.Adapter<BankSampahAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTempatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private val binding: ItemTempatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BankSampahDataTempat) {
            binding.titleTextView.text = item.namaTempat
            binding.addressTextView.text = item.alamatLengkap
        }
    }
}