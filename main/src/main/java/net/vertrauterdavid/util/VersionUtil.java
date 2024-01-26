package net.vertrauterdavid.util;

import net.vertrauterdavid.EventCore;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

public class VersionUtil {

    private double newestVersion = -1;
    private double currentVersion = -1;

    public VersionUtil() {
        newestVersion = getNewestVersion();
        currentVersion = getCurrentVersion();

        if (newestVersion == -1 || currentVersion == -1) {
            EventCore.getInstance().getLogger().log(Level.WARNING, " ");
            EventCore.getInstance().getLogger().log(Level.WARNING, "§cVersion check failed! If you want to report this error you can do so under:");
            EventCore.getInstance().getLogger().log(Level.WARNING, "§chttps://github.com/VertrauterDavid");
            EventCore.getInstance().getLogger().log(Level.WARNING, " ");
            return;
        }

        if (newestVersion > currentVersion) {
            EventCore.getInstance().getLogger().log(Level.WARNING, " ");
            EventCore.getInstance().getLogger().log(Level.WARNING, "§aEventCore started!");
            EventCore.getInstance().getLogger().log(Level.WARNING, " ");
            EventCore.getInstance().getLogger().log(Level.WARNING, "§fCurrent version: §c" + currentVersion + " §f| Latest version: §c" + newestVersion);
            EventCore.getInstance().getLogger().log(Level.WARNING, " ");
            EventCore.getInstance().getLogger().log(Level.WARNING, "§cYou can download the latest version here:");
            EventCore.getInstance().getLogger().log(Level.WARNING, "§chttps://github.com/VertrauterDavid");
            EventCore.getInstance().getLogger().log(Level.WARNING, " ");
            return;
        }

        EventCore.getInstance().getLogger().log(Level.INFO, " ");
        EventCore.getInstance().getLogger().log(Level.INFO, "§aEventCore successfully started!");
        EventCore.getInstance().getLogger().log(Level.INFO, " ");
        EventCore.getInstance().getLogger().log(Level.INFO, "§fCurrent version: §a" + currentVersion + " §f| Latest version: §a" + newestVersion);
        EventCore.getInstance().getLogger().log(Level.INFO, "§aYou are up to date!");
        EventCore.getInstance().getLogger().log(Level.INFO, " ");
    }

    public double getNewestVersion() {
        if (this.newestVersion != -1) {
            return this.newestVersion;
        }
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("https://api.vertrauterdavid.net/plugins/checkVersionFree.php").openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            byte[] input = ("product=EventCore").getBytes(StandardCharsets.UTF_8);
            httpURLConnection.getOutputStream().write(input, 0, input.length);
            httpURLConnection.getResponseCode();
            return Double.parseDouble(new String(httpURLConnection.getInputStream().readAllBytes(), StandardCharsets.UTF_8));
        } catch (Exception ignored) { }
        return 0;
    }

    public double getCurrentVersion() {
        if (this.currentVersion != -1) {
            return this.currentVersion;
        }
        double currentVersion = 0;
        try {
            currentVersion = Double.parseDouble(EventCore.getInstance().getDescription().getVersion());
        } catch (NumberFormatException ignored) { }
        return currentVersion;
    }

}
