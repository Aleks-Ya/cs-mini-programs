import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

/**
 * Поток, выполняющий fetch.
 */
class Fetcher implements Callable<String> {
    private static final String GIT_EXECUTABLE = getGitExecutable();
    private static final String command = GIT_EXECUTABLE + " fetch --all --prune ";

    private final File repoDir;

    public Fetcher(File repoDir) {
        this.repoDir = repoDir;
    }

    @Override
    public String call() throws Exception {
        String output = repoDir.getAbsolutePath() + ":\n";
        try {
            if (!repoDir.exists()) {
                throw new IllegalArgumentException(
                        String.format("ERROR: Repo dir doesn't exists: %s%n", repoDir.getAbsolutePath()));
            }

            Process process = Runtime.getRuntime().exec(command, null, repoDir);
            int status = process.waitFor();
            if (status == 0) {
                output += inputStreamToString(process.getInputStream());
            } else {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            output += e.getMessage();
        }
        return output;
    }

    private String inputStreamToString(InputStream io) throws IOException {
        StringBuilder result = new StringBuilder();
        InputStreamReader isr = new InputStreamReader(io);
        char[] c = new char[1];
        while (isr.read(c) != -1) {
            result.append(c);
        }
        return result.toString();
    }

    /**
     * Написать для разных ОС.
     */
    public static String getGitExecutable() {
        return "/usr/bin/git";
    }
}