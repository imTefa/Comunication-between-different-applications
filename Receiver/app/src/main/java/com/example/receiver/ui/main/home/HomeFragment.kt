package com.example.receiver.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.receiver.R
import com.example.receiver.bg.server.ServerService
import com.example.receiver.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnStartReceiving.setOnClickListener { startServerService() }
        binding.btnShowData.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_usersListFragment)
        }
    }

    private fun startServerService() {
        val intent = Intent(requireContext(), ServerService::class.java)
        ContextCompat.startForegroundService(requireContext(), intent)
    }


    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}