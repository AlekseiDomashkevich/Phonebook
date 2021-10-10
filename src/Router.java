
import controller.ApplicationController;
import controller.IController;
import controller.PhonebookController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Router {
    private String[] arguments;

    public Router(String[] arguments) {
        this.arguments = arguments;
    }

    public void dispatch() {
        IController controller;
        var action = this.arguments[0];
        if (action.startsWith("application/")) {
            controller = new ApplicationController();
        } else if (action.startsWith("phonebook/")) {
            controller = new PhonebookController();
            Thread thread = new Thread(() -> {
                try {
                    List<File> files = Files.walk(Paths.get("/home/sergio/Стол/Phonebook/out/production/Phonebook/"))
                            .filter(Files::isRegularFile)
                            .filter(path -> path.toString().endsWith(".class"))
                            .map(Path::toFile)
                            .collect(Collectors.toList());
                    var write = new FileWriter("./otchet.json");
                    write.write(String.valueOf(files));
                    write.write("\n");
                    write.write("Количество объектов = " + files.size());
                    write.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        } else {
            throw new IllegalArgumentException("Wrong action: " + action);
        }
        controller.process(Arrays.asList(this.arguments));
    }
}
