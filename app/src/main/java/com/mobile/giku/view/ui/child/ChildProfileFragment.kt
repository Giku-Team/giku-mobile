package com.mobile.giku.view.ui.child

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.giku.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.mobile.giku.databinding.FragmentChildProfileBinding
import com.mobile.giku.model.remote.child.ChildData
import com.mobile.giku.view.adapter.child.ChildClickListener
import com.mobile.giku.view.adapter.child.ChildProfileAdapter
import com.mobile.giku.viewmodel.child.ChildProfileViewModel

class ChildProfileFragment : Fragment(), ChildClickListener {

    private var _binding: FragmentChildProfileBinding? = null
    private val binding get() = _binding!!

    private val childProfileViewModel: ChildProfileViewModel by viewModel()

    private lateinit var adapter: ChildProfileAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChildProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        observeChildrenData()

        binding.btnAddYourChild.setOnClickListener {
            findNavController().navigate(R.id.action_childProfileFragment_to_addChildProfileFragment)
        }
    }

    private fun setupRecyclerView() {
        adapter = ChildProfileAdapter(this)
        binding.rvChildProfile.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ChildProfileFragment.adapter
        }
    }

    private fun observeChildrenData() {
        childProfileViewModel.childrenData.observe(viewLifecycleOwner) { response ->
            response?.let {
                adapter.submitList(it.data)
            }
        }
    }

    override fun onChildClick(childData: ChildData) {
        /*findNavController().navigate(
            R.id.action_childProfileFragment_to_childDetailsFragment,
            Bundle().apply { putParcelable("childData", childData) }
        )*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
