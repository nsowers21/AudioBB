package edu.temple.audiobb

import android.os.Bundle
import android.text.format.DateUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import kotlin.math.roundToInt


class ControllerFragment : Fragment() {
    lateinit var controllerPosition: TextView
    lateinit var controllerDuration: TextView
    lateinit var seekBar: SeekBar
    lateinit var rewindButton: ImageButton
    lateinit var playButton: ImageButton
    lateinit var pauseButton: ImageButton
    lateinit var ffButton: ImageView
    lateinit var stopButton: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_controller, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controllerPosition = view.findViewById(R.id.player_position)
        controllerDuration = view.findViewById(R.id.player_duration)
        seekBar = view.findViewById(R.id.seekBar)
        rewindButton = view.findViewById(R.id.rewindButton)
        playButton = view.findViewById(R.id.playButton)
        pauseButton = view.findViewById(R.id.pauseButton)
        ffButton = view.findViewById(R.id.ffButton)
        stopButton = view.findViewById(R.id.stopButton)
        val bookViewModel = ViewModelProvider(requireActivity()).get(bookViewModel::class.java)
        bookViewModel.getBook().observe(viewLifecycleOwner,{Book->
            controllerDuration.text=timeDuration(Book.duration)
            bookViewModel.getProgress().observe(viewLifecycleOwner, { Progress->
                val elapsed = ((Progress.progress.toDouble()/Book.duration.toDouble()) * 100f).roundToInt()
                seekBar.progress = elapsed
                controllerPosition.text = timeDuration(Progress.progress)
                controllerDuration.text = timeDuration(Book.duration)
            })
            seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if(fromUser){
                        val change = (progress.toDouble()/100f) * Book.duration
                        (activity as ControllerInterface).seek(change)
                    }//end of if
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    TODO("Not yet implemented")
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    TODO("Not yet implemented")
                }
            })
            //rewind button
            rewindButton.setOnClickListener{
                (activity as ControllerInterface).rewind()
            }//end of rewind button

            //play button
            playButton.setOnClickListener{
                playButton.visibility=View.GONE
                pauseButton.visibility=View.VISIBLE
                (activity as ControllerInterface).play(Book.id)
            }//end of play button

            //pause button
            pauseButton.setOnClickListener{
                pauseButton.visibility=View.GONE
                playButton.visibility=View.VISIBLE
                (activity as ControllerInterface).pause()
            }//end of pause button

            //stop button
            stopButton.setOnClickListener{
                (activity as ControllerInterface).stop()
            }//end of stop button

            //fast forward button
            ffButton.setOnClickListener{
                (activity as ControllerInterface).fastforward()
            }//end of fast forward button
        })
    }


    interface ControllerInterface{
        fun rewind()
        fun play(id:Int)
        fun pause()
        fun stop()
        fun fastforward()
        fun seek(position: Double)
    }//end of interface

    private fun timeDuration (elapsedTime:Int) : String{
        return DateUtils.formatElapsedTime(elapsedTime.toLong())
    }//end of timeDuration

}