package edu.temple.audiobb


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.temple.audlibplayer.PlayerService

class bookViewModel: ViewModel() {

    private val book: MutableLiveData<Book> by lazy {
        MutableLiveData()
    }

    fun getBook(): LiveData<Book> {
        return book
    }

    fun setSelectedBook(item: Book?) {
        this.book.value = item
    }
    private val progress: MutableLiveData<PlayerService.BookProgress> by lazy {
        MutableLiveData()
    }

    fun getProgress(): LiveData<PlayerService.BookProgress> {
        return progress
    }

    fun setProgess(bookProgress: PlayerService.BookProgress) {
        this.progress.value = bookProgress
    }
}

/*
    val books = BookList()
        .apply {
        add(Book("Count of Monte Cristo", "Alexandre Dumas"))
        add(Book("To Kill a Mockingbird", "Harper Lee"))
        add(Book("The Great Gatsby", "F.Scott Fitzgerld"))
        add(Book("The Call of the Wild", "Jack London"))
        add(Book("The lliad", "Homer"))
        add(Book("Charlie's Chocolate Factory", "Roald Dahl"))
        add(Book("The Outsiders", "S.E.Hinton"))
        add(Book("The Godfather", "Mario Puzo"))
        add(Book("Ulysses", "James Joyce"))
        add(Book("Of Mice and Men", "John Steinbeck"))
        add(Book("The Shinning", "Stephen King"))
        add(Book("Lone Survivor", "Marcus Littrell"))
    }
    */


