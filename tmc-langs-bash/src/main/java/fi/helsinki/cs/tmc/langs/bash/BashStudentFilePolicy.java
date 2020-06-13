package fi.helsinki.cs.tmc.langs.bash;

import fi.helsinki.cs.tmc.langs.io.ConfigurableStudentFilePolicy;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class BashStudentFilePolicy extends ConfigurableStudentFilePolicy {
    
    public BashStudentFilePolicy(Path configFileParent) {
        super(configFileParent);
    }

    @Override
    public boolean isStudentSourceFile(Path path, Path projectRootPath) {
        return path.startsWith(Paths.get("src"));
    }
}