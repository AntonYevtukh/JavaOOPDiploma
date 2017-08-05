package air_tickets.in_out;

import air_tickets.exceptions.DataIntegrityException;
import air_tickets.globals.Schedule;
import air_tickets.globals.Users;
import air_tickets.globals.World;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.IOException;

/**
 * Created by Anton on 03.08.2017.
 */
public class FileSaveLoader implements Initializer, Saver {

    private final String path;
    private final Serializer serializer;
    private final String ext;

    public FileSaveLoader(String path, Extension extension) {
        this.serializer = extension.getSerializer();
        ext = extension.toString();
        this.path = path + ext + "\\";
    }

    public void init() {
       load();
       System.err.println("Data successfully loaded from directory:\n" + path);
       System.err.println("Initialization from file successfully completed.\n");
       try {
           Thread.sleep(100);
       } catch (InterruptedException exc) {

       }
    }

    public void load() {

        try {
            System.err.println("Reading files with extension: " + ext);
            World world = serializer.deserialize(World.class, path + "world." + ext);
            Schedule schedule = serializer.deserialize(Schedule.class, path + "schedule." + ext);
            Users users = serializer.deserialize(Users.class, path + "users." + ext);

            if (world.setAsInstance())
                System.err.println("World successfully loaded from the file:\n" + path + "world." + ext);
            else
                System.err.println("World instance already exists, nothing to load");

            if (schedule.setAsInstance())
                System.err.println("Schedule successfully loaded from the file:\n" + path + "schedule." + ext);
            else
                System.err.println("Schedule instance already exists, nothing to load");

            if (users.setAsInstance())
                System.err.println("Users successfully loaded from the file:\n" + path + "users." + ext);
            else
                System.err.println("Users instance already exists, nothing to load");

        } catch (IOException | ClassNotFoundException | DataIntegrityException | JsonIOException |JsonSyntaxException exc) {
            System.err.println("Error loading entity: " + exc.getClass().getSimpleName() + ": " + exc.getMessage());
            System.err.println("Program will be terminated.");
            System.err.println("Try to use CodeInitializer or ensure that all files have valid content and exist in directory:");
            System.err.println(path);
            System.exit(1);
        }
    }

    public void save() {
        try {
            File file = new File(path);
            file.mkdirs();
            serializer.serialize(World.getInstance(), path + "world." + ext);
            serializer.serialize(Schedule.getInstance(), path + "schedule." + ext);
            serializer.serialize(Users.getInstance(), path + "users." + ext);
            System.err.println("Data successfully saved into directory\n" + path);
        } catch (IOException exc) {
            System.err.println("Data was not saved properly, it is not recommend to load it from directory\n" + path);

        }
    }
}
