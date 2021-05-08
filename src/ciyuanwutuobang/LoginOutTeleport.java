package ciyuanwutuobang;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginOutTeleport extends JavaPlugin implements Listener {
    private Map<String, BukkitTask> timer = new HashMap<>();
    private Map<String, Location> loc = new HashMap<>();
    private Map<String, String> Zhuangtai = new HashMap<>();
    private List<String> worlds = new ArrayList<>();

    private void debug(String s){
        //Bukkit.getConsoleSender().sendMessage(s);
    }

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getConsoleSender().sendMessage("[LoginOutTeleport]星系掉线传送已开启");
        Bukkit.getConsoleSender().sendMessage("[LoginOutTeleport]作者：150149  QQ：1802796278");

        worlds.add("DIM-1505");
        worlds.add("DIM-20");
        worlds.add("DIM-65");
        worlds.add("DIM-64");
        worlds.add("DIM-1503");
        worlds.add("DIM-21");
        worlds.add("DIM-79");
        worlds.add("DIM-78");
        worlds.add("DIM-1501");
        worlds.add("DIM-1506");
        worlds.add("DIM-1511");
        worlds.add("DIM-1500");
        worlds.add("DIM-15");
        worlds.add("DIM-69");
        worlds.add("DIM-68");
        worlds.add("DIM-22");
        worlds.add("DIM-81");
        worlds.add("DIM-80");
        worlds.add("DIM-67");
        worlds.add("DIM-66");
        worlds.add("DIM-13");
        worlds.add("DIM-61");
        worlds.add("DIM-60");
        worlds.add("DIM-18");
        worlds.add("DIM-75");
        worlds.add("DIM-74");
        worlds.add("DIM-1509");
        worlds.add("DIM-1502");
        worlds.add("DIM-19");
        worlds.add("DIM-77");
        worlds.add("DIM-76");
        worlds.add("DIM-1507");
        worlds.add("DIM-16");
        worlds.add("DIM-71");
        worlds.add("DIM-70");
        worlds.add("DIM-1508");
        worlds.add("DIM-1510");
        worlds.add("DIM-1504");
        worlds.add("DIM-17");
        worlds.add("DIM-73");
        worlds.add("DIM-72");
        worlds.add("DIM-63");
        worlds.add("DIM-62");
        worlds.add("DIM-29");
        worlds.add("DIM-30");
        worlds.add("DIM-31");
        worlds.add("DIM-28");
        worlds.add("DIM-27");
        worlds.add("DIM-26");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("[LoginOutTeleport]星系掉线传送已关闭");
    }

    @EventHandler
    public void onChangworld(PlayerChangedWorldEvent event) {
        if (event.getPlayer().isOnline()) {
            debug("所在的世界为:" + event.getPlayer().getWorld().getName());
            for (String s :worlds) {
                if (s.equals(event.getPlayer().getWorld().getName())) {
                    loc.put(event.getPlayer().getPlayerListName(),event.getPlayer().getLocation());
                    Zhuangtai.put(event.getPlayer().getPlayerListName(),"true");
                    break;
                }
                else {
                    Zhuangtai.put(event.getPlayer().getPlayerListName(),"false");
                }
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        timer.get(event.getPlayer().getPlayerListName()).cancel();
        if ("true".equals(Zhuangtai.get(event.getPlayer().getPlayerListName()))) {
            Bukkit.getConsoleSender().sendMessage("检测到" + event.getPlayer().getPlayerListName() + "掉线在" + loc.get(event.getPlayer().getPlayerListName()).getWorld().getName());
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if ("true".equals(Zhuangtai.get(event.getPlayer().getPlayerListName()))) {
            event.getPlayer().sendMessage("§8[ §3LoginOutTeleport §8]§c检测到你的掉线位置在太空，20秒后将传送回掉线前位置");
            BukkitTask time2 = new BukkitRunnable() {
                @Override
                public void run() {
                    event.getPlayer().teleport(loc.get(event.getPlayer().getPlayerListName()));
                    while(event.getPlayer().getLocation().add(0,-1,0).getBlock().isEmpty()) {
                        event.getPlayer().teleport(event.getPlayer().getLocation().add(0,-1,0));
                    }
                }
            }.runTaskLater(this, 400);
        }
        BukkitTask time=new BukkitRunnable() {
            @Override
            public void run() {
                if (event.getPlayer().isOnline()) {
                    debug("所在的世界为:" + event.getPlayer().getWorld().getName());
                    for (String s :worlds) {
                        if (s.equals(event.getPlayer().getWorld().getName())) {
                            loc.put(event.getPlayer().getPlayerListName(),event.getPlayer().getLocation());
                            Zhuangtai.put(event.getPlayer().getPlayerListName(),"true");
                            break;
                        }
                        else {
                            Zhuangtai.put(event.getPlayer().getPlayerListName(),"false");
                        }
                    }
                }
            }
        }.runTaskTimer(this, 100,20);
        timer.put(event.getPlayer().getPlayerListName(),time);
    }
}
