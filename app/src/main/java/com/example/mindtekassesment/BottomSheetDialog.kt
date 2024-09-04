package com.example.mindtekassesment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.ViewGroup as ViewGroup1

class BottomSheetDialog : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup1?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_bottom_sheet, container, false)

        // Use correct IDs
        val itemsCountText: TextView = view.findViewById(R.id.itemsCountTextView)
        val topCharactersText: TextView = view.findViewById(R.id.topCharactersTextView)

        // Retrieve arguments
        val itemsCount = arguments?.getInt("itemsCount") ?: 0
        val topCharacters = arguments?.getString("topCharacters") ?: ""

        // Set the text
        itemsCountText.text = "Items in list: $itemsCount"
        topCharactersText.text = "Top characters:\n$topCharacters"

        return view
    }
}
