package com.akshay.evaluation7.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akshay.evaluation7.adapter.BooksReadAdapter
import com.akshay.evaluation7.database.BooksDatabase
import com.akshay.evaluation7.databinding.FragmentBooksDetailsBinding
import com.akshay.evaluation7.repository.BooksRepository
import com.akshay.evaluation7.viewmodel.BooksViewModel
import com.akshay.evaluation7.viewmodel.BooksViewModelFactory

class BooksDetailsFragment : Fragment() {
    private lateinit var binding: FragmentBooksDetailsBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BooksReadAdapter
    private lateinit var booksViewModel: BooksViewModel
    private lateinit var booksViewModelFactory: ViewModelProvider.Factory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = BooksDatabase.getDataBaseDetails(requireContext())
        val repository = BooksRepository(database.booksDao())
        booksViewModelFactory = BooksViewModelFactory(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBooksDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()

        booksViewModel.allReadBooksLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.displayUnreadBooksCount.text = "Total Books : ${it.size}"
        }

        adapter = BooksReadAdapter()
        recyclerView.adapter = adapter
    }
    private fun setUp(){
        booksViewModel = ViewModelProvider(this, booksViewModelFactory)[BooksViewModel::class.java]
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
    }

}