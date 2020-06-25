package fi.helsinki.cs.tmc.langs.bash;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import fi.helsinki.cs.tmc.langs.domain.TestDesc;
import fi.helsinki.cs.tmc.langs.utils.TestUtils;

import com.google.common.collect.ImmutableList;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;

public class BashExerciseDescParserTest {

    private final Path allPassedSampleDir;
    private final Path noPointsSampleDir;
    
    public BashExerciseDescParserTest() {
        Path descSamplesDir = TestUtils.getPath(getClass(), "Desc_samples");
        allPassedSampleDir = descSamplesDir.resolve("all_tests_passed_sample");
        noPointsSampleDir = descSamplesDir.resolve("no_points_sample");
    }

    private void testDescAsExpected(TestDesc desc, String name, String[] points) {
        assertEquals(name, desc.name);
        assertArrayEquals(points, desc.points.toArray());
    }

    @Test
    public void testDescParsePointsCorrectly() throws IOException {
        BashExerciseDescParser parser = new BashExerciseDescParser(allPassedSampleDir);
        ImmutableList<TestDesc> descs = parser.parse();

        testDescAsExpected(descs.get(0), "PassingSampleTests.ProgramTest.TestGetName", 
                new String[]{"1", "1.1"});
        testDescAsExpected(descs.get(1), "PassingSampleTests.ProgramTest.TestGetYear", 
                new String[]{"1", "1.2"});
    }

    @Test
    public void testDescParseEmptyPointsCorrectly() throws IOException {
        BashExerciseDescParser parser = new BashExerciseDescParser(noPointsSampleDir);
        ImmutableList<TestDesc> descs = parser.parse();

        testDescAsExpected(descs.get(0), 
                "NoPoints.ProgramTest.TestCheckSameFailed", new String[]{});
        testDescAsExpected(descs.get(1), 
                "NoPoints.ProgramTest.TestCheckFinePassed", new String[]{});
    }
}