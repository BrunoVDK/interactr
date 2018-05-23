package interactr.cs.kuleuven.be.ui.command;

import interactr.cs.kuleuven.be.exceptions.InvalidAddCharException;
import interactr.cs.kuleuven.be.exceptions.InvalidLabelException;
import interactr.cs.kuleuven.be.ui.control.DialogWindow;
import interactr.cs.kuleuven.be.ui.control.diagram.DiagramView;

/**
 * A class of commands for appending a char to an active component's label.
 *
 * @author Team 25
 * @version 1.0
 */
public class AppendCharCommand extends Command {

    /**
     * Initialize this new append char command with given char to append.
     *
     * @param c The char that is to be appended.
     */
    public AppendCharCommand(char c) {
        this.c = c;
    }

    /**
     * Returns whether or not the given char is a valid char to append.
     *
     * @param c The char to check with.
     * @return True if and only if the char is a letter, a digit or part of a valid set of chars.
     *          This valid set includes underscores, brackets, ampersands, ...
     */
    boolean canHaveAsChar(char c) {
        return (Character.isLetter(c)
                || Character.isDigit(c)
                || ":();-_<>*&[]".contains(Character.toString(c)));
    }

    /**
     * Registers the char that is to be appended.
     */
    private char c;

    @Override
    public void executeDiagramView(DiagramView view) {
        try {
            view.setSelectedLabel(view.getSelectedLabel() + this.c);
        }
        catch (InvalidLabelException ignored) {}
    }

    @Override
    public void executeDialogWindow(DialogWindow dialog) {
        try {
            dialog.appendChar(this.c);
        }
        catch (InvalidAddCharException e) {
            throw new CommandNotProcessedException();
        }
    }
}