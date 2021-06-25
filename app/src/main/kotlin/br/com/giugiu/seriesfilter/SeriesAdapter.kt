package br.com.giugiu.seriesfilter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView

import br.com.giugiu.seriesfilter.SeriesAdapter.SeriesViewHolder
import br.com.giugiu.seriesfilter.dataBase.SeriesModel
import br.com.giugiu.seriesfilter.databinding.ViewItemSeriesBinding
import br.com.giugiu.seriesfilter.widget.DetalhesSeriesDialog

class SeriesAdapter(var listaGeral: List<SeriesModel>, val context: Context) : RecyclerView.Adapter<SeriesViewHolder>(), Filterable {
	var listaFiltrada = ArrayList<SeriesModel?>(listaGeral) // lista de controle para exibição

	inner class SeriesViewHolder(private val viewSB: ViewItemSeriesBinding) : RecyclerView.ViewHolder(viewSB.root) {
		fun bind(position: Int) {
			viewSB.tvName.text = listaFiltrada[position]?.nome
			viewSB.tvServico.text = listaFiltrada[position]?.servico
			viewSB.tvGenero.text = listaFiltrada[position]?.genero
			viewSB.root.setOnClickListener{
				DetalhesSeriesDialog(context, listaFiltrada[position]!!).show() }
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
			SeriesViewHolder(ViewItemSeriesBinding.inflate(LayoutInflater.from(parent.context), parent, false))

	override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) = holder.bind(position)

	override fun getItemCount() = listaFiltrada.size

	@ExperimentalStdlibApi
	override fun getFilter() = object : Filter() {
		override fun performFiltering(charSequence: CharSequence): FilterResults { // manipula a string de pesquisa
			val buscaTxt = charSequence.toString()
			var filteredList: ArrayList<SeriesModel?> = ArrayList()
			if (buscaTxt.isEmpty()) { // se texto vazio, volta a ser a lista completa
				filteredList = ArrayList<SeriesModel?>(listaGeral)
			} else {
				for (item in listaGeral) {
					// lógica de filtragem, testando em cada item
					if (item.nome.lowercase().contains(buscaTxt.lowercase())) // testa o nome do item
						filteredList.add(item)
				}
			}

			return (FilterResults().also { it.values = filteredList })
		}

		@Suppress("UNCHECKED_CAST")
		override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
			listaFiltrada = filterResults.values as ArrayList<SeriesModel?>
			notifyDataSetChanged() // notifica que dados foram atualizados ( lista filtrada )
		}
	}
}