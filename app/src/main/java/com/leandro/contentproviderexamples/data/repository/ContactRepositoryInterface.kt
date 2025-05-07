package com.leandro.contentproviderexamples.data.repository

import com.leandro.contentproviderexamples.domain.model.Contact
import com.leandro.contentproviderexamples.domain.model.ContactResult

interface ContactRepositoryInterface {
    fun getContacts() : ContactResult
}