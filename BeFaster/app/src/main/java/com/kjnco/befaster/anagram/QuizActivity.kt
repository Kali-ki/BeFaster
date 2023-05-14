package com.kjnco.befaster.anagram

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.kjnco.befaster.R
import com.kjnco.befaster.gen_know.Quiz
import com.kjnco.befaster.main_menu.TrainingActivity

class QuizActivity : AppCompatActivity() {
    companion object {
        var anagramList = mutableMapOf<Int, List<Int>>()
        val oddOneOutList = hashMapOf<Int, Int>()
    }

    init {
        // Init the anagramList
        anagramList[R.string.anagram_1] = listOf(R.string.anagram1_1, R.string.anagram1_2, R.string.anagram1_3)
        anagramList[R.string.anagram_2] = listOf(R.string.anagram2_1, R.string.anagram2_2, R.string.anagram2_3)
        anagramList[R.string.anagram_3] = listOf(R.string.anagram3_1, R.string.anagram3_2, R.string.anagram3_3)
        anagramList[R.string.anagram_4] = listOf(R.string.anagram4_1, R.string.anagram4_2, R.string.anagram4_3)

        // Init the oddOneOutList
        oddOneOutList[R.string.anagram_1] = R.string.anagram1_2
        oddOneOutList[R.string.anagram_2] = R.string.anagram2_2
        oddOneOutList[R.string.anagram_3] = R.string.anagram3_2
        oddOneOutList[R.string.anagram_4] = R.string.anagram4_2
    }

    init {
        // Shuffle the anagramList
        anagramList = anagramList.toList().shuffled().toMap().toMutableMap()

        // Shuffle the answer
        for((key, value) in anagramList) {
            val shuffledAnswerList = value.shuffled()
            anagramList[key] = shuffledAnswerList
        }
    }

    private var currentQuestionIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz2)

        // Setting up fragment manager and fragment transaction
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        // Assign the question and the answer to the fragment
        val anagram: Int = anagramList.keys.elementAt(0)
        val answers: List<Int> = anagramList.values.elementAt(0)
        val anagramFragment = AnagramFragment.newInstance(anagram, answers[0], answers[1], answers[2], oddOneOutList[anagram]!!)
        fragmentTransaction.add(R.id.fragment_container, anagramFragment)
        fragmentTransaction.commit()
    }

    /**
     * Function to change the question
     */
    fun setTheNextQuestion() {
        currentQuestionIndex++
        if (currentQuestionIndex < AnagramActivity.numberOfAnagrams) {
            val anagram: Int = anagramList.keys.elementAt(currentQuestionIndex)
            val answers: List<Int> = anagramList.values.elementAt(currentQuestionIndex)
            val anagramFragment = AnagramFragment.newInstance(anagram, answers[0], answers[1], answers[2], oddOneOutList[anagram]!!)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, anagramFragment)
                .commit()
        }else {
            val intent = Intent(this, TrainingActivity::class.java)
            startActivity(intent)
        }
    }
    /**
     * Empty function to avoid the error
     */
    fun onRadioButtonClicked(view: View) {

    }
}