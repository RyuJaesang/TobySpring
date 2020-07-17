package springbook.learningtest.junit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class JUnitTest {
    static Set<JUnitTest> testObjects = new HashSet<JUnitTest>();
    @Test public void test1(){
        assertThat(this, is(not(sameInstance(testObject))));
        testObject = this;
    }
}
