package edu.temple.dicethrow

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView.Orientation


/*
Our DieThrow application has been refactored to move the dieRoll() logic
into the ViewModel instead of the Fragment.
Study the DieViewModel, ButtonFragment, and DieFragment classes to
see the changes.

Follow the requirements below to have this app function
in both portrait and landscape configurations.
The Activity layout files for both Portrait and Landscape are already provided
*/

class MainActivity : AppCompatActivity(), ButtonFragment.ButtonInterface {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dieViewModel = ViewModelProvider(this)[DieViewModel::class.java]

        /* TODO 1: Load fragment(s)
            - Show _only_ ButtonFragment if portrait
            - show _both_ fragments if Landscape
          */

        //boolean - true if landscape, false if not
        //if landscape
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){//if in landscape orientation
            supportFragmentManager.beginTransaction()
                .replace(R.id.buttonContainer, ButtonFragment())//replaces empty button container with the button fragment
                .replace(R.id.diceContainer, DieFragment())//replaces empty dice container with die fragment
                .commit()

            dieViewModel.rollDie()//rolls the die upon populating the containers
        }else{//if portrait
            supportFragmentManager.beginTransaction()
                .replace(R.id.buttonContainer, ButtonFragment())//replaces the empty container with the button
                .commit()
        }

    }

    /* TODO 2: switch fragments if die rolled and in portrait (no need to switch fragments if Landscape)
        */

    // This callback function gets invoked when the child Fragment invokes it
    // Remember to place Fragment transactions on BackStack so then can be reversed
    override fun buttonClicked() {
        //only replaces the button with the die if the device is in portrait orientation
        if(resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE){
            supportFragmentManager.beginTransaction()
                .replace(R.id.buttonContainer, DieFragment())
                .addToBackStack(null)//allows the user to return to the button if they press the back button
                .commit()
        }
    }


}