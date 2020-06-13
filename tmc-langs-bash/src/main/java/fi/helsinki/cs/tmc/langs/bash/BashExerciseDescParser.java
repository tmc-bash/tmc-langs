package fi.helsinki.cs.tmc.langs.bash;

import fi.helsinki.cs.tmc.langs.domain.TestDesc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.common.collect.ImmutableList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BashExerciseDescParser {

    private static final TypeReference<Map<String, List<String>>> MAP_TYPE_REFERENCE
            = new TypeReference<Map<String, List<String>>>() {};

    private static Path RESULT_FILE = Paths.get(".available_points.json");

    private static Logger log = LoggerFactory.getLogger(BashExerciseDescParser.class);

    private Path path;
    private ObjectMapper mapper;

    public BashExerciseDescParser(Path path) {
        this.path = path;
        this.mapper = new ObjectMapper();
    }

    public ImmutableList<TestDesc> parse() throws IOException {
        List<TestDesc> testDescs = new ArrayList<>();

        byte[] json = Files.readAllBytes(path.resolve(RESULT_FILE));
        Map<String, List<String>> parse = mapper.readValue(json, MAP_TYPE_REFERENCE);

        for (String name : parse.keySet()) {
            ImmutableList<String> points = ImmutableList.copyOf(parse.get(name));
            testDescs.add(new TestDesc(name, points));
        }

        return ImmutableList.copyOf(testDescs);
    }
}
