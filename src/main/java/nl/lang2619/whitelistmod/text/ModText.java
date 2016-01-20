package nl.lang2619.whitelistmod.text;

import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.Mod;
import net.minecraft.server.MinecraftServer;
import nl.lang2619.whitelistmod.config.ModConfig;
import nl.lang2619.whitelistmod.twitch.TwitchConfig;

import javax.xml.soap.Text;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Tim on 24-12-2014.
 */
public class ModText {
    private static final HashSet<String> users = new HashSet<String>();

    public static HashSet<String> getUsers() {
        return users;
    }

    private static final HashSet<String> whitelisted = new HashSet<String>();

    public static HashSet<String> getWhitelisted() {
        return whitelisted;
    }

    public static void checkWhitelist() {
        try {
            setUsers();
            Whitelisted();
            Iterator<String> names;
            names = users.iterator();

            while (names.hasNext()) {
                if (ModConfig.version.equalsIgnoreCase("add")) {
                    GameProfile gameProfile;
                    String name = names.next();
                    gameProfile = MinecraftServer.getServer().func_152358_ax().func_152655_a(name);
                    try {
                        MinecraftServer.getServer().getConfigurationManager().func_152601_d(gameProfile);
                    } catch (NullPointerException e) {
                        System.out.println(name + " doesn't exist");
                    }
                }
                if (ModConfig.version.equalsIgnoreCase("sync")) {
                    String name = null;
                    try {
                        GameProfile gameProfile;
                        name = names.next();
                        gameProfile = MinecraftServer.getServer().func_152358_ax().func_152655_a(name);
                        MinecraftServer.getServer().getConfigurationManager().func_152601_d(gameProfile);
                    } catch (NullPointerException e) {
                        System.out.println(name + " doesn't exist");
                    }
                    try {
                        Iterator<String> names2;
                        names2 = whitelisted.iterator();
                        while (names2.hasNext()) {
                            GameProfile gameProfile1;
                            String name2 = names2.next();
                            if (!users.contains(name2)) {
                                gameProfile1 = MinecraftServer.getServer().func_152358_ax().func_152655_a(name2);
                                MinecraftServer.getServer().getConfigurationManager().func_152597_c(gameProfile1);
                            }
                        }
                    } catch (Exception e) {

                    }
                }
            }

            MinecraftServer.getServer().getConfigurationManager().loadWhiteList();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setUsers() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File(TextConfig.path))));
        String l;

        while ((l = in.readLine()) != null) {
            users.add(l.trim().toLowerCase());
        }
    }


    public static void Whitelisted() {
        String[] names = MinecraftServer.getServer().getConfigurationManager().func_152599_k().func_152685_a();

        for (int i = 0; i < names.length; i++) {
            whitelisted.add(names[i].toLowerCase());
        }
    }

}
