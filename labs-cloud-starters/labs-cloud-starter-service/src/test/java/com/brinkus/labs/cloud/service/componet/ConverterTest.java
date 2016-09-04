package com.brinkus.labs.cloud.service.componet;

import com.brinkus.labs.cloud.service.component.Converter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ConverterTest {

    private class IntegerConverter implements Converter<String, Integer> {

        @Override
        public Integer convert(final String input) {
            return Integer.parseInt(input);
        }

    }

    private IntegerConverter converter;

    @Before
    public void before() throws Exception {
        converter = new IntegerConverter();
    }

    @After
    public void after() throws Exception {
        converter = null;
    }

    @Test
    public void convertElement() {
        Integer output = converter.convert("5");
        assertThat(output, is(5));
    }

    @Test
    public void convertElements() {
        List<Integer> outputs = converter.convert(Arrays.asList("1", "2"));
        assertThat(outputs.size(), is(2));
        assertThat(outputs.get(0), is(1));
        assertThat(outputs.get(1), is(2));
    }

}
