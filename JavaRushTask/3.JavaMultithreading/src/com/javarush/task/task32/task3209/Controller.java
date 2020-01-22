package com.javarush.task.task32.task3209;

import com.javarush.task.task32.task3209.listeners.UndoListener;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.io.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Controller {
    private View view;
    private HTMLDocument document;
    private File currentFile;

    public String getPlainText(){
        StringWriter writer = new StringWriter();
        HTMLEditorKit editorKit = new HTMLEditorKit();
        try {
            editorKit.write(writer, document, 0, document.getLength());
        }
        catch (Exception e){
            ExceptionHandler.log(e);
        }
        return writer.toString();
    }
    public void setPlainText(String text){
        resetDocument();
        StringReader reader = new StringReader(text);
        HTMLEditorKit editor = new HTMLEditorKit();
        try {
            editor.read(reader, document, 0);
        }
        catch (Exception e){
            ExceptionHandler.log(e);
        }
    }

    public void resetDocument(){
       if(document != null) {
           UndoListener listener = view.getUndoListener();
           document.removeUndoableEditListener(listener);
       }
           HTMLEditorKit kit = new HTMLEditorKit();
           document = (HTMLDocument) kit.createDefaultDocument();
           document.addUndoableEditListener(view.getUndoListener());
           view.update();

    }
    public HTMLDocument getDocument() {
        return document;
    }

    public  void exit(){
        System.exit(0);
    }
    public void init(){
        createNewDocument();
    }

    public Controller(View view) {
        this.view = view;
    }

    public static void main(String[] args) {
        View view  = new View();
        Controller controller = new Controller(view);
        view.setController(controller);
        view.init();
        controller.init();


    }

    public void saveDocumentAs() {
        view.selectHtmlTab();
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new HTMLFileFilter());
        chooser.showDialog(view, "Save File");
        if(chooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION){
            currentFile = chooser.getSelectedFile();
            view.setTitle(currentFile.getName());
            try {
                HTMLEditorKit editorKit = new HTMLEditorKit();
                FileWriter writer = new FileWriter(currentFile);
                editorKit.write(writer, document, 0, document.getLength());
                writer.close();
            } catch (Exception e) {
                ExceptionHandler.log(e);
            }
        }
    }

    public void saveDocument() {
        view.selectHtmlTab();
        if (currentFile == null)
            saveDocumentAs();
        else {
            try {
                view.setTitle(currentFile.getName());
                FileWriter writer = new FileWriter(currentFile);
                new HTMLEditorKit().write(writer, document, 0, document.getLength());
            } catch (Exception e) {
                ExceptionHandler.log(e);
            }
        }
    }

    public void openDocument() {
        view.selectHtmlTab();
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new HTMLFileFilter());
        if(chooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION){
            currentFile = chooser.getSelectedFile();
            view.setTitle(currentFile.getName());
            resetDocument();
            try {
                HTMLEditorKit editorKit = new HTMLEditorKit();
                FileReader reader = new FileReader(currentFile);
                editorKit.read(reader, document, 0);
                view.resetUndo();
                reader.close();
            } catch (Exception e) {
                ExceptionHandler.log(e);
            }
        }
    }

    public void createNewDocument() {
        view.selectHtmlTab();
        resetDocument();
        view.setTitle("HTML редактор");
        view.resetUndo();
        currentFile = null;
    }
}
