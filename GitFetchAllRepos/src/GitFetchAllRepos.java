import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

/**
 * Вызывает команду fetch для git-репозитариев, перечисленных в repo_urls_for_fetch.txt
 */
public class GitFetchAllRepos {
    private static final File REPO_URLS = new File("repo_urls_for_fetch.txt");
    private static final String GIT_EXECUTABLE = getGitExecutable();

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.printf("Read: %s\n\n", REPO_URLS.getAbsolutePath());
        BufferedReader reader = new BufferedReader(new FileReader(REPO_URLS));
        String line;
        while ((line = reader.readLine()) != null) {
            File repoDir = new File(line);
            System.out.printf("Fetch: %s\n", repoDir.getAbsolutePath());
            if (!repoDir.exists()) {
                throw new IllegalArgumentException("Repo dir doesn't exists: " + repoDir.getAbsolutePath());
            }
            String command = GIT_EXECUTABLE + " fetch --all --prune ";
            System.out.printf("Command: %s\n", command);
            Process process = Runtime.getRuntime().exec(command, null, repoDir);
            int status = process.waitFor();
            if (status == 0) {
                reprintOutput(process);
            } else {
                throw new RuntimeException();
            }
            System.out.println();
        }
    }

    private static void reprintOutput(Process process) throws IOException {
        InputStream is = process.getInputStream();
        int b;
        while ((b = is.read()) != -1) {
            System.out.write(b);
        }
    }

    /**
     * Написать для разных ОС.
     */
    public static String getGitExecutable() {
        return "/usr/bin/git";
    }

//    private static void runGit(String command, File gitExecutable, File repoDir) throws IOException, InterruptedException {
//        String initCmd = gitExecutable.getAbsolutePath() + " " + command;
//        Process gitInit = Runtime.getRuntime().exec(initCmd, null, repoDir);
//        int status = gitInit.waitFor();
//        if (status != 0) {
//            throw new RuntimeException(format("Process %s return error status: %s", initCmd, status));
//        }
//    }
}