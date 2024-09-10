package com.example.scrolling

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, FragmentA())
            .commit()
    }
}

class FragmentA : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                LazyColumn {
                    items(List(50) { it }) { index ->
                        Button(
                            onClick = {
                                Log.d("ScrollingFocusExample", "Moving to B")
                                requireActivity().supportFragmentManager.beginTransaction()
                                    .replace(R.id.fragmentContainer, FragmentB())
                                    .addToBackStack("b")
                                    .commit()
                            },
                            modifier = Modifier
                                .onFocusChanged {
                                    if (it.hasFocus) {
                                        Log.d("ScrollingFocusExample", "Button $index has focus")
                                    }
                                }
                        ) {
                            Text(text = "Button $index")
                        }
                    }
                }
            }
        }
    }
}

class FragmentB : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Text(text = "Fragment B")
            }
        }
    }
}
