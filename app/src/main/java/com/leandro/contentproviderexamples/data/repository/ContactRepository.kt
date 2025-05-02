package com.leandro.contentproviderexamples.data.repository

import android.content.ContentResolver
import android.provider.ContactsContract
import com.leandro.contentproviderexamples.domain.model.Contact
import javax.inject.Inject

class ContactRepository @Inject constructor(private val contentResolver: ContentResolver) : ContactRepositoryInterface {

    private val mProjections = arrayOf(
        ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER)

    override fun getContacts(): List<Contact> {
        val contacts = mutableListOf<Contact>()
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            mProjections,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + "ASC"
        )

        cursor?.use {
            val idIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
            val nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

            while (it.moveToNext()) {
                val id = it.getString(idIndex)
                val name = it.getString(nameIndex)
                val phoneNumber = it.getString(numberIndex)
                contacts.add(Contact(id, name, phoneNumber))
            }
        }

        return contacts
    }
}