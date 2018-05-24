package interactr.cs.kuleuven.be.domain;

/**
 * A class of result messages.
 *
 * @author Team 25
 * @version 2.0
 */
public class InvocationMessage extends Message {

    /**
     * Initialize this invocation message with given sending and receiving party.
     *
     * @param sender The sender of this invocation message.
     * @param receiver The receiver of this invocation message.
     */
    public InvocationMessage(Party sender , Party receiver) {super("message()", sender, receiver);}

    @Override
    public boolean canHaveAsLabel(String label) {
        if (!super.canHaveAsLabel(label))
            return false;
        if (!canHaveAsMethodName(methodNameInLabel(label)))
            return false;
        for (String argument : argumentsInLabel(label))
            if (!canHaveAsArgument(argument))
                return false;
        return label.endsWith(")");
    }

    /**
     * Returns the method name in this message's label.
     *
     * @return The method name of this message's label.
     */
    public String getMethodName() {
        return methodNameInLabel(getLabel());
    }

    /**
     * Returns the arguments in this message's label.
     *
     * @return The argument list of this message's label.
     */
    public String[] getArguments() {
        return argumentsInLabel(getLabel());
    }

    /**
     * Returns the method name in the given label.
     *
     * @param label The label having the desired method name.
     * @return All characters before the first open parenthesis in the given label, or null if there aren't any.
     */
    private String methodNameInLabel(String label) {
        String[] parts = label.split("\\(");
        if (parts.length == 2)
            return parts[0];
        return null;
    }

    /**
     * Returns the arguments in the given invocation message label.
     *
     * @param label The label having the desired argument list.
     * @return A list of strings having the arguments in the given label. The list can be empty, but not null.
     */
    private String[] argumentsInLabel(String label) {
        String[] parts = label.split("\\(");
        if (parts.length == 2) {
            if (parts[1].endsWith(")") && parts[1].length() > 1)
                return parts[1].substring(0, parts[1].length() - 1).split(",",-1);
        }
        return new String[0];
    }

    /**
     * Checks whether the given method name is a valid method name for this invocation message.
     *
     * @param methodName The method name for this invocation message.
     * @return True if and only if the given string starts with a lowercase letter and consists of
     *      only letters, digits and underscores.
     */
    public boolean canHaveAsMethodName(String methodName) {
        if (methodName == null || methodName.length() == 0)
            return false;
        if (!Character.isLowerCase(methodName.charAt(0)) || !Character.isLetter(methodName.charAt(0)))
            return false;
        for (int i=1 ; i<methodName.length() ; i++) {
            char currentChar = methodName.charAt(i);
            if (!Character.isLetter(currentChar) && !Character.isDigit(currentChar) && currentChar != '_')
                return false;
        }
        return true;
    }

    /**
     * Checks whether or not the given argument is a valid argument for this invocation message.
     *
     * @param argument The argument to check.
     * @return True if and only if the given argument has non-zero length and consists of anything but
     *      commas and parentheses.
     */
    public boolean canHaveAsArgument(String argument) {
        if (argument == null || argument.length() == 0)
            return false;
        for (int i=0 ; i<argument.length() ; i++) {
            char currentChar = argument.charAt(i);
            if (currentChar == ',' || currentChar == '(' || currentChar == ')')
                return false;
        }
        return true;
    }

    @Override
    public void acceptVisitor(DiagramVisitor visitor) {
        visitor.visit(this);
    }

}