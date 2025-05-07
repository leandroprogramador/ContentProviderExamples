package com.leandro.contentproviderexamples.domain.model

sealed class ContactResult {
    data class Success(val contacts: List<Contact>) : ContactResult()
    data class Error(val message: String) : ContactResult()
}