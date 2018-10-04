package com.example.oldivity;

import android.content.Context;
import android.view.View;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import static org.junit.Assert.*;

public class MainActivityTest {

    MainActivity myObjectUnderTest = new MainActivity();
    @Test
    public void emailAndPasswordValidator() {
        assertThat(myObjectUnderTest.validateEntries("a@test.com", "123"), is(true));

    }

}
