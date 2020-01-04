package com.groupOne;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * This class created to start the application (Guess My Number Battle)
 *
 * @author COSC2658: Data Strucure - Group 1
 * Dong Nguyen: s3634096
 * Duc Ho:      s
 * Thuan Trang: s
 * Danh Le:     s
 *
 */

public class StartApplication extends Application {

    /**Name: start
     **Purpose: execute UI when the class startApplication is run by user or in other word the application is started
     **Passed: The UI is config correctly
     **Returns: void
     **Input: Stage // Output: Void
     **Effect: This methods will help to open UI when the application run
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("./view/gameScreen.fxml"));

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**Name: main
     **Purpose: execute additional required function to run the application correctly
     * Example: Launch application
     **Passed: All the code is executed successfully
     **Returns: void
     **Input: void // Output: void
     **Effect: This method help to config UI behavior before showing to user
     */
    public static void main(String[] args) {
        launch(args);
    }
}

