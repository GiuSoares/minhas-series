package br.com.giugiu.seriesfilter.frags

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import br.com.giugiu.seriesfilter.R
import br.com.giugiu.seriesfilter.SeriesAdapter
import br.com.giugiu.seriesfilter.dataBase.SeriesDBHelper
import br.com.giugiu.seriesfilter.databinding.FragmentListaSeriesBinding


/**
 * @author Giulia Soares
 */
class ListaSeriesFragment : Fragment(R.layout.fragment_lista_series) {
	private val db: SeriesDBHelper by lazy { SeriesDBHelper(requireContext()) }
	private val seriesAdapter: SeriesAdapter by lazy { SeriesAdapter(db.readAllSeries()!!, requireContext()) }

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setHasOptionsMenu(true)
		val bind = FragmentListaSeriesBinding.bind(view)

		bind.rvListaSeries.layoutManager = LinearLayoutManager(context)
		bind.rvListaSeries.adapter = seriesAdapter
		bind.rvListaSeries.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

		bind.floatingActionButton.setOnClickListener {
			findNavController().navigate(R.id.goes_Listar_to_Adicionar, bundleOf("visibility" to View.GONE)) }
	}

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) { //instancia a opção de busca do menu
		inflater.inflate(R.menu.search_menu, menu)

		val search = (menu.findItem(R.id.action_search).actionView as SearchView)
		search.imeOptions = EditorInfo.IME_ACTION_DONE //
		search.maxWidth = Int.MAX_VALUE      // passa limite do Inteiro fazendo com que ocupe todo o espaço

		search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
			override fun onQueryTextSubmit(query: String?): Boolean { // após apertar enter
				seriesAdapter.filter.filter(query)
				return false
			}

			override fun onQueryTextChange(newText: String?): Boolean { // enquanto está digitando
				seriesAdapter.filter.filter(search.query)
				return false
			}
		})
	}

	override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) { // mostra a opção selecionada
		R.id.action_search -> true
		else -> super.onOptionsItemSelected(item)
	}
}