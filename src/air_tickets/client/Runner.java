package air_tickets.client;

import air_tickets.in_out.FileSaveLoader;
import air_tickets.in_out.Initializer;
import air_tickets.in_out.Saver;
import air_tickets.in_out.Extension;
import air_tickets.menus.MainMenu;

/**
 * Created by Anton on 13.07.2017.
 */
public class Runner {

    private final static String SAVE_LOAD_PATH = System.getProperty("user.dir") + "\\src\\air_tickets\\files\\";
    private final static Saver datSaver = new FileSaveLoader(SAVE_LOAD_PATH, Extension.DAT);
    private final static Saver jsonSaver = new FileSaveLoader(SAVE_LOAD_PATH, Extension.JSON);
    private final static Initializer initializer = (Initializer) jsonSaver;

    public static void main(String[] args) {

        initializer.init();
        new MainMenu().run();
        datSaver.save();
        jsonSaver.save();
    }
}
