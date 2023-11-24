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
        loadConfig();
    }

    @Override
    public void onDisable()
    {
        // Plugin shutdown logic
        System.out.println(LOG + " CMDFurnace stoppé");
    }

    private void loadConfig() {
        // Récupération ou création du dossier de données du plugin
        File dataFolder = getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        // Nom du fichier de configuration
        String configFileName = "recipes.txt";

        // Création du chemin d'accès au fichier de configuration
        Path configFile = dataFolder.toPath().resolve(configFileName);

        // Vérification si le fichier de configuration existe
        if (!Files.exists(configFile)) {
            // Copie du fichier de configuration par défaut depuis les ressources du plugin
            try {
                Files.copy(getResource(configFileName), configFile, StandardCopyOption.REPLACE_EXISTING);
                getLogger().info("Le fichier de configuration a été créé avec succès !");
            } catch (IOException e) {
                getLogger().severe("Erreur lors de la création du fichier de configuration : " + e.getMessage());
            }
        } else {
            getLogger().info("Le fichier de configuration existe déjà.");
        }
    }
}
