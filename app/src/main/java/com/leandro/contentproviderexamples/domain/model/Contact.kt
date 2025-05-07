package com.leandro.contentproviderexamples.domain.model

import com.leandro.contentproviderexamples.core.BaseAdapter


data class Contact(
    val id : String,
    val name : String,
    val phoneNumber : String
) : BaseAdapter.ListAdapterItem