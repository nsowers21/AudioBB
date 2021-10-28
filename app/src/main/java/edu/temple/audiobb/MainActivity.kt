package edu.temple.audiobb

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity() {

    private val viewModel: bookViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.setTwoPane(resources.configuration.orientation==Configuration.ORIENTATION_LANDSCAPE)
        //leaving blank to start
        viewModel.setSelectedBook(Book("",""))
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            supportFragmentManager.popBackStack()
            supportFragmentManager.beginTransaction().
            add(R.id.containerDet, viewModel.bookDetFrag)
                .commit()
        }else{
            supportFragmentManager.popBackStack()
        }
        if(savedInstanceState==null){
            supportFragmentManager.commit{
                add(R.id.fragmentContainerView,viewModel.booklistFrag)
            }
        }
    }
    //callbacks in reverse
    override fun onBackPressed() {
        super.onBackPressed()
        viewModel.setSelectedBook(Book("",""))
    }

}