package net.vertrauterdavid.util.plugin;

import org.bukkit.plugin.java.JavaPlugin;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

public class VersionUtil {

    private double newestVersion = -1;
    private double currentVersion = -1;

    public VersionUtil(JavaPlugin javaPlugin, String product) {
        newestVersion = getNewestVersion(product);
        currentVersion = getCurrentVersion(javaPlugin);

        if (newestVersion == -1 || currentVersion == -1) {
            javaPlugin.getLogger().log(Level.WARNING, "§cVersion check failed! If you want to report this error you can do so under:\n§chttps://github.com/VertrauterDavid");
            return;
        }

        if (newestVersion > currentVersion) {
            javaPlugin.getLogger().log(Level.WARNING, "\n\n§a" + product + " started!\n\n§fCurrent version: §c%s §f| Latest version: §c%s\n\n§cYou can download the latest version here:\n§chttps://github.com/VertrauterDavid\n\n".formatted(currentVersion, newestVersion));
            return;
        }

        javaPlugin.getLogger().log(Level.INFO, "\n\n§a" + product + " successfully started!\n\n§fCurrent version: §a%s §f| Latest version: §a%s\n§aYou are up to date:\n\n".formatted(currentVersion, newestVersion));
    }

    public double getNewestVersion(String product) {
        if (this.newestVersion != -1) {
            return this.newestVersion;
        }

        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("https://api.vertrauterdavid.net/plugins/checkVersionFree.php").openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            byte[] input = ("product=" + product).getBytes(StandardCharsets.UTF_8);
            httpURLConnection.getOutputStream().write(input, 0, input.length);
            httpURLConnection.getResponseCode();
            newestVersion = Double.parseDouble(new String(httpURLConnection.getInputStream().readAllBytes(), StandardCharsets.UTF_8));
            return newestVersion;
        } catch (Exception ignored) { }

        return 0;
    }

    public double getCurrentVersion(JavaPlugin javaPlugin) {
        if (this.currentVersion != -1) {
            return this.currentVersion;
        }

        try {
            this.currentVersion = Double.parseDouble(javaPlugin.getDescription().getVersion());
        } catch (NumberFormatException ignored) { }

        return this.currentVersion;
    }

}
