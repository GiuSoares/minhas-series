package br.com.giugiu.seriesfilter.frags

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import br.com.giugiu.seriesfilter.R
import br.com.giugiu.seriesfilter.databinding.FragmentMenuBinding

class MenuFragment : Fragment(R.layout.fragment_menu) {
	private val bind: FragmentMenuBinding by viewBinding() // inicializado pela função viewBinding

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState) //savedInstanceState salva o estado do objeto

		val navController = findNavController()
		bind.btAdicionar.setOnClickListener {
			navController.navigate(R.id.goes_Menu_to_Adicionar, bundleOf("visibility" to View.GONE)) }
		bind.btPesquisar.setOnClickListener { navController.navigate(R.id.goes_Menu_to_Listar) }
	}
}