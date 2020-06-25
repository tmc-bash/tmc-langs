package fi.helsinki.cs.tmc.langs.bash;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import fi.helsinki.cs.tmc.langs.abstraction.Strategy;
import fi.helsinki.cs.tmc.langs.abstraction.ValidationResult;
import fi.helsinki.cs.tmc.langs.domain.ExerciseDesc;
import fi.helsinki.cs.tmc.langs.domain.RunResult;
import fi.helsinki.cs.tmc.langs.domain.TestResult;
import fi.helsinki.cs.tmc.langs.io.StudentFilePolicy;
import fi.helsinki.cs.tmc.langs.utils.TestUtils;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

public class BashPluginTest {

    //@Before
    public void setUp() {
        // TODO
    }

    //@Test
    public void testGetLanguageName() {
        // TODO
    }

    //@Test
    public void testBashIsRecognizedAsBash() {
        // TODO
        // Possibly a C# specific test that is not needed in Bash.

    }

    //@Test
    public void testPythonIsNotRecognizedAsBash() {
        // TODO
        // Possibly a C# specific test that is not needed in Bash.
    }

    //@Test
    public void getStudentFilePolicyReturnsBashStudentFilePolicy() {
        // TODO
    }

    //@Test
    public void testJarPathExists() {
        // TODO
    }

    //@Test
    public void testDownloadingRunner() throws IOException {
        // TODO
    }

    //@Test
    public void testRunTestsPassing() {
        // TODO
    }

    //@Test
    public void testRunTestsFailing() {
        // TODO
    }

    //@Test
    public void testRunTestsNonCompiling() {
        // TODO
    }

    //@Test
    public void testScanExercise() {
        // TODO
    }

    //@Test
    public void testCheckCodeStyleStrategy() {
        // TODO
    }

    //@Test
    public void testCleanRemovesBinAndObj() throws IOException {
        // TODO
        // Possibly a C# specific test that is not needed in Bash.
    }

    // Are any other tests needed?
    // Maybe tests for other languages in langs should be checked?

    // When finished, possible unnecessaty imports should be removed
}