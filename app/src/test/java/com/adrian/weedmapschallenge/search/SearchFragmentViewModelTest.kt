package com.adrian.weedmapschallenge.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adrian.weedmapschallenge.common.LocationHelper
import com.adrian.weedmapschallenge.domain.FusionRepository
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
internal class SearchFragmentViewModelTest {

    @Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mockFusionRepository: FusionRepository
    @Mock
    lateinit var mockLocationHelper: LocationHelper

    private lateinit var viewModel: SearchFragmentViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = SearchFragmentViewModel(mockFusionRepository, mockLocationHelper)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getBusinessesLiveData() {
    }

   @Test
    fun getSuccessfulLocationUpdateLiveData() {
    }

   @Test
    fun getErrorToastLiveData() {
    }

   @Test
    fun getEmptyResultsLiveData() {
    }

   @Test
    fun searchYelp() {
    }

   @Test
    fun updateLocation() {
    }
}

/*
//retrofit
 @Test
    public void testApiFetchDataSuccess() {
        // Mock API response
        when(apiClient.fetchNews()).thenReturn(Single.just(new NewsList()));
        viewModel.fetchNews();
        verify(observer).onChanged(NewsListViewState.LOADING_STATE);
        verify(observer).onChanged(NewsListViewState.SUCCESS_STATE);
    }


    //viewmodels
    // Pass:
    assertEquals(viewModel.liveData1.getOrAwaitValue(), "foo")
    assertEquals(viewModel.liveData2.getOrAwaitValue(), "FOO")
 */