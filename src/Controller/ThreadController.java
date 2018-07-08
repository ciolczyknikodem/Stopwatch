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
    
    private static final String start = "start";
    private static final String check = "check";
    private static final String pause = "pause";
    private static final String stop = "stop";
    private static final String exit = "exit";

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
            String[] userInput = input.takeInputFromUser().split("\\W");

            String command = userInput[COMMAND_INDEX];

            String threadName = null;
            if (userInput.length > 1) {
                threadName = userInput[THREAD_NAME_INDEX];
            }

            if (command.equals(start) && threadName != null) {
                initializeNewStopwatch(threadName);
            }

            if (command.equals(check)) {
                if (threadName == null) {
                    view.displayThreadsInfo(container);
                }
                else {
                    view.displayThreadsInfo(threadName, container);
                }
            }

            if (command.equals(pause) && threadName != null) {
                try {
                    pauseStopwatchThread(threadName);
                }
                catch (IllegalArgumentException e) {
                    System.err.println(e.getMessage());
                }
            }

            if (command.equals(stop)) {
                if (userInput.length > 1) {
                    Stopwatch currentThread = findThread(userInput[THREAD_NAME_INDEX]);

                    if (currentThread != null) {
                        deleteThreadFromContainer(userInput[THREAD_NAME_INDEX]);
                        currentThread.interrupt();
                    }
                }
            }

            if (userInput[COMMAND_INDEX].equals(exit)) {
                stopAllRunningThreads();
                isAppEnd = true;
            }
        }
    }

    private void initializeNewStopwatch(String threadName) {
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.setName(threadName);

        container.add(stopwatch);
        stopwatch.start();
    }

    private void pauseStopwatchThread(String name) {
        Stopwatch thread = findThread(name);

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


