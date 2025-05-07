package com.leandro.contentproviderexamples.ui.contact

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.leandro.contentproviderexamples.data.repository.ContactRepository
import com.leandro.contentproviderexamples.domain.model.Contact
import com.leandro.contentproviderexamples.domain.model.ContactResult
import com.leandro.contentproviderexamples.ui.contacts.ContactViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

@OptIn(ExperimentalCoroutinesApi::class)
class ContactViewModelTest {

    @get:Rule val mockitoRule : MockitoRule = MockitoJUnit.rule()
    @get:Rule val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel : ContactViewModel
    @Mock lateinit var repository: ContactRepository
    @Mock lateinit var observer: Observer<ContactResult>

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ContactViewModel(repository)
        viewModel.contacts.observeForever(observer)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        viewModel.contacts.removeObserver(observer)
    }

    @Test
    fun mustReturnContactsList() = runTest {
        val fakeContacts = ContactResult.Success(listOf(
            Contact("1", "Manuzinha", "11973313127"),
            Contact("2", "Guel", "11964422361"),
        ))

        `when`(repository.getContacts()).thenReturn(fakeContacts)

        viewModel.getContacts()
        testDispatcher.scheduler.advanceUntilIdle()

        verify(observer).onChanged(fakeContacts)
    }

    @Test
    fun mustReturnError() = runTest {
        val fakeError = ContactResult.Error("Error to find contacts")
        `when`(repository.getContacts()).thenReturn(fakeError)
        viewModel.getContacts()
        testDispatcher.scheduler.advanceUntilIdle()
        verify(observer).onChanged(fakeError)
    }

}