package com.leandro.contentproviderexamples.ui.contacts

import com.leandro.contentproviderexamples.R
import com.leandro.contentproviderexamples.core.BaseAdapter
import com.leandro.contentproviderexamples.databinding.ItemContactBinding
import com.leandro.contentproviderexamples.domain.model.Contact

class ContactAdapter(contacts : List<Contact>, private val onContactClick: OnContactClick) : BaseAdapter<ItemContactBinding, Contact>(ArrayList(contacts))  {
    override val layoutId: Int
        get() = R.layout.item_contact

    override fun bind(binding: ItemContactBinding, item: Contact) {
        binding.apply {
            executePendingBindings()
            contact = item
            binding.root.setOnClickListener { onContactClick.onClick(item) }
        }
    }

    interface OnContactClick{
        fun onClick(contact: Contact)
    }
}