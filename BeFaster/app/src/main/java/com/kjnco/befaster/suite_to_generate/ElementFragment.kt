package com.kjnco.befaster.suite_to_generate

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.kjnco.befaster.R

class ElementFragment : Fragment() {

    private var arrow: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view = inflater.inflate(R.layout.fragment_element, container, false)

       arrow = arguments?.getString("arrow")!!

        // Arrow's image
        val arrowID = when(arrow) {
            "Haut" -> R.drawable.arrow_up
            "Bas" -> R.drawable.arrow_down
            "Gauche" -> R.drawable.arrow_left
            "Droite" -> R.drawable.arrow_right
            else -> R.drawable.transparent
        }

       val arrowView: ImageView = view.findViewById(R.id.arrow) as ImageView

        // Adding the sent arrow to the view
        arrowView.setImageResource(R.drawable.transparent)
        Handler(Looper.getMainLooper()).postDelayed({
            arrowView.setImageResource(arrowID)
        }, SuiteToGenerate.DELAY)

        // Showing the next arrow
        Handler(Looper.getMainLooper()).postDelayed({
            (activity as? ExtraArrowActivity)?.goToNextElement()
        }, SuiteToGenerate.DURATION)

        return view
    }

    companion object {
        private const val ARG_ARROW = "arrow"

        @JvmStatic
        fun newInstance(arrow: String) : ElementFragment {
            val fragment = ElementFragment()
            val args = Bundle()
            args.putString(ARG_ARROW, arrow)
            fragment.arguments = args
            return fragment
        }
    }
}