package br.com.giugiu.seriesfilter

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.textfield.TextInputLayout

object BindingAdapters {
	@BindingAdapter("errorText")
	@JvmStatic
	fun setErrorMessage(view: TextInputLayout, errorMessage: String?) {
		if (errorMessage.isNullOrBlank())
			view.error = "Campo vazio"
		else
			view.error = null
	}
}