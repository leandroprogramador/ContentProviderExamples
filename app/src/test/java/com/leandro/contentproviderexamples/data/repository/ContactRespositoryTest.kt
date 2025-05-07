package com.leandro.contentproviderexamples.data.repository

import android.content.ContentResolver
import com.leandro.contentproviderexamples.domain.model.ContactResult
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class ContactRespositoryTest {

    private lateinit var repository: ContactRepository
    lateinit var fakeContentResolver : ContentResolver

    @Before
    fun setup() {
        fakeContentResolver  = mock()
        repository = ContactRepository(fakeContentResolver)
    }

    @Test
    fun returnContactList(){
        val result = repository.getContacts()
        assertTrue(result is ContactResult.Success)
    }

    @Test
    fun returnErrorWhenSearchContacts() {
        val repository = object : ContactRepository(fakeContentResolver) {
            override fun getContacts(): ContactResult {
                return ContactResult.Error("Error to find contacts")
            }
        }
        val result = repository.getContacts()
        assertTrue(result is ContactResult.Error)
    }
}