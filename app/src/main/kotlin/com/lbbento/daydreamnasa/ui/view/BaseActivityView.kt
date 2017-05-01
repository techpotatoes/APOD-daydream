package com.lbbento.daydreamnasa.ui.view

import android.app.Activity
import android.net.Uri
import android.os.Bundle

abstract class BaseActivityView: Activity(), BaseViewContract {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupInjection()
    }

    @Throws(Exception::class)
    override fun parseUri(uriToParse: String): Uri {
        return Uri.parse(uriToParse)
    }
}