package interactr.cs.kuleuven.be.ui.command;

/**
 * An interface for command handlers. Each such handler can execute or undo commands.
 *  The default implementation for execution/undoing throws an exception.
 *
 *  This is an 'Element' in the 'Visitor' pattern.
 *  Each 'Element' can pass commands down a 'Chain of Responsibility' if it so desires.
 *  The number of types of command handlers is assumed to remain small.
 *
 * @author Team 25
 * @version 1.0
 */
public interface CommandHandler {

    /**
     * Returns the next handler for this handler.
     *
     * @return The next handler in the chain of responsibility of this handler.
     */
    default CommandHandler nextHandler() {
        return null;
    }

    /**
     * Undo the given command.
     *
     * @param command The command that is to be undone.
     */
    default void undoCommand(Command command) throws CommandNotProcessedException {
        throw new CommandNotProcessedException();
    }

    /**
     * Execute the given command.
     *
     * @param command The command that is to be processed.
     */
    default void executeCommand(Command command) throws CommandNotProcessedException {
        throw new CommandNotProcessedException();
    }

}