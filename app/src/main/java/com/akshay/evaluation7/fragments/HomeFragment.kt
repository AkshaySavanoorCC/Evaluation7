package com.akshay.evaluation7.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akshay.evaluation7.R
import com.akshay.evaluation7.VisitCountDataStore
import com.akshay.evaluation7.adapter.BooksAdapter
import com.akshay.evaluation7.database.BooksDatabase.Companion.getDataBaseDetails
import com.akshay.evaluation7.databinding.FragmentHomeBinding
import com.akshay.evaluation7.model.BooksEntity
import com.akshay.evaluation7.repository.BooksRepository
import com.akshay.evaluation7.viewmodel.BooksViewModel
import com.akshay.evaluation7.viewmodel.BooksViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BooksAdapter
    private lateinit var booksViewModel: BooksViewModel
    private lateinit var booksViewModelFactory: ViewModelProvider.Factory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = getDataBaseDetails(requireContext())
        val repository = BooksRepository(database.booksDao())
        booksViewModelFactory = BooksViewModelFactory(repository)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        booksViewModel =
            ViewModelProvider(this, booksViewModelFactory)[BooksViewModel::class.java]

        recyclerView = binding.recyclerView
        recyclerView.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        var activeUsers = 0
        val dataStore = VisitCountDataStore(requireContext())
        dataStore.preferenceFlow.asLiveData().observe(viewLifecycleOwner) {
            binding.displayUsersCount.text = "Number of visitors : ${it+1}"
        }

        val onToggleChanged: (BooksEntity,Boolean) -> Unit = { originalBook,isChecked ->

            val updatedBook = BooksEntity(originalBook.bookId,originalBook.title, originalBook.author, isChecked)
            booksViewModel.updateToggleStatus(updatedBook)
        }

        val onDelete : (BooksEntity) -> Unit = {
            booksViewModel.deleteBook(it)
        }



        binding.addBook.setOnClickListener {
            binding.apply {
                bookDetailPlaceHolder.visibility = View.VISIBLE
                addBook.visibility = View.GONE
                unReadBook.visibility = View.GONE
            }

        }

        fun bookDetails(): BooksEntity {
            val bookTitle = binding.bookTitle.text.toString()
            val bookAuthor = binding.bookAuthor.text.toString()
            return BooksEntity(0,bookTitle, bookAuthor, false)
        }
       binding.saveBook.setOnClickListener {
           booksViewModel.insertNewBook(bookDetails())
           binding.apply {
               bookTitle.text.clear()
               bookAuthor.text.clear()
               addBook.visibility = View.VISIBLE
               unReadBook.visibility = View.VISIBLE
               bookDetailPlaceHolder.visibility = View.GONE
           }
       }
binding.unReadBook.setOnClickListener {
    findNavController().navigate(R.id.action_homeFragment_to_booksDetailsFragment)
}

        booksViewModel.allBooksLiveData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            binding.displayBooksCount.text =  "Total Books : ${it.size.toString()}"
        })
        adapter = BooksAdapter(onToggleChanged,onDelete)
        recyclerView.adapter = adapter
    }
}