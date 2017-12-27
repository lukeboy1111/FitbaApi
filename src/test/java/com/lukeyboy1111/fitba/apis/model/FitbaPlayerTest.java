package com.lukeyboy1111.fitba.apis.model;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Test;

public class FitbaPlayerTest
{

    @Test
    public void idIsGeneratedWhenNewPlayerIsCreated()
    {
        FitbaPlayer player = new FitbaPlayer("MW Test", "desc", new Date());
        assertThat(player.getId(), is(not("")));
        assertThat(player.getId(), is(not(nullValue())));
    }
    
}
