package com.lbbento.daydreamnasa.ui.view

import android.net.Uri

interface BaseViewContract {
    fun setupScreen()
    fun parseUri(uriToParse: String) : Uri
    fun setupInjection()
}