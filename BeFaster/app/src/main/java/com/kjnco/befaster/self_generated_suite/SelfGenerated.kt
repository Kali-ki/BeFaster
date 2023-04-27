package com.kjnco.befaster.self_generated_suite

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kjnco.befaster.R

class SelfGenerated : AppCompatActivity() {


    companion object {
        const val DURATION = 3000L
        const val DELAY = 1000L
        const val NUMBER_OF_ELEMENT = 3
        var currentIteration = 0
        const val NUMBER_OF_SEQUENCE = 3
        var currentSequence = 0
    }

    private val arrows = listOf(R.drawable.arrow_up, R.drawable.arrow_right, R.drawable.arrow_down, R.drawable.arrow_left)
    var listOfArrows = mutableListOf<Int>()

    init {

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_self_generated)

        val arrow = arrows.random()
        listOfArrows.add(arrow)
        val fragment = ElementFragment.newInstance(arrow)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragment)
            .commit()
    }

    /**
     * Method to go from a fragment to another
     */
    fun goToNextElement() {
        currentIteration++
        if (currentIteration < NUMBER_OF_ELEMENT) {
            val arrow = arrows.random()
            listOfArrows.add(arrow)
            val fragment = ElementFragment.newInstance(arrow)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }else {
            val intent = Intent(this, RestitutionActivity::class.java)
            val arrayOfArrows = ArrayList<Int>(listOfArrows)
            listOfArrows.clear()
            intent.putIntegerArrayListExtra("listOfArrows", arrayOfArrows)
            startActivity(intent)
        }
    }

}