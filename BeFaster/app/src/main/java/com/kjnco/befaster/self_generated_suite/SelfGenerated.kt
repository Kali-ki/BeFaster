package com.kjnco.befaster.self_generated_suite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kjnco.befaster.R
import com.kjnco.befaster.main_menu.TrainingActivity

class SelfGenerated : AppCompatActivity() {


    companion object {
        const val DURATION = 3000L
        const val DELAY = 1000L
        const val numberOfIteration = 6
        var currentIteration = 0
    }

    val arrows = listOf(R.drawable.arrow_up, R.drawable.arrow_right, R.drawable.arrow_down, R.drawable.arrow_left)
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
        if (currentIteration < numberOfIteration) {
            val arrow = arrows.random()
            listOfArrows.add(arrow)
            val fragment = ElementFragment.newInstance(arrow)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, RestitutionFragment.newInstance(listOfArrows))
                .commit()
        }
    }
}