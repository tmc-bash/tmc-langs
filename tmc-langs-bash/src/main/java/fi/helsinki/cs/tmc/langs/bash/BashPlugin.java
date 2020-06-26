package fi.helsinki.cs.tmc.langs.bash;

import fi.helsinki.cs.tmc.langs.AbstractLanguagePlugin;
import fi.helsinki.cs.tmc.langs.abstraction.Strategy;
import fi.helsinki.cs.tmc.langs.abstraction.ValidationError;
import fi.helsinki.cs.tmc.langs.abstraction.ValidationResult;
import fi.helsinki.cs.tmc.langs.domain.ExerciseBuilder;
import fi.helsinki.cs.tmc.langs.domain.ExerciseDesc;
import fi.helsinki.cs.tmc.langs.domain.RunResult;
import fi.helsinki.cs.tmc.langs.domain.TestDesc;
import fi.helsinki.cs.tmc.langs.io.StudentFilePolicy;
import fi.helsinki.cs.tmc.langs.io.sandbox.StudentFileAwareSubmissionProcessor;
import fi.helsinki.cs.tmc.langs.io.zip.StudentFileAwareUnzipper;
import fi.helsinki.cs.tmc.langs.io.zip.StudentFileAwareZipper;
import fi.helsinki.cs.tmc.langs.utils.ProcessRunner;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

public class BashPlugin extends AbstractLanguagePlugin {

    private static final Path BASH_FOLDER_PATH = Paths.get("src");
    private static final Path TEST_FOLDER_PATH = Paths.get("test");

    private static final String CANNOT_RUN_TESTS_MESSAGE = "Failed to run tests.";
    private static final String CANNOT_PARSE_TEST_RESULTS_MESSAGE = "Failed to read test results.";
    private static final String CANNOT_SCAN_EXERCISE_MESSAGE = "Failed to scan exercise.";
    private static final String CANNOT_PARSE_EXERCISE_DESCRIPTION_MESSAGE 
            = "Failed to parse exercise description.";
    private static final String CANNOT_SCAN_PROJECT_TYPE_MESSAGE = "Failed to scan project type";

    private static Logger log = LoggerFactory.getLogger(BashPlugin.class);

    public BashPlugin() {
        super(
                new ExerciseBuilder(), 
                new StudentFileAwareSubmissionProcessor(), 
                new StudentFileAwareZipper(),
                new StudentFileAwareUnzipper());
    }

    @Override
    public boolean isExerciseTypeCorrect(Path path) {

        try {
            Stream<Path> folderPaths = Files.walk(path.resolve(BASH_FOLDER_PATH), 2);
            Stream<Path> testPaths = Files.walk(path.resolve(TEST_FOLDER_PATH), 2);

            if (folderPaths.map(p -> p.toString()).filter(f -> f.endsWith(".sh")).count() != 0
                    && testPaths.map(p -> p.toString()).filter(
                            f -> f.endsWith(".bats")).count() != 0) {
                return true;
            }

        } catch (Exception ex) {
            log.error(CANNOT_SCAN_PROJECT_TYPE_MESSAGE, ex);
        }

        return false;
    }

    @Override
    protected StudentFilePolicy getStudentFilePolicy(Path projectPath) {
        return new BashStudentFilePolicy(projectPath);
    }

    @Override
    public String getPluginName() {
        return "bash";
    }

    @Override
    public Optional<ExerciseDesc> scanExercise(Path path, String exerciseName) {
        ProcessRunner runner = new ProcessRunner(getAvailablePointsCommand(), path);

        try {
            runner.call();
        } catch (Exception e) {
            log.error(CANNOT_SCAN_EXERCISE_MESSAGE, e);
        }

        try {
            ImmutableList<TestDesc> testDescs = new BashExerciseDescParser(path).parse();
            return Optional.of(new ExerciseDesc(exerciseName, testDescs));
        } catch (IOException e) {
            log.error(CANNOT_PARSE_EXERCISE_DESCRIPTION_MESSAGE, e);
        }

        return Optional.absent();
    }

    @Override
    public RunResult runTests(Path path) {
        ProcessRunner runner = new ProcessRunner(getTestCommand(), path);

        try {
            runner.call();
        } catch (Exception e) {
            log.error(CANNOT_RUN_TESTS_MESSAGE, e);
        }

        try {
            return new BashTestResultParser(path).parse();
        } catch (IOException e) {
            log.error(CANNOT_PARSE_TEST_RESULTS_MESSAGE, e);
        }
        return null;
    }

    @Override
    public ValidationResult checkCodeStyle(Path path, Locale messageLocale) {
        return new ValidationResult() {
            @Override
            public Strategy getStrategy() {
                return Strategy.DISABLED;
            }

            @Override
            public Map<File, List<ValidationError>> getValidationErrors() {
                return Maps.newHashMap();
            }
        };
    }

    @Override
    public void clean(Path path) {
    }

    private boolean isOsWindows() {
        String os = System.getProperty("os.name").toLowerCase();
        return (os.indexOf("win") >= 0);
    }

    private String[] getAvailablePointsCommand() {
        if (isOsWindows()) {
            String powershellPath = 
                    "C:\\Windows\\System32\\WindowsPowerShell\\v1.0\\powershell.exe";
            return new String[] { powershellPath, "bash ./tmc/available_points.sh" };
        } else {
            return new String[] { "/bin/bash", "-c", "./tmc/available_points.sh" };
        }
    }

    private String[] getTestCommand() {
        if (isOsWindows()) {
            String powershellPath = 
                    "C:\\Windows\\System32\\WindowsPowerShell\\v1.0\\powershell.exe";
            return new String[] { powershellPath, "bash ./tmc/runner.sh" };
        } else {
            return new String[] { "/bin/bash", "-c", "./tmc/runner.sh" };
        }
    }
}
