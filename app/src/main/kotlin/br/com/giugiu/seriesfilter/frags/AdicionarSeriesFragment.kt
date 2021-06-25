package br.com.giugiu.seriesfilter.frags

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import br.com.giugiu.seriesfilter.R
import br.com.giugiu.seriesfilter.dataBase.SeriesDBHelper
import br.com.giugiu.seriesfilter.dataBase.SeriesModel
import br.com.giugiu.seriesfilter.databinding.DialogSeriesInfoBinding

class AdicionarSeriesFragment : Fragment(R.layout.dialog_series_info) {
	private val bind: DialogSeriesInfoBinding by viewBinding()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val db = SeriesDBHelper(requireContext())

		bind.btSalvar.setOnClickListener {
			val seriesModel = validateSeries()

			if (seriesModel != null) {
				if (db.insertSerie(seriesModel)) {
					Toast.makeText(activity, getString(R.string.toast_saved), Toast.LENGTH_LONG).show()
					findNavController().navigateUp() //retorna tela anterior
				}
			}
		}

		bind.btExcluir.visibility = arguments?.getInt("visibility")!!
	}

	private fun validateSeries(): SeriesModel? {
		val name = bind.etNome.text.toString()
		val servico = bind.etServico.text.toString()
		val ano = bind.etAno.text.toString()
		val temporada = bind.etTemporadas.text.toString()
		val epsiodio = bind.etEpisodios.text.toString()
		val genero = bind.etGenero.text.toString()
		when {
			name.isEmpty() -> {
				bind.tilNome.error = "Campo vazio"
				return null
			}
			servico.isEmpty() -> {
				bind.tilServico.error = "Campo vazio"
				return null
			}
			ano.isEmpty() -> {
				bind.tilAno.error = "Campo vazio"
				return null
			}
			temporada.isEmpty() -> {
				bind.tilTemporadas.error = "Campo vazio"
				return null
			}
			epsiodio.isEmpty() -> {
				bind.tilEpisodios.error = "Campo vazio"
				return null
			}
			genero.isEmpty() -> {
				bind.tilGenero.error = "Campo vazio"
				return null
			}
			else -> return SeriesModel(name, servico, ano, temporada, epsiodio, genero)
		}
	}

}