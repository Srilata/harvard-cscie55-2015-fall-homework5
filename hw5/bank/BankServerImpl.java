package cscie55.hw5.bank;

import cscie55.hw5.bank.command.Command;
import cscie55.hw5.bank.command.CommandDeposit;
import cscie55.hw5.bank.command.CommandStop;

import java.util.Queue;

import java.util.concurrent.*;

/**
 * @Author ssurabhi on 11/20/15.
 * @version 1.0
 */
public class BankServerImpl implements BankServer {

    private Bank bank;
    private CommandExecutionThread[] threads;
    public static Queue<Command> commandQueue = new ArrayBlockingQueue<Command>(1000000);

    /**
     * constructor.
     * @param bank,SERVER_THREADS,executeCommandInsideMonitor.
     */
    public BankServerImpl(Bank bank,int SERVER_THREADS,boolean executeCommandInsideMonitor)  {
        this.bank = bank;
        threads = new CommandExecutionThread[SERVER_THREADS];
        for (int i = 0; i < SERVER_THREADS; i++) {
            threads[i] = new CommandExecutionThread(bank, commandQueue, executeCommandInsideMonitor);
        }

        // Start the test threads
        for (CommandExecutionThread thread : threads) {
            thread.start();
        }

    }

    /**
     * Executes the given Command.
     * @param command the Command to be executed.
     */
    @Override
    public void execute(Command command) {
        if(command instanceof CommandDeposit){
            try {
                command.execute(bank);
            } catch (InsufficientFundsException e) {
                e.printStackTrace();
            }
        }
        commandQueue.add(command);
    }

    /**
     * Stop the BankServer. No further Commands will be executed.
     * @throws InterruptedException
     */
    @Override
    public void stop() throws InterruptedException {
        Command cStop = new CommandStop();
        commandQueue.add(cStop);
    }

    /**
     * Returns the total of all Account balances for all Accounts managed by the BankServer's Bank.
     * @return The total of all Account balances for all Accounts managed by the BankServer's Bank.
     */
    @Override
    public long totalBalances() {
       return bank.totalBalances();
    }

}
