package com.example.mindtekassesment

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.widget.SearchView
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var carouselAdapter: CarouselAdapter
    private lateinit var listAdapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.fab)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)


        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        carouselAdapter = CarouselAdapter(emptyList()) { pageIndex ->
            viewModel.onPageChanged(pageIndex)
        }
        viewPager.adapter = carouselAdapter

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        listAdapter = ListAdapter(emptyList())
        recyclerView.adapter = listAdapter


        viewModel.pageItems.observe(this) { items ->
            listAdapter.updateItems(items)
        }

        viewModel.filteredItems.observe(this) { items ->
            carouselAdapter.updateItems(items)
        }

        // Set up SearchView
        val searchView: SearchView = findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.onSearchQueryChanged(newText ?: "")
                return true
            }
        })

        // Set up Floating Action Button for Bottom Sheet Dialog
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            showBottomSheetDialog()
        }
    }

    private fun showBottomSheetDialog() {
        val dialog = BottomSheetDialog(this)
        val dialogView: View = LayoutInflater.from(this).inflate(R.layout.dialog_bottom_sheet, null)

        val statistics = viewModel.getStatistics()
        val topCharacters = statistics.entries.joinToString("\n") { "${it.key} = ${it.value}" }

        dialogView.findViewById<TextView>(R.id.itemsCountTextView).text = "Items Count: ${viewModel.filteredItems.value?.size ?: 0}"
        dialogView.findViewById<TextView>(R.id.topCharactersTextView).text = "Top Characters:\n$topCharacters"

        dialog.setContentView(dialogView)
        dialog.show()
    }
}
