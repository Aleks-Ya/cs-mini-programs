import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 * Читает версию программы из jar.
 */
class Version {
    private String version;

    Version() {
        try {
            URLClassLoader cl = (URLClassLoader) getClass().getClassLoader();
            URL url = cl.findResource("META-INF/MANIFEST.MF");
            Manifest manifest = new Manifest(url.openStream());
            Attributes attrs = manifest.getMainAttributes();
            version = attrs.getValue("version");
        } catch (IOException e) {
            version = e.getMessage();
        }
    }

    public String getVersion() {
        return version;
    }
}
