package edu.temple.audiobb

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class bookViewModel: ViewModel() {

    var listFrag = BookListFragment.newInstance(books)
    var detailsFrag = BookDetailsFragment()

    private val book: MutableLiveData<String> by lazy{
        MutableLiveData<String>()
    }

    fun getBook(): LiveData<String>{
        return book
    }

    fun setSelectedBook(selectedBook: String){
        this.book.value = selectedBook
    }
    val books = BookList().apply {
        add(Book("Count of Monte Cristo", "Alexandre Dumas"))
        add(Book("To Kill a Mockingbird", "Harper Lee"))
        add(Book("The Great Gatsby", "F.Scott Fitzgerld"))
        add(Book("The Call of the Wild", "Jack London"))
        add(Book("The lliad", "Homer"))
        add(Book("Charlie and the Chocolate Factory", "Roald Dahl"))
        add(Book("The Outsiders", "S.E.Hinton"))
        add(Book("The Godfather", "Mario Puzo"))
        add(Book("Ulysses", "James Joyce"))
        add(Book("Of Mice and Men", "John Steinbeck"))
        add(Book("The Shinning", "Stephen King"))
        add(Book("Lone Survivor", "Marcus Littrell"))
    }

    private val twoPane: MutableLiveData<Boolean>by lazy{
        MutableLiveData<Boolean>()
    }
    fun getTwoPane():LiveData<Boolean>{
        return twoPane
    }
    fun setTwoPane(pane: Boolean){
        twoPane.value=pane
    }


}