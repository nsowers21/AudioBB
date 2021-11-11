package edu.temple.audiobb

import java.io.Serializable

object BookList :Serializable{
    private val bookList : MutableList<Book> by lazy {
        ArrayList()
    }
    fun addBooks(library: List<Book>){
        for(book in library){
            addBook(book)
        }
    }

    fun addBook(book: Book) {
        bookList.add(book)
    }

    fun clearBooks(){
        bookList.clear()
    }

    operator fun get(index: Int): Book {
        return bookList[index]
    }

    fun size(): Int {
        return bookList.size
    }




}