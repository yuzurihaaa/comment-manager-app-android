package com.yuzuriha.jetpack.tryout.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.yuzuriha.jetpack.tryout.databinding.CommentsFragmentBinding
import com.yuzuriha.jetpack.tryout.utilities.adapters.CommentAdapter
import com.yuzuriha.jetpack.tryout.viewmodel.AppViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CommentsFragment : Fragment() {

    private val viewModel: AppViewModel by sharedViewModel()

    private lateinit var binding: CommentsFragmentBinding

    private val args: CommentsFragmentArgs by navArgs()

    private val commentAdapter by lazy { CommentAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getComments(args.postItem?.id ?: 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CommentsFragmentBinding.inflate(inflater)
        binding.postItem = args.postItem
        bindListener()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        with(binding.commentList) {
            adapter = commentAdapter
            val decoration = androidx.recyclerview.widget.DividerItemDecoration(
                context,
                androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
            )
            addItemDecoration(decoration)
        }

        binding.filterInput.addTextChangedListener {
            viewModel.filterComment(it?.toString())
        }

    }

    private fun bindListener() {
        viewModel.comments.observe(viewLifecycleOwner, Observer(commentAdapter::submitList))
    }

}