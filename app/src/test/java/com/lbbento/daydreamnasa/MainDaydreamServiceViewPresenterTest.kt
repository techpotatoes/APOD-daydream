package com.lbbento.daydreamnasa

import com.nhaarman.mockito_kotlin.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainDaydreamServiceViewPresenterTest {

    @Mock
    lateinit var view: MainDayDreamServiceViewContract

    @Test
    @Throws(Exception::class)
    fun shouldSetupAndLoadContentOnDreamingStarted() {
        val mainDaydreamServiceViewPresenter = MainDaydreamServiceViewPresenter()

        mainDaydreamServiceViewPresenter.onDreamingStarted()

        verify(view).setScreenContent()
    }
}