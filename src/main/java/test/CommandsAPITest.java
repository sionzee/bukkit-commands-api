package test;

import cz.sionzee.commandsapi.CommandsAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class CommandsAPITest extends JavaPlugin {

    Queue<TestContainer> list;
    TestContainer currentTest;

    @Override
    public void onEnable() {
        list = new ArrayDeque<TestContainer>(20);
        Bukkit.getServer().getLogger().addHandler(new Handler() {
            @Override
            public void publish(LogRecord record) {
                if(record.getLevel() == Level.CONFIG && currentTest != null) {
                    String message = record.getMessage();
                    if(message.contains("UNIT:")) {
                        String msg = message.substring(message.indexOf("UNIT:") + "UNIT:".length());
                        currentTest.onLog(msg);
                    }
                }
            }
            @Override public void flush() {}
            @Override public void close() throws SecurityException { }
        });

        CommandsAPI.INSTANCE.registerCommand("test", TestCommand.class);

        Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {

            addTest("Testing base command", "/test", "onCommand Executed");
            addTest("Testing subcommand without args", "/test test", "onCommand Executed", "onCommand Test()");
            addTest("Testing subcommand with args (test)", "/test test2 test", "onCommand Executed", "onCommand Test2(test)");

            println("..............Executing CommandsAPI Tests..............");
            startTest();

        }, 20L * 2);
    }

    void startTest() {
        currentTest = list.poll();
        currentTest.execute();
    }

    public void addTest(String name, String command, String... output) {
        list.add(new TestContainer(name, command, output));
    }

    public static void println(String text){
        System.out.println(text);
    }

    private class TestContainer {
        int index;
        String[] exceptedOutput = {};
        String testName = "";
        String command = "";
        boolean error;

        public TestContainer(String testName, String command, String[] exceptedOutput) {
            this.index = 0;
            this.exceptedOutput = exceptedOutput;
            this.command = command;
            this.testName = testName;
            this.error = false;
        }

        public void execute() {
            println("Invoking command \"" + command + "\", excepted output:\n" + String.join("\n", exceptedOutput));
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        }

        public void onLog(String message) {
            if(!message.equalsIgnoreCase(exceptedOutput[index])) error = true;
            if(!onDone())
                index++;
        }

        public boolean onDone() {
            if(exceptedOutput.length -1 == index) {
                if(!error) println("TEST FOR " + testName + " was successful.");
                else println("TEST FOR " + testName + " was unsuccessful.");
                if(list.size() > 0) startTest();
                return true;
            }
            return false;
        }
    }
}
