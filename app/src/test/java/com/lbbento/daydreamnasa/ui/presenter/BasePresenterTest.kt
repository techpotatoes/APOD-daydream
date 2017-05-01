package com.lbbento.daydreamnasa.ui.presenter

import com.lbbento.daydreamnasa.ui.view.BaseViewContract
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BaseServicePresenterTest {

    @Test
    fun onAttachedToWindowShouldSetViewAndCallOnSetContent() {
        val mockedView = mock(BaseViewContract::class.java)
        val basePresenter = BasePresenterTest()

        basePresenter.onAttachedToWindow(mockedView)

        verify(mockedView).setupScreen()
        assertEquals(basePresenter.mView, mockedView)
    }

    class BasePresenterTest : BasePresenter<BaseViewContract>()
}