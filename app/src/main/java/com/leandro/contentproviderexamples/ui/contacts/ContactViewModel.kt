package com.leandro.contentproviderexamples.ui.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leandro.contentproviderexamples.data.repository.ContactRepository
import com.leandro.contentproviderexamples.domain.model.Contact
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(private val contactRepository: ContactRepository) : ViewModel(){

    private val _contacts = MutableLiveData<List<Contact>>()
    val contacts : LiveData<List<Contact>> = _contacts

    fun getContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            val contactsList = contactRepository.getContacts()
            _contacts.postValue(contactsList)
        }
    }
}