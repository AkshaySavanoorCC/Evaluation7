package com.akshay.evaluation7.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akshay.evaluation7.R
import com.akshay.evaluation7.VisitCountDataStore
import com.akshay.evaluation7.adapter.BooksAdapter
import com.akshay.evaluation7.database.BooksDatabase
import com.akshay.evaluation7.databinding.FragmentHomeBinding
import com.akshay.evaluation7.model.BooksEntity
import com.akshay.evaluation7.repository.BooksRepository
import com.akshay.evaluation7.viewmodel.BooksViewModel
import com.akshay.evaluation7.viewmodel.BooksViewModelFactory

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BooksAdapter
    private lateinit var booksViewModel: BooksViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = BooksDatabase.getDataBaseDetails(requireContext())
        val repository = BooksRepository(database.booksDao())
        val booksViewModelFactory = BooksViewModelFactory(repository)
        booksViewModel = ViewModelProvider(this, booksViewModelFactory)[BooksViewModel::class.java]

        setupRecyclerView()
        setupDataStore()
        setupUIListeners()
    }

    private fun setupRecyclerView() {
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = BooksAdapter(::onToggleChanged, ::onDelete)
        recyclerView.adapter = adapter
    }

    @SuppressLint("SetTextI18n")
    private fun setupDataStore() {
        val dataStore = VisitCountDataStore(requireContext())
        dataStore.preferenceFlow.asLiveData().observe(viewLifecycleOwner) {
            binding.displayUsersCount.text = "Number of visitors: ${it + 1}"
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupUIListeners() {
        binding.addBook.setOnClickListener {
            with(binding) {
                bookDetailPlaceHolder.visibility = View.VISIBLE
                addBook.visibility = View.GONE
                unReadBook.visibility = View.GONE
            }
        }

        binding.saveBook.setOnClickListener {
            val bookDetails = bookDetailsFromUI()
            booksViewModel.insertNewBook(bookDetails)
            clearBookDetailsUI()
        }

        binding.unReadBook.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_booksDetailsFragment)
        }

        booksViewModel.allBooksLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.displayBooksCount.text = "Total Books: ${it.size}"
        }
    }

    private fun onToggleChanged(originalBook: BooksEntity, isChecked: Boolean) {
        val updatedBook = BooksEntity(originalBook.bookId,originalBook.author,originalBook.title,isChecked)
        booksViewModel.updateToggleStatus(updatedBook)
    }

    private fun onDelete(book: BooksEntity) {
        booksViewModel.deleteBook(book)
    }

    private fun bookDetailsFromUI(): BooksEntity {
        return with(binding) {
            val bookTitle = bookTitle.text.toString()
            val bookAuthor = bookAuthor.text.toString()
            BooksEntity(0, bookTitle, bookAuthor, false)
        }
    }

    private fun clearBookDetailsUI() {
        with(binding) {
            bookTitle.text.clear()
            bookAuthor.text.clear()
            addBook.visibility = View.VISIBLE
            unReadBook.visibility = View.VISIBLE
            bookDetailPlaceHolder.visibility = View.GONE
        }
    }
}
