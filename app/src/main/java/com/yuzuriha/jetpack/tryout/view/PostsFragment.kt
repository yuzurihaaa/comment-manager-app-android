package com.yuzuriha.jetpack.tryout.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.yuzuriha.jetpack.tryout.R
import com.yuzuriha.jetpack.tryout.databinding.PostsFragmentBinding
import com.yuzuriha.jetpack.tryout.utilities.Status
import com.yuzuriha.jetpack.tryout.utilities.adapters.PostAdapter
import com.yuzuriha.jetpack.tryout.viewmodel.AppViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PostsFragment : Fragment() {

    private val viewModel: AppViewModel by sharedViewModel()
    private lateinit var binding: PostsFragmentBinding
    private val postAdapter by lazy { PostAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PostsFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        bindListener()
    }

    private fun initView() {
        with(binding.postList) {
            adapter = postAdapter
            val decoration = DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
            addItemDecoration(decoration)
        }
    }

    private fun bindListener() {
        viewModel.postState.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let(postAdapter::submitList)
                }
                Status.ERROR -> {
                    AlertDialog.Builder(requireContext())
                        .setTitle(getString(R.string.error_fetching))
                        .setMessage(getString(R.string.please_check))
                        .setPositiveButton(android.R.string.yes) { dialog, _ -> dialog.cancel() }
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show()
                }
                Status.LOADING -> {
                }
            }
        })
    }
}