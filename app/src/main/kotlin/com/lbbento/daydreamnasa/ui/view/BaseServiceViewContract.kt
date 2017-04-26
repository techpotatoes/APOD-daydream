package com.lbbento.daydreamnasa.ui.view

import android.net.Uri

interface BaseServiceViewContract {
    fun setupScreen()
    fun parseUri(uriToParse: String) : Uri
}