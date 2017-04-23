package com.lbbento.daydreamnasa.view

import android.net.Uri

interface BaseServiceViewContract {
    fun setScreenContent()
    fun parseUri(uriToParse: String) : Uri
}