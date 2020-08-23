package com.adrian.weedmapschallenge.search

import android.location.Location
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adrian.weedmapschallenge.common.LocationHelper
import com.adrian.weedmapschallenge.domain.FusionRepository
import com.adrian.weedmapschallenge.getOrAwaitValue
import junit.framework.Assert.assertEquals
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


@RunWith(JUnit4::class)
internal class SearchFragmentViewModelTest {

    @Rule @JvmField
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
    fun updateUserLocation_updatesLiveData_asTrue() {
        val expectedLocation = Location("")
        expectedLocation.latitude = TEST_LATITUDE
        expectedLocation.longitude = TEST_LONGITUDE

        `when`(mockLocationHelper.getUsersLastLocation()).thenReturn(expectedLocation)

        viewModel.updateLocation()

        assertEquals(true, viewModel.successfulLocationUpdateLiveData.getOrAwaitValue())
    }

    @Test
    fun updateUserLocation_updatesLiveData_withNullLocation_asFalse() {
    }

    companion object {
        private const val TEST_LATITUDE = 60.00
        private const val TEST_LONGITUDE = 9.00
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