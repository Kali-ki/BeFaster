package com.kjnco.befaster.self_generated_suite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import com.kjnco.befaster.R

class RestitutionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_restitution, container, false)

        // Argument
        val arrow_list = arguments?.getIntegerArrayList("arrow_list")

        // Mapping the arrow images to their direction
        var arrow_direction = arrayListOf<String>()
        if (arrow_list != null) {
            for (arrow in arrow_list) {
                arrow_direction += when (arrow) {
                    R.drawable.arrow_up -> "Haut"
                    R.drawable.arrow_down -> "Bas"
                    R.drawable.arrow_left -> "Gauche"
                    R.drawable.arrow_right -> "Droite"
                    else -> ""
                }
            }
        }

        // Adding the list of directions to the view
        val restitutionList: ListView = view.findViewById(R.id.arrow_list) as ListView
        val adapter = context?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, arrow_direction) }

        if (adapter != null) {
            restitutionList.adapter = adapter
        }

        return view
    }

    companion object {
       private const val ARG_ARROW_LIST = "arrow_list"
        @JvmStatic
        fun newInstance(arrow_list: List<Int>): RestitutionFragment {
           val fragment = RestitutionFragment()
           val args = Bundle()
           args.putIntegerArrayList(ARG_ARROW_LIST, arrow_list as ArrayList<Int>)
           fragment.arguments = args
           return fragment
        }

    }
}