package com.leandro.contentproviderexamples.ui.contacts

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.leandro.contentproviderexamples.R
import com.leandro.contentproviderexamples.databinding.FragmentContactsBinding
import com.leandro.contentproviderexamples.domain.model.Contact
import com.leandro.contentproviderexamples.domain.model.ContactResult
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ContactsFragment : Fragment(), ContactAdapter.OnContactClick {
   lateinit var binding : FragmentContactsBinding
   private val viewModel : ContactViewModel by viewModels()
    private lateinit var adapter: ContactAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactsBinding.inflate(inflater, container, false)
        checkPermission()
        return binding.root
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                viewModel.getContacts()
            } else {
                Toast.makeText(requireActivity(), "Permission needed", Toast.LENGTH_SHORT).show()
            }
        }
    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
        } else {
            viewModel.getContacts()
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createLayoutManager()
        viewModel.contacts.observe(viewLifecycleOwner){
            if(it is ContactResult.Success) {
                createListContacts(it.contacts)
            } else {
                Toast.makeText(requireActivity(), (it as ContactResult.Error).message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createListContacts(it: List<Contact>?) {
        adapter = ContactAdapter(it!!, this)
        binding.recyclerContacts.adapter = adapter
    }

    private fun createLayoutManager() {
        binding.recyclerContacts.layoutManager = LinearLayoutManager(requireActivity())
    }

    override fun onClick(contact: Contact) {

    }


}