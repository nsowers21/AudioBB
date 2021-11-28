package edu.temple.audiobb

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import edu.temple.audlibplayer.PlayerService


class MainActivity : AppCompatActivity() , BookListFragment.BookSelectedInterface, ControllerFragment.ControllerInterface{

    private lateinit var bookListFragment: BookListFragment
    private lateinit var controllerFragment: ControllerFragment
    var connected = false
    lateinit var bookViewModel: bookViewModel

   private val searchRequest = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
       supportFragmentManager.popBackStack()
       it.data?.run {
           bookListViewModel.copyBooks(getSerializableExtra(BookList.BOOKLIST_KEY)as BookList)
           bookListFragment.bookListUpdated()
       }
   }
    private val isSingleContainer : Boolean by lazy{
        findViewById<View>(R.id.container2) == null
    }

    private val selectedBookViewModel : bookViewModel by lazy {
        ViewModelProvider(this).get(bookViewModel::class.java)
    }

    private val bookListViewModel : BookList by lazy {
        ViewModelProvider(this).get(BookList::class.java)
    }
    companion object {
        const val BOOKLISTFRAGMENT_KEY = "BookListFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // If we're switching from one container to two containers
        // clear BookDetailsFragment from container1
        if(supportFragmentManager.findFragmentById(R.id.container1) is BookDetailsFragment
            && selectedBookViewModel.getBook().value!=null){
            supportFragmentManager.popBackStack()
        }

        if(savedInstanceState ==null){
            bookListFragment= BookListFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.container1,bookListFragment, BOOKLISTFRAGMENT_KEY)
                .commit()
        }else{
            bookListFragment=supportFragmentManager.findFragmentByTag(BOOKLISTFRAGMENT_KEY) as BookListFragment
            if(isSingleContainer&&selectedBookViewModel.getBook().value!=null){
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container1, BookDetailsFragment())
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit()
            }
        }

        if(!isSingleContainer&& supportFragmentManager.findFragmentById(R.id.container2) !is BookDetailsFragment)
            supportFragmentManager.beginTransaction()
                .add(R.id.container2,BookDetailsFragment())
                .commit()
        findViewById<ImageButton>(R.id.searchButton).setOnClickListener{
            searchRequest.launch(Intent(this, SearchActivity::class.java))
        }
        bindService(Intent(this, PlayerService::class.java)
            , serviceConnect
            , BIND_AUTO_CREATE
        )
    }//end of onCreate

    //callbacks in reverse
    override fun onBackPressed() {
        selectedBookViewModel.setSelectedBook(null)
        super.onBackPressed()
    }//end of onBackPressed

    override fun bookSelected(){
        if(isSingleContainer){
            supportFragmentManager.beginTransaction()
                .replace(R.id.container1,BookDetailsFragment())
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit()
        }
    }//end of selectedBook

    val controllerHandler = object:Handler(Looper.myLooper()!!){
        override fun handleMessage(msg: Message) {
            if(msg.obj!=null){
                bookViewModel.setProgess(msg.obj as PlayerService.BookProgress)
            }
        }
    }
    private lateinit var controlFragment: ControllerFragment
    lateinit var playerService: PlayerService.MediaControlBinder
    private val serviceConnect = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            connected = true
            playerService = service as PlayerService.MediaControlBinder
            playerService.setProgressHandler(controllerHandler)
            controlFragment = ControllerFragment()
            supportFragmentManager.commit {
                add(R.id.container3, controlFragment)
                hide(controlFragment)
            }
        }
        override fun onServiceDisconnected(name: ComponentName?) {
            connected=false
        }
    }


    override fun rewind() {
        if(playerService.isPlaying){
            bookViewModel.getProgress().value?.progress?.let { it1 -> playerService.seekTo(it1 - 10) }
        }
    }

    override fun play(id: Int) {
        playerService.play(id)
    }

    override fun pause() {
        playerService.pause()
    }

    override fun stop() {
        playerService.stop()
    }

    override fun fastforward() {
        if(playerService.isPlaying){
            bookViewModel.getProgress().value?.progress?.let { it1 -> playerService.seekTo(it1 + 10) }
        }
    }

    override fun seek(position: Double) {
        playerService.seekTo(position.toInt())
    }

}//end of class
