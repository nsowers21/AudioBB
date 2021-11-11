package edu.temple.audiobb

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class BookDetailsFragment : Fragment() {

    private val viewModel: bookViewModel by activityViewModels()
    private lateinit var coverOfBook:ImageView
    private lateinit var titleOfBook:TextView
    private lateinit var authorOfBook:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_book_details, container, false)
        //Get the view from the layout to wire up
        coverOfBook = view.findViewById<ImageView>(R.id.bookCover)
        authorOfBook= view.findViewById<TextView>(R.id.authDet)
        titleOfBook = view.findViewById<TextView>(R.id.titleDet)

        return view
        }//end of view

    //Once the view is created this allows for the book to be changed by calling the changeBook function
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getBook().observe(requireActivity(), {
            changeBook(it)
        })
    }//end of onViewCreated

    //Changes the book cover, title, and author everytime a new one is selected
    private fun changeBook(book: Book?){
        book?.run {
            Picasso.get().load(coverURL).into(coverOfBook)
            Picasso.get().load(coverURL).into(coverOfBook)
            titleOfBook.text=title
        }
    }//end of change book

}//end of BookDetailFragment

