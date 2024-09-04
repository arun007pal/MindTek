package com.example.mindtekassesment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _allItems = MutableLiveData<List<ListItem>>()
    private val _filteredItems = MutableLiveData<List<ListItem>>()
    private val _pageItems = MutableLiveData<List<ListItem>>()

    val filteredItems: LiveData<List<ListItem>> get() = _filteredItems
    val pageItems: LiveData<List<ListItem>> get() = _pageItems

    private val itemsPerPage = 5  // Define items per page for easier management

    init {
        loadItems()
    }

    private fun loadItems() {
        viewModelScope.launch {
            // Simulate network delay
            delay(1000)

            // Load data (replace with actual data fetching logic)
            val loadedItems = listOf(
                ListItem("Apple", "Fruit", "https://w0.peakpx.com/wallpaper/241/723/HD-wallpaper-road-clouds-landscape-nature-sky.jpg"),
                ListItem("Banana", "Fruit", "https://w0.peakpx.com/wallpaper/991/436/HD-wallpaper-mountains-nature-sky-clouds-landscape.jpg"),
                ListItem("Orange", "Fruit", "https://w0.peakpx.com/wallpaper/428/266/HD-wallpaper-mountains-valley-trees-grass-landscape-green.jpg"),
                ListItem("Blueberry", "Fruit", "https://w0.peakpx.com/wallpaper/126/284/HD-wallpaper-clouds-summer-landscape-sky-humor-anime-summer-landscape.jpg"),
                ListItem("Apple", "Fruit", "https://w0.peakpx.com/wallpaper/241/723/HD-wallpaper-road-clouds-landscape-nature-sky.jpg"),
                ListItem("Banana", "Fruit", "https://w0.peakpx.com/wallpaper/991/436/HD-wallpaper-mountains-nature-sky-clouds-landscape.jpg"),
                ListItem("Orange", "Fruit", "https://w0.peakpx.com/wallpaper/428/266/HD-wallpaper-mountains-valley-trees-grass-landscape-green.jpg"),
                ListItem("Blueberry", "Fruit", "https://w0.peakpx.com/wallpaper/126/284/HD-wallpaper-clouds-summer-landscape-sky-humor-anime-summer-landscape.jpg"),
                ListItem("Apple", "Fruit", "https://w0.peakpx.com/wallpaper/241/723/HD-wallpaper-road-clouds-landscape-nature-sky.jpg"),
                ListItem("Banana", "Fruit", "https://w0.peakpx.com/wallpaper/991/436/HD-wallpaper-mountains-nature-sky-clouds-landscape.jpg"),
                ListItem("Orange", "Fruit", "https://w0.peakpx.com/wallpaper/428/266/HD-wallpaper-mountains-valley-trees-grass-landscape-green.jpg"),
                ListItem("Blueberry", "Fruit", "https://w0.peakpx.com/wallpaper/126/284/HD-wallpaper-clouds-summer-landscape-sky-humor-anime-summer-landscape.jpg"),
                ListItem("Apple", "Fruit", "https://w0.peakpx.com/wallpaper/241/723/HD-wallpaper-road-clouds-landscape-nature-sky.jpg"),
                ListItem("Banana", "Fruit", "https://w0.peakpx.com/wallpaper/991/436/HD-wallpaper-mountains-nature-sky-clouds-landscape.jpg"),
                ListItem("Orange", "Fruit", "https://w0.peakpx.com/wallpaper/428/266/HD-wallpaper-mountains-valley-trees-grass-landscape-green.jpg"),
                ListItem("Blueberry", "Fruit", "https://w0.peakpx.com/wallpaper/126/284/HD-wallpaper-clouds-summer-landscape-sky-humor-anime-summer-landscape.jpg"),
                ListItem("Apple", "Fruit", "https://w0.peakpx.com/wallpaper/241/723/HD-wallpaper-road-clouds-landscape-nature-sky.jpg"),
                ListItem("Banana", "Fruit", "https://w0.peakpx.com/wallpaper/991/436/HD-wallpaper-mountains-nature-sky-clouds-landscape.jpg"),
                ListItem("Orange", "Fruit", "https://w0.peakpx.com/wallpaper/428/266/HD-wallpaper-mountains-valley-trees-grass-landscape-green.jpg"),
                ListItem("Blueberry", "Fruit", "https://w0.peakpx.com/wallpaper/126/284/HD-wallpaper-clouds-summer-landscape-sky-humor-anime-summer-landscape.jpg"),
                ListItem("Apple", "Fruit", "https://w0.peakpx.com/wallpaper/241/723/HD-wallpaper-road-clouds-landscape-nature-sky.jpg"),
                ListItem("Banana", "Fruit", "https://w0.peakpx.com/wallpaper/991/436/HD-wallpaper-mountains-nature-sky-clouds-landscape.jpg"),
                ListItem("Orange", "Fruit", "https://w0.peakpx.com/wallpaper/428/266/HD-wallpaper-mountains-valley-trees-grass-landscape-green.jpg"),
                ListItem("Blueberry", "Fruit", "https://w0.peakpx.com/wallpaper/126/284/HD-wallpaper-clouds-summer-landscape-sky-humor-anime-summer-landscape.jpg"),
                ListItem("Blueberry", "Fruit", "https://w0.peakpx.com/wallpaper/126/284/HD-wallpaper-clouds-summer-landscape-sky-humor-anime-summer-landscape.jpg")
            )

            _allItems.value = loadedItems
            _filteredItems.value = loadedItems
            updatePageItems(0)
            _allItems.value = loadedItems
            _pageItems.value = loadedItems.take(10) // Assuming 5 items per page
        }
    }

    fun onPageChanged(pageIndex: Int) {
       // updatePageItems(pageIndex)
        _pageItems.value = _filteredItems.value?.drop(pageIndex * 5)?.take(5) // Assuming 5 items per page

    }

    private fun updatePageItems(pageIndex: Int) {
        _filteredItems.value?.let { items ->
            val startIndex = pageIndex * itemsPerPage
            val endIndex = (startIndex + itemsPerPage).coerceAtMost(items.size)
            _pageItems.value = items.subList(startIndex, endIndex)
        }
    }

    fun onSearchQueryChanged(query: String) {
        _allItems.value?.let { items ->
            val filtered = if (query.isBlank()) items else items.filter {
                it.title.contains(query, ignoreCase = true)
            }
            _filteredItems.value = filtered
            _pageItems.value = _filteredItems.value?.take(5)

          //  updatePageItems(0)  // Reset to the first page after filtering
        }
    }

    fun getStatistics(): Map<Char, Int> {
        val charFrequency = mutableMapOf<Char, Int>()
        _filteredItems.value?.forEach { item ->
            item.title.forEach { char ->
                if (char.isLetterOrDigit()) {  // Only count letters and digits
                    charFrequency[char] = charFrequency.getOrDefault(char, 0) + 1
                }
            }
        }
        // Return top 3 occurring characters
        return charFrequency.entries.sortedByDescending { it.value }.take(3).associate { it.key to it.value }
    }
}
