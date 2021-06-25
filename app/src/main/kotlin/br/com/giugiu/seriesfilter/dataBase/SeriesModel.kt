package br.com.giugiu.seriesfilter.dataBase

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import br.com.giugiu.seriesfilter.BR

/**
 * Encapsula o objeto série
 */
class SeriesModel(nome: String, var servico: String, var ano: String, var quantidadeTemporadas: String,
	var quantidadeEpisodios: String, var genero: String, val id: Int = 0) : BaseObservable() {
	@get:Bindable var nome: String = nome
		set(value) {
			field = value
			notifyPropertyChanged(BR.nome)
		}
}

