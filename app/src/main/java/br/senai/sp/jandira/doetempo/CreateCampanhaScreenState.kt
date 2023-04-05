package br.senai.sp.jandira.doetempo

import android.net.Uri

data class CreateCampanhaScreenState(
    val listOfSelectedImages: List<Uri> = emptyList()
)
