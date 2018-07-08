package Controller;

import Model.Stopwatch;
import Tools.InputGetter;
import View.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ThreadController {
    private static final int THREAD_NAME_INDEX = 1;
    private static final int COMMAND_INDEX = 0;

    private List<Stopwatch> container;
    private InputGetter input;
    private View view;

    public ThreadController() {
        this.view = new View();
        this.input = new InputGetter();
        this.container = new ArrayList<>();
    }

    public void runApp() {
        boolean isAppEnd = false;

        while (!isAppEnd) {
            view.displayCommandQuestion();
            String[] commands = input.takeInputFromUser().split("\\W");

            if (commands[COMMAND_INDEX].equals("start") && commands.length > 1) {
                initializeNewStopwatch(commands);
            }

            if (commands[COMMAND_INDEX].equals("check")) {
                if (commands.length < 2) {
                    view.displayThreadsInfo(container);
                }
                else {
                    view.displayThreadsInfo(commands[THREAD_NAME_INDEX], container);
                }
            }

            if (commands[COMMAND_INDEX].equals("pause")) {
                if (commands.length > 1) {
                    pauseStopwatchThread(commands);

                }
            }

            if (commands[COMMAND_INDEX].equals("stop")) {
                if (commands.length > 1) {
                    Stopwatch currentThread = findThread(commands[THREAD_NAME_INDEX]);

                    if (currentThread != null) {
                        deleteThreadFromContainer(commands[THREAD_NAME_INDEX]);
                        currentThread.interrupt();
                    }
                }
            }

            if (commands[COMMAND_INDEX].equals("exit")) {
                stopAllRunningThreads();
                isAppEnd = true;
            }
        }
    }

    private void initializeNewStopwatch(String[] commands) {
        String threadName = commands[THREAD_NAME_INDEX];
        Stopwatch stopwatch = new Stopwatch();

        stopwatch.setName(threadName);
        container.add(stopwatch);

        stopwatch.start();
    }

    private void pauseStopwatchThread(String[] commands) {
        Stopwatch thread = findThread(commands[THREAD_NAME_INDEX]);

        if (thread != null) {
            if (!thread.isPause()) {
                thread.setPause();
            }
            else {
                thread.setUnPause();
            }
        }
        else {
            throw new IllegalArgumentException("There is no thread of this name!");
        }
    }

    private Stopwatch findThread(String name) {
        for (Stopwatch element : container) {
            if (element.getName().equals(name)) {
                return element;
            }
        }
        return null;
    }

    private void deleteThreadFromContainer(String name) {
        Iterator<Stopwatch> iterator = container.iterator();

        while(iterator.hasNext()) {
            Stopwatch element = iterator.next();

            if (element.getName().equals(name)) {
                iterator.remove();
            }
        }
    }

    private void stopAllRunningThreads() {
        Iterator<Stopwatch> iterator = container.iterator();

        while(iterator.hasNext()) {
            Stopwatch element = iterator.next();

            element.interrupt();
            iterator.remove();
            }
    }
}


