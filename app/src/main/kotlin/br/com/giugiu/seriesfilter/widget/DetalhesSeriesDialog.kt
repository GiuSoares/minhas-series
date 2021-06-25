package br.com.giugiu.seriesfilter.widget

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast

import br.com.giugiu.seriesfilter.R
import br.com.giugiu.seriesfilter.dataBase.SeriesDBHelper
import br.com.giugiu.seriesfilter.dataBase.SeriesModel
import br.com.giugiu.seriesfilter.databinding.DialogSeriesInfoBinding

class DetalhesSeriesDialog(val ctx: Context, val serieModel: SeriesModel) : Dialog(ctx, R.style.AppTheme_Detail_Dialog) {
	private val bind: DialogSeriesInfoBinding by lazy {
		DialogSeriesInfoBinding.inflate(LayoutInflater.from(ctx))
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val db = SeriesDBHelper(ctx)
		setContentView(bind.root)

		// faz a ligação das informações com o layout
		bind.serie = serieModel

		bind.btSalvar.setOnClickListener {
			val series = validateSeries(serieModel.id)
			if (series != null) {
				if (db.updateSerie(series)) {
					Toast.makeText(ctx, "Série atualizada", Toast.LENGTH_LONG).show()
					dismiss()
				} else
					Toast.makeText(ctx, "Série não atualizada", Toast.LENGTH_SHORT).show()
			}
		}

		bind.btExcluir.setOnClickListener {
			if (db.delete(serieModel.id)) {
				Toast.makeText(ctx, "Série deletada", Toast.LENGTH_LONG).show()
				dismiss()
			} else
				Toast.makeText(ctx, "Série não deletada", Toast.LENGTH_SHORT).show()
		}
	}

	private fun validateSeries(id: Int): SeriesModel? {
		val name = bind.etNome.text.toString()
		val servico = bind.etServico.text.toString()
		val ano = bind.etAno.text.toString()
		val temporada = bind.etTemporadas.text.toString()
		val epsiodio = bind.etEpisodios.text.toString()
		val genero = bind.etGenero.text.toString()
		if (name.isEmpty()) {
			bind.tilNome.error = "Campo vazio"
			return null
		}
		if (servico.isEmpty()) {
			bind.tilServico.error = "Campo vazio"
			return null
		}
		if (ano.isEmpty()) {
			bind.tilAno.error = "Campo vazio"
			return null
		}
		if (temporada.isEmpty()) {
			bind.tilTemporadas.error = "Campo vazio"
			return null
		}
		if (epsiodio.isEmpty()) {
			bind.tilEpisodios.error = "Campo vazio"
			return null
		}
		if (genero.isEmpty()) {
			bind.tilGenero.error = "Campo vazio"
			return null
		}
		else return SeriesModel(name, servico, ano, temporada, epsiodio, genero, id)
	}
}