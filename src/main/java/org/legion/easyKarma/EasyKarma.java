package org.legion.easyKarma;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.legion.easyKarma.Commands.karma;
import org.legion.easyKarma.Events.onAttack;
import org.legion.easyKarma.Events.onKill;
import org.legion.easyKarma.Events.onMobTarget;
import org.legion.easyKarma.Events.onMove;
import org.legion.easyKarma.TabCompleters.karmaTab;

import java.io.File;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.legion.easyKarma.Utils.Util.color;

public final class EasyKarma extends JavaPlugin {

    private static EasyKarma instance;

    public EasyKarma() {
        EasyKarma.instance = this;
    }

    public static EasyKarma getInstance() {
        return EasyKarma.instance;
    }

    private Connection connection;
    public final Map<UUID, Integer> karma = new HashMap<>();

    @Override
    public void onEnable() {
        setupDatabase();
        loadKarma();
        saveDefaultConfig();
        getLogger().info(color("&aEasyKarma loaded successfully!"));

        // Events
        Bukkit.getPluginManager().registerEvents(new onMove(), this);
        Bukkit.getPluginManager().registerEvents(new onAttack(), this);
        Bukkit.getPluginManager().registerEvents(new onKill(), this);
        Bukkit.getPluginManager().registerEvents(new onMobTarget(), this);

        // Commands
        this.getCommand("karma").setExecutor(new karma());
        getCommand("karma").setTabCompleter(new karmaTab());
    }

    @Override
    public void onDisable() {
        saveKarma();
        closeDatabase();
        getLogger().info(color("&cEasyKarma stopped successfully!"));
    }

    private void setupDatabase() {
        try {
            File dbFile = new File(getDataFolder(), "karma.db");
            if (!dbFile.getParentFile().exists()) dbFile.getParentFile().mkdirs();

            connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile.getAbsolutePath());

            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS karma (
                        uuid TEXT PRIMARY KEY,
                        amount INTEGER NOT NULL
                    );
                """);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            getLogger().severe("Не удалось подключиться к базе данных!");
        }
    }

    private void loadKarma() {
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT * FROM karma;");
            while (rs.next()) {
                UUID uuid = UUID.fromString(rs.getString("uuid"));
                int amount = rs.getInt("amount");
                karma.put(uuid, amount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveKarma() {
        try (PreparedStatement ps = connection.prepareStatement("""
            INSERT OR REPLACE INTO karma (uuid, amount) VALUES (?, ?);
        """)) {
            for (Map.Entry<UUID, Integer> entry : karma.entrySet()) {
                ps.setString(1, entry.getKey().toString());
                ps.setInt(2, entry.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeDatabase() {
        try {
            if (connection != null && !connection.isClosed()) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
