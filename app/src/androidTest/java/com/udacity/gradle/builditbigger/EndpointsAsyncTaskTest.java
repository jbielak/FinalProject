package com.udacity.gradle.builditbigger;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.jbielak.javajokes.Joker;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * Created by Justyna on 2018-05-26.
 */

@RunWith(AndroidJUnit4.class)
public class EndpointsAsyncTaskTest {

    @Test
    public void testGetJoke() {
        EndpointsAsyncTask task = new EndpointsAsyncTask();
        String result = task.doInBackground(InstrumentationRegistry.getTargetContext());
        String expected = Joker.getJoke();
        assertNotNull(result);
        assertEquals(expected, result);
    }
}
