package nodepad;

import java.io.BufferedReader;
import javafx.event.EventHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.IndexRange;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Nodepad extends Application {
   
    // Scene ---> root(main pane) ---> pane1(bar)---> number of Menus(file,edit,help)---> each menu contain MenuItem
    Scene scene;
    BorderPane root;   
    MenuBar bar;
    Menu file,edit,help;
    TextArea textarea;
    //MenuItems
    MenuItem New,open,save,exit;
    MenuItem undo,cut,copy,paste,delete,selectAll;
    MenuItem about;
    
    FileChooser filechooser;
    File file1;
    Alert alert;
    
    @Override
    public void init(){
        //Items of file menu
        New=new MenuItem("New");
        open=new MenuItem("Open");
        save=new MenuItem("Save");
        exit=new MenuItem("Exit");
        //constructors
        file=new Menu("File");
        bar=new MenuBar();
        root=new BorderPane();
        scene=new Scene(root,400,400); 
        //Items of edit menu
        undo=new MenuItem("Undo");
        cut=new MenuItem("Cut");
        copy=new MenuItem("Copy");
        paste=new MenuItem("Paste");
        delete=new MenuItem("Delete");
        selectAll=new MenuItem("SelectAll");
        //Items of Help Menu
        about=new MenuItem("About");
        //Edit Constructor
        edit=new Menu("Edit");
        help=new Menu("Help");
        //TextArea
        textarea=new TextArea();
        /*textarea=new TextArea(10,20); no of rows and columns*/
        filechooser=new FileChooser();
       
        
    }
    @Override
    public void start(Stage primaryStage){
         file.getItems().addAll(New,open,save,exit);
         bar.getMenus().addAll(file);
         root.setTop(bar);
       //Add edit Menu to bar
        edit.getItems().addAll(undo,cut,copy,paste,delete,selectAll);
        help.getItems().addAll(about);
        bar.getMenus().addAll(edit,help);
        //Adding TextArea (Pane2) to MainPane(root)
          /*textarea.setMaxSize(600, 900);*/
        textarea.setPrefColumnCount(60);
        textarea.setPrefRowCount(20);
        root.setCenter(textarea);
        
        //Key Combinations
        New.setAccelerator(KeyCombination.keyCombination("Alt+n"));
        open.setAccelerator(KeyCombination.keyCombination("Alt+o"));
        save.setAccelerator(KeyCombination.keyCombination("Alt+s"));
        exit.setAccelerator(KeyCombination.keyCombination("Alt+e"));
        undo.setAccelerator(KeyCombination.keyCombination("Alt+u"));
        copy.setAccelerator(KeyCombination.keyCombination("Alt+c"));
        cut.setAccelerator(KeyCombination.keyCombination("Alt+x"));
        paste.setAccelerator(KeyCombination.keyCombination("Alt+p"));
        selectAll.setAccelerator(KeyCombination.keyCombination("Alt+a"));
        delete.setAccelerator(KeyCombination.keyCombination("Alt+d"));
        
        //Event Handlers of File Menu
        open.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            file1=filechooser.showOpenDialog(primaryStage);
//            file1.getAbsolutePath()
            if(file1 != null)
            {
//              textarea.appendText(file1.getName());
                FileInputStream fis = null;
                int size;
                byte[] b;
                try {
                    fis = new FileInputStream(file1);
//                    System.out.println(fis);
                    size = fis.available();
                    b = new byte[size];
                    fis.read(b);
                    textarea.setText(new String(b));                 
                } catch (IOException ex) {
                    Logger.getLogger(Nodepad.class.getName()).log(Level.SEVERE, null, ex);
                }
                finally{
                    try {
                        fis.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Nodepad.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } 
            }
         });
        save.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            file1=filechooser.showSaveDialog(primaryStage);
            if(file != null)
            {
//                textarea.appendText(file1.getName());
                    FileOutputStream fis = null;  
                    byte[] b;
                try {
                    fis = new FileOutputStream(file1);
                    b = textarea.getText().getBytes();
                    fis.write(b);
                    
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Nodepad.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Nodepad.class.getName()).log(Level.SEVERE, null, ex);
                }
                 finally{
                    try { 
                        fis.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Nodepad.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } 
               
            }
         });
         exit.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            Platform.exit();
         });
         New.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            textarea.clear();
         });
         //Event Handler of Help Menu
         about.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
                try{
//                    alert=new Alert(AlertType.NONE);   //Initialized here to prevent exceptions
//                    alert.setAlertType(AlertType.INFORMATION);
//                    alert.setContentText("Microsoft Windows,Version 1909");
//                    alert.show();
                       final Stage dialog = new Stage();
                        dialog.setTitle("  About me ");
                        Text t = new Text("This NotePad Created by Mayar Hassan ,"
                                + "\n in 3/2/2021 1:44pm"
                                + "\n Gmail : mayar7assan999@gamil.com"
                                + "\n LinkedIn :linkedin.com/in/mayar-hassan-969380127/"
                                + "\n GoodBye ");
                        dialog.initModality(Modality.APPLICATION_MODAL);
                        dialog.initOwner(primaryStage);
                        VBox dialogVbox = new VBox(20);
                        dialogVbox.getChildren().add(t);
                        Scene dialogScene = new Scene(dialogVbox, 350, 150);
                        dialog.setScene(dialogScene);
                        dialog.show();
                }
                catch(Exception e){
                     e.printStackTrace();
                }    
         });
         
         //Event Handlers of Edit Menu
         cut.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            textarea.cut();
         });
         copy.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            textarea.copy();
         });
         paste.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            textarea.paste();
         });
         delete.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
             IndexRange selection=textarea.getSelection();
             int start=selection.getStart();
             int end=selection.getEnd();
             textarea.deleteText(start,end);
         });
        undo.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            textarea.undo();
         });
        selectAll.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            textarea.selectAll();
         });
        //Nodes IDs
        bar.setId("menubar");
        file.setId("menu");
        edit.setId("menu");
        help.setId("menu");
        textarea.setId("context-menu");

        try{
             scene.getStylesheets().add(getClass().getResource("notepad.css").toString());
        }
        catch(Exception e){
             System.out.println(e);
        } 
        
         
        primaryStage.setTitle("NodePad++");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        Application.launch(args);
    }
    
}
