package com.lbbento.daydreamnasa

import android.view.View
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.lbbento.daydreamnasa.daydreamnasa.R
import com.lbbento.daydreamnasa.view.BaseServiceView

class MainDaydreamServiceView : BaseServiceView<MainDaydreamServiceViewPresenter>(::MainDaydreamServiceViewPresenter), MainDayDreamServiceViewContract {

    @BindView(R.id.main_dreamservice_view_wallImg)
    lateinit var wallImage : ImageView

    override fun onDreamingStarted() {
        super.onDreamingStarted()
        presenter.onDreamingStarted()
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
