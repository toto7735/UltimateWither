package me.toto7735.ultimateWither.listener;

import me.toto7735.ultimateWither.UltimateWither;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class Listener implements org.bukkit.event.Listener {

    @EventHandler
    public void onSpawn(EntitySpawnEvent event) {
        if (!event.getEntityType().equals(EntityType.WITHER)) return;
        Wither wither = (Wither) event.getEntity();
        new BukkitRunnable() {
            public void run() {
                for (Entity player : wither.getNearbyEntities(100, 100, 100)) {
                    if (!(player instanceof Player)) continue;
                    if (wither.isDead()) {
                        this.cancel();
                        return;
                    }
                    if (wither.getHealth() <= wither.getMaxHealth() / 2) {
                        switch (new Random().nextInt(3)) {
                            case 0:
                                player.setVelocity(player.getVelocity().multiply(0.2).setY(1));
                                new BukkitRunnable() {
                                    public void run() {
                                        player.setVelocity(player.getVelocity().multiply(0.1).setY(1));
                                        new BukkitRunnable() {
                                            public void run() {
                                                player.setVelocity(player.getVelocity().multiply(-0.2).setY(1));
                                            }
                                        }.runTaskLater(UltimateWither.getInstance(), 10);
                                    }
                                }.runTaskLater(UltimateWither.getInstance(), 10);
                                break;
                            case 1:
                                for (int i = 0; i < 5; ++i) wither.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, wither.getLocation(), 5);
                                wither.getWorld().playSound(wither.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
                                ((Player) player).damage(2);
                                break;
                            case 2:
                                player.setVelocity(player.getVelocity().multiply(-1));
                                ((Player) player).playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 1, 1);
                                for (int i = 0; i < 10; ++i) wither.getWorld().spawnParticle(Particle.CRIT_MAGIC, wither.getLocation(), 10);
                                break;
                            default:
                                for (int i = 0; i < 5; ++i) wither.launchProjectile(Fireball.class);
                        }
                    } else {
                        switch (new Random().nextInt(2)) {
                            case 0:
                                for (int i = 0; i < 3; ++i) wither.launchProjectile(Fireball.class);
                                break;
                            case 1:
                                player.setVelocity(player.getVelocity().multiply(-1));
                                ((Player) player).playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 1, 1);
                                for (int i = 0; i < 10; ++i) wither.getWorld().spawnParticle(Particle.CRIT_MAGIC, wither.getLocation(), 10);
                                break;
                            default:
                                wither.teleport(player);
                                ((Player) player).playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                                for (int i = 0; i < 10; ++i) wither.getWorld().spawnParticle(Particle.PORTAL, wither.getLocation().add(new Random().nextInt(2), new Random().nextInt(2), new Random().nextInt(2)), 10);
                        }
                    }
                }
            }
        }.runTaskTimer(UltimateWither.getInstance(), 0, 120);
    }

}
