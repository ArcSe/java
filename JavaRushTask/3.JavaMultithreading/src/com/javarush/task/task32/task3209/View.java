package com.javarush.task.task32.task3209;

import com.javarush.task.task32.task3209.listeners.FrameListener;
import com.javarush.task.task32.task3209.listeners.TabbedPaneChangeListener;
import com.javarush.task.task32.task3209.listeners.UndoListener;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame implements ActionListener {
    private Controller controller;
    private  JTabbedPane tabbedPane = new JTabbedPane(); //Панель с двумя вкладками
    private  JTextPane htmlTextPane = new JTextPane(); //Компонент для визуального редактирования html
    private JEditorPane plainTextPane = new JEditorPane(); //компонент для редактирования html в виде текста
    private UndoManager undoManager = new UndoManager();
    private UndoListener undoListener = new UndoListener(undoManager);

    public void undo(){
        try{
            undoManager.undo();}
        catch (Exception e){
            ExceptionHandler.log(e);
        }
    }

    public void update(){
        Document document = controller.getDocument();
        htmlTextPane.setDocument(document);
    }

    public void showAbout(){
        JOptionPane.showMessageDialog(this, "Information about app","info", JOptionPane.INFORMATION_MESSAGE);
    }
    public void selectHtmlTab(){
        tabbedPane.setSelectedIndex(0); // выбор html
        resetUndo();

    }

    public boolean isHtmlTabSelected(){
        return tabbedPane.getSelectedIndex() == 0;
    }

    public void redo(){
        try {
            undoManager.redo();
        }
        catch (Exception e){
            ExceptionHandler.log(e);
        }
    }
    public View() {
        try{
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch (Exception e){
             new ExceptionHandler(e);
        }
    }

    public void initMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        MenuHelper.initFileMenu(this, menuBar);
        MenuHelper.initEditMenu(this, menuBar);
        MenuHelper.initStyleMenu(this, menuBar);
        MenuHelper.initAlignMenu(this, menuBar);
        MenuHelper.initColorMenu(this, menuBar);
        MenuHelper.initFontMenu(this, menuBar);
        MenuHelper.initHelpMenu(this, menuBar);
        getContentPane().add(menuBar, BorderLayout.NORTH);


    }

    public void initEditor(){
        htmlTextPane.setContentType("text/html");
        JScrollPane jScrollPane =new JScrollPane(htmlTextPane);
        tabbedPane.addTab("HTML", jScrollPane);
        JScrollPane jScrollPane1 =new JScrollPane(plainTextPane);
        tabbedPane.addTab("Текст", jScrollPane1);
        tabbedPane.setPreferredSize(new Dimension());
        tabbedPane.addChangeListener(new TabbedPaneChangeListener(this));
        getContentPane().add(tabbedPane, BorderLayout.CENTER);
    }

    public UndoListener getUndoListener() {
        return undoListener;
    }

    public void resetUndo(){
        undoManager.discardAllEdits();
    }
    public boolean canRedo(){

        return undoManager.canRedo();
    }
    public boolean canUndo(){

        return undoManager.canUndo();
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String line = actionEvent.getActionCommand();
        switch (line){
            case "Новый":{
                controller.createNewDocument();
                break;
            }
            case "Открыть":{
                controller.openDocument();
                break;
            }
            case "Сохранить":{
                controller.saveDocument();
                break;
            }
            case  "Сохранить как...": {
                controller.saveDocumentAs();
                break;
            }
            case "Выход": {
                controller.exit();
                break;
            }
            case "О программе":{
                showAbout();
                break;
            }
        }

    }

    public void selectedTabChanged(){
        if(tabbedPane.getSelectedIndex() == 0){
            controller.setPlainText( plainTextPane.getText());
        }
        else if(tabbedPane.getSelectedIndex() == 1){
            plainTextPane.setText(controller.getPlainText());
        }
        resetUndo();
    }
    public void initGui(){
        initMenuBar();
        initEditor();
        pack();
    }

    public void init(){
        initGui();
        FrameListener listener = new FrameListener(this);
        this.addWindowListener(listener);
        this.setVisible(true);
    }
    public void exit(){
        controller.exit();
    }
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Controller getController() {
        return controller;
    }
}
