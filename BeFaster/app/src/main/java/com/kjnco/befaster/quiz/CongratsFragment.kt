package com.kjnco.befaster.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kjnco.befaster.R

class CongratsFragment: Fragment(R.layout.inter_question_page) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.inter_question_page, container, false)
        return view
    }
}
