package com.sismics.util;

import org.junit.Assert;
import org.junit.Test;

import java.io.FilenameFilter;
import java.util.List;

/**
 * Additional tests for ResourceUtil class.
 *
 */
public class TestResourceUtilFiltering {

    @Test
    public void testListWithFilter() throws Exception {
        // Filter only class files
        FilenameFilter classFilter = (dir, name) -> name.endsWith(".class");
        List<String> classFiles = ResourceUtil.list(Test.class, "/junit/framework", classFilter);
        Assert.assertTrue(classFiles.contains("Test.class"));

        // Verify all returned files have .class extension
        for (String file : classFiles) {
            Assert.assertTrue("All files should end with .class", file.endsWith(".class"));
        }

        // Filter with specific name pattern
        FilenameFilter testFilter = (dir, name) -> name.startsWith("Test");
        List<String> testFiles = ResourceUtil.list(Test.class, "/junit/framework", testFilter);
        Assert.assertTrue(testFiles.contains("Test.class"));
        Assert.assertTrue(testFiles.contains("TestCase.class"));

        // Filter that matches nothing
        FilenameFilter nonexistentFilter = (dir, name) -> name.contains("nonexistent");
        List<String> emptyList = ResourceUtil.list(Test.class, "/junit/framework", nonexistentFilter);
        Assert.assertTrue(emptyList.isEmpty());
    }
}
