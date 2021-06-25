package br.com.giugiu.seriesfilter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.textfield.TextInputLayout

/** @author Giulia Soares
 */
class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		setupActionBarWithNavController(findNavController(R.id.host)) // a ActionBar será controlada pelo host
	}

	override fun onSupportNavigateUp() = findNavController(R.id.host).navigateUp() //o que acontece quando clica na seta
}