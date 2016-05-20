package cscie55.hw5.bank;

import cscie55.hw5.bank.command.Command;

import java.util.Queue;

/**
 * @Author ssurabhi on 11/20/15.
 * @version 1.0
 */
public class CommandExecutionThread extends Thread {

    private Bank bank;
    private Queue<Command> commandQueue;
    private boolean executeCommandInsideMonitor;

    /**
     * constructor.
     * @param bank,commandQueue,executeCommandInsideMonitor.
     */
    public CommandExecutionThread(Bank bank, Queue<Command> commandQueue, boolean executeCommandInsideMonitor) {
        this.bank = bank;
        this.commandQueue = commandQueue;
        this.executeCommandInsideMonitor = executeCommandInsideMonitor;
    }

    public void run() {

        while (true) {
            Command command;
            synchronized (commandQueue) {
                while (commandQueue.isEmpty()) {
                    try {
                        commandQueue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    commandQueue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                command = commandQueue.remove();
                commandQueue.notifyAll();
                if (command.isStop()) {
                    return;
                }
                if (executeCommandInsideMonitor) {
                    try {
                        command.execute(bank);
                    } catch (InsufficientFundsException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (!executeCommandInsideMonitor) {
                try {
                    command.execute(bank);
                } catch (InsufficientFundsException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
