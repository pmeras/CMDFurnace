package pmeras.cmdfurnace;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class CMDFurnace extends JavaPlugin
{
    private final static String LOG = "_CMDFurnace_";

    @Override
    public void onEnable()
    {
        // Plugin startup logic
        System.out.println(LOG + " CMDFurnace chargé");
        Objects.requireNonNull(this.getCommand("furnace")).setExecutor(new Commande());
        Commande.initCommande();
    }

    @Override
    public void onDisable()
    {
        // Plugin shutdown logic
        System.out.println(LOG + " CMDFurnace stoppé");
    }
}
