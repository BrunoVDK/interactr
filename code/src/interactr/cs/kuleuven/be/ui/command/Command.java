package interactr.cs.kuleuven.be.ui.command;

import interactr.cs.kuleuven.be.ui.control.DiagramView;
import interactr.cs.kuleuven.be.ui.control.Dialog;
import interactr.cs.kuleuven.be.ui.control.SubWindow;

/**
 * A class of commands, representing requests that can be executed or undone.
 *
 *  This is a 'Visitor' in the 'Visitor' pattern.
 *  Each subclass does not need to
 *
 * @author Team 25
 * @version 1.0
 */
public class Command {

    /**
     * Sets the receiver for this command.
     *
     * @param receiver The new receiver for this command.
     */
    void setReceiver(CommandHandler receiver) {
        this.receiver = receiver;
    }

    /**
     * Registers the receiver for this command.
     */
    private CommandHandler receiver = null;

    /**
     * Execute this command with the given receiver.
     *
     * @param receiver The receiver for which the command should be executed.
     * @throws CommandNotProcessedException If the command could not be executed.
     */
    public final void execute(CommandHandler receiver) throws CommandNotProcessedException {
        try {
            receiver.executeCommand(this);
        }
        catch (CommandNotProcessedException e) {
            if (receiver.nextHandler() != null)
                execute(receiver.nextHandler());
            else
                throw e;
        }
        setReceiver(receiver);
    }

    /**
     * Execute this command in the given subwindow.
     *
     * @param window The subwindow in which the command should be executed.
     * @throws CommandNotProcessedException the command could not be executed by the given receiver.
     */
    public void executeSubWindow(SubWindow window) {
        System.out.println("window");throw new CommandNotProcessedException();}

    /**
     * Execute this command in the given diagram view.
     *
     * @param view The view in which the command should be executed.
     * @throws CommandNotProcessedException the command could not be executed by the given receiver.
     */
    public void executeDiagramView(DiagramView view) {
        System.out.println("view");throw new CommandNotProcessedException();}

    /**
     * Execute this command in the given dialog.
     *
     * @param dialog The dialog in which the command should be executed.
     * @throws CommandNotProcessedException the command could not be executed by the given receiver.
     */
    public void executeDialog(Dialog dialog) {throw new CommandNotProcessedException();}

    /**
     * Undo this command.
     *
     * @throws CommandNotProcessedException The command could not be undone.
     */
    public final void undo() throws CommandNotProcessedException {
        if (receiver == null)
            throw new CommandNotProcessedException();
        receiver.undoCommand(this);
    }

    /**
     * Undo this command in the given subwindow.
     *
     * @param window The subwindow in which the command should be undone.
     * @throws CommandNotProcessedException the command could not be executed by the given receiver.
     */
    public void undoSubWindow(SubWindow window) {throw new CommandNotProcessedException();}

    /**
     * Undo this command in the given diagram view.
     *
     * @param view The view in which the command should be undone.
     * @throws CommandNotProcessedException the command could not be executed by the given receiver.
     */
    public void undoDiagramView(DiagramView view) {throw new CommandNotProcessedException();}

    /**
     * Undo this command in the given dialog.
     *
     * @param dialog The dialog in which the command should be undone.
     * @throws CommandNotProcessedException the command could not be executed by the given receiver.
     */
    public void undoDialog(Dialog dialog) {throw new CommandNotProcessedException();}

}