package com.leandro.contentproviderexamples.data.repository

import com.leandro.contentproviderexamples.domain.model.Contact

interface ContactRepositoryInterface {
    fun getContacts() : List<Contact>
}