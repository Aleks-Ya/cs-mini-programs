package config;

import org.junit.Test;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class FileConfigTest {

    @Test
    public void getRepositories() throws Exception {
        final List<String> expected = new ArrayList<>();
        final String repo1 = "/home/alexx/cs/cash1";
        expected.add(repo1);
        final String repo2 = "~/cs/cash1";
        expected.add(repo2);

        final Path configPath = Files.createTempFile("FileConfigTest_", ".ini");
        Files.write(configPath, expected, Charset.defaultCharset());
        Config config = new FileConfig(configPath);

        List<File> repoList = config.getRepositories();

        for (String repo : expected) {
            assertTrue(repoList.contains(new File(repo)));
        }
    }
}