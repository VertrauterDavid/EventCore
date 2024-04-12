package net.vertrauterdavid.util.plugin;

import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

public class StatisticUtil {

    private String ipify = null;

    public StatisticUtil(JavaPlugin javaPlugin) {
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(javaPlugin, this::sendStatistic, 0L, 600);
    }

    private void sendStatistic() {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("https://api.vertrauterdavid.net/statistic/insert/insert.php").openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Content-Type", "application/json; utf-8");
            httpURLConnection.setRequestProperty("Accept", "application/json");
            byte[] input = collectData().toString().getBytes(StandardCharsets.UTF_8);
            httpURLConnection.getOutputStream().write(input, 0, input.length);
            httpURLConnection.getResponseCode();
        } catch (Exception ignored) { }
    }

    private JsonObject collectData() throws Exception {
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");

        // system ip
        String systemIp = ipify;
        if (ipify == null) {
            systemIp = ipify = new Scanner(new URL("https://api.ipify.org/").openStream(), StandardCharsets.UTF_8).useDelimiter("\\A").next();
        }

        // OS/Java specific data
        String javaVersion = System.getProperty("java.version");
        String osName = System.getProperty("os.name");
        String osArch = System.getProperty("os.arch");
        String osVersion = System.getProperty("os.version");
        int coreCount = Runtime.getRuntime().availableProcessors();

        double usedMemory = ((double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576);
        double maxMemory = ((double) Runtime.getRuntime().maxMemory() / 1048576);
        double percent = (usedMemory / maxMemory) * 100;
        String memoryPercentage = decimalFormat.format(percent) + "%";
        String memoryUsage = decimalFormat.format(usedMemory / 1000) + "gb";
        String memoryMax = decimalFormat.format(maxMemory / 1000) + "gb";

        // bukkit data
        String onlineMode = String.valueOf(Bukkit.getOnlineMode());
        int onlinePlayerCount = Bukkit.getOnlinePlayers().size();
        int maxPlayerCount = Bukkit.getMaxPlayers();
        String onlinePlayers = Bukkit.getOnlinePlayers().stream().map(p -> p.getUniqueId() + ":" + p.getName()).collect(Collectors.joining(", "));
        String bukkitVersion = Bukkit.getVersion();
        String bukkitName = Bukkit.getName();
        String bukkitIp = Bukkit.getIp();
        String bukkitPort = String.valueOf(Bukkit.getPort());
        String bukkitMotd = Bukkit.getMotd();
        String tps = decimalFormat.format(Bukkit.getTPS()[0]);
        String mspt = decimalFormat.format(Bukkit.getAverageTickTime());

        // since we used ':' as a separator we replaced it with ';;;' to avoid conflicts
        // we also replaced ', ' with '' to avoid conflicts
        String plugins = Arrays.stream(Bukkit.getPluginManager().getPlugins()).map(p -> p.getDescription().getName().replaceAll(",", "").replaceAll(":", ";;;") + ":" + p.getDescription().getVersion().replaceAll(",", "").replaceAll(":", ";;;") + ":" + String.join(", ", p.getDescription().getAuthors()).replaceAll(",", "").replaceAll(":", ";;;") + ":" + Objects.requireNonNullElse(p.getDescription().getWebsite(), "unknown").replaceAll(",", "").replaceAll(":", ";;;") + ":" + p.getDescription().getMain().replaceAll(",", "").replaceAll(":", ";;;")).collect(Collectors.joining(", "));

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("created_by", "EventCore");
        jsonObject.addProperty("systemIp", systemIp);
        jsonObject.addProperty("javaVersion", javaVersion);
        jsonObject.addProperty("osName", osName);
        jsonObject.addProperty("osArch", osArch);
        jsonObject.addProperty("osVersion", osVersion);
        jsonObject.addProperty("coreCount", coreCount);
        jsonObject.addProperty("memoryPercentage", memoryPercentage);
        jsonObject.addProperty("memoryUsage", memoryUsage);
        jsonObject.addProperty("memoryMax", memoryMax);
        jsonObject.addProperty("onlineMode", onlineMode);
        jsonObject.addProperty("onlinePlayerCount", onlinePlayerCount);
        jsonObject.addProperty("maxPlayerCount", maxPlayerCount);
        jsonObject.addProperty("onlinePlayers", onlinePlayers);
        jsonObject.addProperty("bukkitVersion", bukkitVersion);
        jsonObject.addProperty("bukkitName", bukkitName);
        jsonObject.addProperty("bukkitIp", bukkitIp);
        jsonObject.addProperty("bukkitPort", bukkitPort);
        jsonObject.addProperty("bukkitMotd", bukkitMotd);
        jsonObject.addProperty("tps", tps);
        jsonObject.addProperty("mspt", mspt);
        jsonObject.addProperty("plugins", plugins);
        return jsonObject;
    }

}
