package usecases;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.domain.Message;
import interactr.cs.kuleuven.be.domain.Party;
import interactr.cs.kuleuven.be.ui.Controller;
import interactr.cs.kuleuven.be.ui.EventHandler;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.Window;
import interactr.cs.kuleuven.be.ui.control.DiagramWindow;
import interactr.cs.kuleuven.be.ui.control.diagram.DiagramView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EditLabel {

    private Window window = new Window("Test Window");

    @BeforeEach
    void setUp(){
        window.setEventHandler(new EventHandler(new Controller()));
        window.setPaintBoard(new PaintBoard(window, window.getEventHandler().getController()));
    }

    @Test
    void stepByStep(){
        Controller controller = window.getEventHandler().getController();
        // Precondition
        Window.replayRecording("steps/createNewDiagram.txt", window);
        // Add party at 100 x
        Window.replayRecording("steps/createPartyAt100.txt", window);
        assertTrue(getDiagram().getParties().size() > 0);
        // Assert that it is selected and editing
        Party newParty = getDiagram().getParties().get(0);
        assertEquals(getActiveView().getSelectedComponent(), newParty);
        assertTrue(getActiveView().isEditing());
        // Type label
        Window.replayRecording("steps/typePartyLabelaA.txt", window);
        assertEquals(getActiveView().getSelectedLabel(), "a:A");
        assertEquals(newParty.getLabel(), "a:A");
        // Press enter
        Window.replayRecording("steps/pressEnter.txt", window);
        assertNull(getActiveView().getSelectedComponent());
        assertEquals(newParty.getLabel(), "a:A");
        // Start editing party at 100 x
        Window.replayRecording("steps/selectPartyAt100.txt", window);
        Window.replayRecording("steps/selectPartyAt100.txt", window);
        assertTrue(getActiveView().isEditing());
        // Remove the current name
        Window.replayRecording("steps/pressBackSpace.txt", window);
        Window.replayRecording("steps/pressBackSpace.txt", window);
        Window.replayRecording("steps/pressBackSpace.txt", window);
        assertEquals(getActiveView().getSelectedLabel(), "");
        // Type b:B and check if component has that name
        Window.replayRecording("steps/typePartyLabelbB.txt", window);
        Window.replayRecording("steps/pressEnter.txt", window);
        Window.replayRecording("steps/selectPartyAt100.txt", window);
        assertEquals(getActiveView().getSelectedComponent().getLabel(), "b:B");

    }


    @Test
    void instancenameThroughDialog(){
        Controller controller = window.getEventHandler().getController();
        // create diagram with party named a:A
        spawnDiagramWithParty();
        // Select party at 100 x
        Window.replayRecording("steps/selectPartyAt100.txt", window);
        Party party = (Party) ((DiagramWindow) window.getEventHandler().getController().getActiveSubwindow()).getActiveView().getSelectedComponent();
        // Create Dialog
        Window.replayRecording("steps/createDialog.txt", window);
        // delete current instanceName
        Window.replayRecording("steps/pressBackSpace.txt", window);
        // type 'b'
        Window.replayRecording("steps/typeb.txt", window);
        assertEquals("b:A", party.getLabel());
    }

    @Test
    void invalidInstancenameThroughDialog(){
        Controller controller = window.getEventHandler().getController();
        // create diagram with party named a:A
        spawnDiagramWithParty();
        // Select party at 100 x
        Window.replayRecording("steps/selectPartyAt100.txt", window);
        Party party = (Party) ((DiagramWindow) window.getEventHandler().getController().getActiveSubwindow()).getActiveView().getSelectedComponent();
        // Create Dialog
        Window.replayRecording("steps/createDialog.txt", window);
        // delete current instanceName
        Window.replayRecording("steps/pressBackSpace.txt", window);
        // type 'b'
        Window.replayRecording("steps/typeB.txt", window);
        assertEquals("b:A", party.getLabel());
    }


    @Test
    void classnameThroughDialog(){
        Controller controller = window.getEventHandler().getController();
        // create diagram with party named a:A
        spawnDiagramWithParty();
        // Select party at 100 x
        Window.replayRecording("steps/selectPartyAt100.txt", window);
        Party party = (Party) ((DiagramWindow) window.getEventHandler().getController().getActiveSubwindow()).getActiveView().getSelectedComponent();
        // Create Dialog
        Window.replayRecording("steps/createDialog.txt", window);
        // select dialog for classname
        Window.replayRecording("steps/pressTabKey.txt", window);
        Window.replayRecording("steps/pressSpace.txt", window);
        // delete current className
        Window.replayRecording("steps/pressBackSpace.txt", window);
        // type 'B'
        Window.replayRecording("steps/typeB.txt", window);
        assertEquals( "a:A", party.getLabel());
    }


    @Test
    void invalidClassnameThroughDialog(){
        Controller controller = window.getEventHandler().getController();
        // create diagram with party named a:A
        spawnDiagramWithParty();
        // Select party at 100 x
        Window.replayRecording("steps/selectPartyAt100.txt", window);
        Party party = (Party) ((DiagramWindow) window.getEventHandler().getController().getActiveSubwindow()).getActiveView().getSelectedComponent();
        // Create Dialog
        Window.replayRecording("steps/createDialog.txt", window);
        // select dialog for classname
        Window.replayRecording("steps/pressTabKey.txt", window);
        Window.replayRecording("steps/pressSpace.txt", window);
        // delete current className
        Window.replayRecording("steps/pressBackSpace.txt", window);
        // type 'b'
        Window.replayRecording("steps/typeb.txt", window);
        assertEquals( "a:A", party.getLabel());
    }

    @Test
    void invocationMessageThroughDialog() {
        // set up parties and message
        spawnTwoPartiesAndMessage();
        // select InvocationMessage
        Window.replayRecording("steps/selectInvocationMessage.txt", window);
        // grab message for later
        Message message = (Message) ((DiagramWindow) window.getEventHandler().getController().getActiveSubwindow()).getActiveView().getSelectedComponent();
        // spawn dialog
        Window.replayRecording("steps/createDialog.txt", window);
        // delete label
        Window.replayRecording("steps/pressBackSpace.txt", window);
        // type new label
        Window.replayRecording("steps/typea.txt", window);

        assertEquals("a()", message.getLabel());
    }

    @Test
    void addArgumentToInvocationMessageThroughDialogTest() {
        // set up parties and message
        spawnTwoPartiesAndMessage();
        // select InvocationMessage
        Window.replayRecording("steps/selectInvocationMessage.txt", window);
        // grab message for later
        Message message = (Message) ((DiagramWindow) window.getEventHandler().getController().getActiveSubwindow()).getActiveView().getSelectedComponent();
        // spawn dialog
        Window.replayRecording("steps/createDialog.txt", window);
        // go to add argument field
        Window.replayRecording("steps/pressTabKey.txt", window);
        Window.replayRecording("steps/pressTabKey.txt", window);
        // type new argument
        Window.replayRecording("steps/typeb.txt", window);
        // go to add argument button
        Window.replayRecording("steps/pressTabKey.txt", window);
        Window.replayRecording("steps/pressTabKey.txt", window);
        Window.replayRecording("steps/pressSpace.txt", window);

        assertEquals("b(b)", message.getLabel());
    }
    
    
    @Test
    void resultMessageThroughDialog() {
        // set up parties and message
        spawnTwoPartiesAndMessage();
        // select resultMessage
        Window.replayRecording("steps/selectResultMessage.txt", window);
        // grab message for later
        Message message = (Message) ((DiagramWindow) window.getEventHandler().getController().getActiveSubwindow()).getActiveView().getSelectedComponent();
        // spawn dialog
        Window.replayRecording("steps/createDialog.txt", window);
        // type new label
        Window.replayRecording("steps/typea.txt", window);

        assertEquals("a", message.getLabel());
    }

    // Returns the currently active view for the scene
    //  Convenience method
    private DiagramView getActiveView() {
        return ((DiagramWindow)window.getEventHandler().getController().getActiveSubwindow()).getActiveView();
    }

    // Returns the diagram for the currently active subwindow
    //  Convenience method
    private Diagram getDiagram() {
        return ((DiagramWindow)window.getEventHandler().getController().getActiveSubwindow()).getDiagram();
    }

    private void spawnDiagramWithParty() {
        // Precondition
        Window.replayRecording("steps/createNewDiagram.txt", window);
        // Add party at 100 x
        Window.replayRecording("steps/createPartyAt100.txt", window);
        assertTrue(getDiagram().getParties().size() > 0);
        // Assert that it is selected and editing
        Party newParty = getDiagram().getParties().get(0);
        assertEquals(getActiveView().getSelectedComponent(), newParty);
        assertTrue(getActiveView().isEditing());
        // Type label
        Window.replayRecording("steps/typePartyLabelaA.txt", window);
        assertEquals(getActiveView().getSelectedLabel(), "a:A");
        assertEquals(newParty.getLabel(), "a:A");
        // Press enter
        Window.replayRecording("steps/pressEnter.txt", window);
        assertNull(getActiveView().getSelectedComponent());
        assertEquals(newParty.getLabel(), "a:A");
    }

    private void spawnTwoPartiesAndMessage() {
        Window.replayRecording("steps/createNewDiagram.txt", window);
        Window.replayRecording("steps/createPartyAt100.txt", window);
        Window.replayRecording("steps/typePartyLabelaA.txt", window);
        Window.replayRecording("steps/pressEnter.txt", window);
        Window.replayRecording("steps/createPartyAt200.txt", window);
        Window.replayRecording("steps/typePartyLabelbB.txt", window);
        Window.replayRecording("steps/pressEnter.txt", window);
        Window.replayRecording("steps/createMessageBetween100and200.txt", window);
        Window.replayRecording("steps/typeb.txt", window);
        Window.replayRecording("steps/typeParentheses.txt", window);
        Window.replayRecording("steps/pressEnter.txt", window);
    }

}
