package com.lbbento.daydreamnasa

import android.view.View
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.lbbento.daydreamnasa.daydreamnasa.R
import com.lbbento.daydreamnasa.view.BaseServiceView

class MainDaydreamServiceView : BaseServiceView(), MainDayDreamServiceViewContract {

    private val presenter = MainDaydreamServiceViewPresenter()

    @BindView(R.id.main_dreamserviceview_wallImg)
    lateinit var wallImage : ImageView

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        presenter.onAttachedToWindow(this)
    }

    override fun setScreenContent() {
        isFullscreen = true
        isInteractive = true
        isScreenBright = false

        val view = View.inflate(applicationContext, R.layout.main_dreamservice_view, null)
        ButterKnife.bind(this, view)
        setContentView(view)
    }
}
