package View;

import Model.Stopwatch;
import java.util.List;

public class View {

    public void displayCommandQuestion() {
        System.out.println("Command?");
    }

    public void displayThreadsInfo(List<Stopwatch> container) {
        for (Stopwatch element : container) {
            System.out.println(element.getName() + " ID:" +  element.getId() + " Duration: " + element.getDuration());
        }
    }

    public void displayThreadsInfo(String name, List<Stopwatch> container) {
        for (Stopwatch element : container) {
            if(element.getName().equals(name)) {
                System.out.println(element.getName()  + " ID:" +  element.getId() + " Duration: " + element.getDuration());
            }
        }
    }
}
