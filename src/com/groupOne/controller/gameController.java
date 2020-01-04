package com.groupOne.controller;
import com.groupOne.model.Result;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
/**
 * This class created to handle all events from game UI as well as logic
 *
 * @author COSC2658: Data Strucure - Group 1
 * Dong Nguyen: s3634096
 * Duc Ho:      s
 * Thuan Trang: s
 * Danh Le:     s
 *
 */

public class gameController implements Initializable {
    // Initialize all UI components that created in gameScreen.fxml UI file
    @FXML
    PasswordField snumberField;
    @FXML
    TextField gnumberField;
    @FXML
    TextField percentField;
    @FXML
    TableView<Result> resultTable;
    @FXML
    TableColumn<Result, Integer> stepColumn;
    @FXML
    TableColumn<Result, Integer> numberColumn;
    @FXML
    TableColumn<Result, Integer> strikeColumn;
    @FXML
    TableColumn<Result, Integer> hitColumn;
    @FXML
    TableColumn<Result, Integer> missColumn;
    @FXML
    TextField firstgField;
    @FXML
    ComboBox<Integer> strikeBox;
    @FXML
    ComboBox<Integer> hitBox;
    @FXML
    ComboBox<Integer> missBox;
    @FXML
    Button guessBtn;
    @FXML
    Button nextGuessBtn;
    @FXML
    Button resBtn;

    // Initialize all required global variables
    private String snumber, gnumber, firstGuess, nextGuess, percent ;
    private int stepsNumber = 0;
    private  List<Integer> snumberList = new ArrayList<>();
    private  List<Integer> infoList = new ArrayList<>();
    private  List<Integer> rivalGuessList = new ArrayList<>();
    private  List<Integer> ourGuessList = new ArrayList<>();
    private  List<Result> resultList = new ArrayList<>();
    private  List<Integer> testsNumber = new ArrayList<>();
    private  List<Integer> testfGuessNumber = new ArrayList<>();
    private  static List<String> holder = new ArrayList<>();
    private  static List<String> tempHolder = new ArrayList<>();
    private ObservableList<Result> resultData ;
    private Boolean isFirst = true;
    private List<Integer>  testGuess = null;

    /**Name: initialize
     **Event: When the gameScreen.fxml is loaded
     **Purpose: Setting UI component before showing to user
     **Passed: All UI components named correctly
     **Returns: void
     **Input: void // Output: The Guess Number Battle UI -  the gameScreen.fxml is loaded successfully
     **Effect: The method help user to additionally config UI
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set text alignment for text field
        snumberField.setAlignment(Pos.CENTER);
        gnumberField.setAlignment(Pos.CENTER);
        firstgField.setAlignment(Pos.CENTER);
        percentField.setAlignment(Pos.CENTER);

        // Set text alignment for table column;
        stepColumn.setStyle("-fx-alignment: CENTER;");
        numberColumn.setStyle("-fx-alignment: CENTER;");
        strikeColumn.setStyle("-fx-alignment: CENTER;");
        hitColumn.setStyle("-fx-alignment: CENTER;");
        missColumn.setStyle("-fx-alignment: CENTER;");

        // Set place holder
        gnumberField.setPromptText("Enter a number with 4 digits");
        resultTable.setPlaceholder(new Label("Make a guess to gain result"));

        // Set value for comboBox
        Integer[] number = new Integer[]{0,1,2,3,4,5,6,7,8,9};
        strikeBox.getItems().addAll(number);
        hitBox.getItems().addAll(number);
        missBox.getItems().addAll(number);
        strikeBox.setValue(0);
        hitBox.setValue(0);
        missBox.setValue(0);


        // Set default first guess number
        testfGuessNumber.add(3);
        testfGuessNumber.add(2);
        testfGuessNumber.add(1);
        testfGuessNumber.add(0);

    }

// -------------------------------------All EVENT FUNCTIONS START FROM HERE-------------------------------------------//
    /**Name: generateClicked
     **Event: When the GET SECRET NUMBER button pressed by user
     **Purpose: generate a secret number
     **Passed: All code line perform without errors
     **Returns: Void
     **Input: event triggered // Output: The created secret number showed in UI in password form
     **Effect: This method help user generate a secret number by interacting with  UI
     */
    public void generateClicked(ActionEvent actionEvent) {
        // Generate secret number
        snumberList =  generateNumber();
        // Transform secret number to string type
        snumber = snumberList.get(0) + "" + snumberList.get(1) + "" + snumberList.get(2) + "" +snumberList.get(3);
        // Show secret number in UI
        snumberField.setText(snumber);
    }
    /**Name: resultClicked
     **Event: When the GET RESULT button pressed by user
     **Purpose: get the result from rival guess
     **Passed: All code line perform without errors
     **Returns: Void
     **Input: event triggered // Output: The result are showed in table located in right side of UI
     **Effect: This method help user get and view the result of rival guess through UI
     */
    public void resultClicked(ActionEvent actionEvent) {
        // Get user input
        gnumber = gnumberField.getText();
        // Increase steps
        stepsNumber++;
        // Condition whether the guess number from rival is valid or not
        // IF YES
        if(isValidNumber(gnumber)) {
            // Transform type
            rivalGuessList = stringTransform(gnumber);
            // Get result from user input
            infoList = getResult(snumberList, rivalGuessList);
            // Load table to show result in UI
            resultList.add(new Result(stepsNumber, gnumber, infoList.get(0), infoList.get(1), infoList.get(2)));
            loadTable();
            // Check if the rival already won the game !!!
            if(infoList.get(0) == 4) {
                resBtn.setDisable(true);
                resBtn.setText("They won the game :((");
            }
            // Reset all list
            rivalGuessList = new ArrayList<>();
            infoList = new ArrayList<>();
        // IF NOT
        } else {
            // Create a alert dialog to show in UI
            Alert alert = new Alert(Alert.AlertType.ERROR);
            // Set information for message that delivery to user in UI
            alert.setTitle("Error Message");
            alert.setHeaderText("Invalid input from user");
            alert.setContentText("The number provided by rivals is not in correct format");
            // Show the alert and wait for user response
            alert.showAndWait();
        }
    }
    /**Name: generatefgClicked
     **Event: When the GET FIRST GUESS button pressed by user
     **Purpose: generate the first guess
     **Passed: All code line perform without errors
     **Returns: Void
     **Input: event triggered // Output: The guess is showed in UI
     **Effect: This method help user get and view the guess number through UI
     */
    public void generatefgClicked(ActionEvent actionEvent) {
        // Use default first guess (which created in initialize function )
        ourGuessList = testfGuessNumber;
        // Show on UI
        firstGuess = ourGuessList.get(0) + "" + ourGuessList.get(1) + "" + ourGuessList.get(2) + "" +ourGuessList.get(3);
        firstgField.setText(firstGuess);
        // Set some component properties
        guessBtn.setDisable(true);
        guessBtn.setText("Current Guess");
        percentField.setText("0 %");
    }
    /**Name: generatengClicked
     **Event: When the GET NEXT GUESS button pressed by user
     **Purpose: generate the next guess based on the previous one result
     **Passed: All code line perform without errors
     **Returns: Void
     **Input: event triggered // Output: The guess is showed in UI
     **Effect: This method help user get next guess based on conducted result
     */
    public void generatengClicked(ActionEvent actionEvent) {
        // Get result from previous guess
        int strikeValue = strikeBox.getSelectionModel().getSelectedItem();
        int hitValue = hitBox.getSelectionModel().getSelectedItem();
        int missValue = missBox.getSelectionModel().getSelectedItem();

        // Increase steps
        stepsNumber++;
        // Load table to show result in UI
        resultList.add(new Result(stepsNumber,firstgField.getText(), strikeValue,hitValue, missValue));
        loadTable();


        // Check if already won !!!
        // IF YES
        if(strikeValue == 4) {
            percentField.setText("100 %");
            nextGuessBtn.setText("You won the game !!!");
            nextGuessBtn.setDisable(true);
            percentField.setDisable(true);
        // IF NOT
        } else {
            // Get value from comboBox
            List<Integer> strike_hit_miss = new ArrayList<>();
            strike_hit_miss.add(strikeValue);
            strike_hit_miss.add(hitValue);
            strike_hit_miss.add(missValue);

            // Guess next number
            nextGuess = guessNumber(firstgField.getText(), strike_hit_miss);
            firstgField.setText(nextGuess);
            percentField.setText(percent);
        }
    }

    /**Name: resetGame
     **Event: When the REST GAME button pressed by user
     **Purpose: reset game
     **Passed: All code line perform without errors
     **Returns: Void
     **Input: event triggered // Output: The guess is showed in UI
     **Effect: This method help user reset the current game before or after finish it
     */
    public void resetGame(ActionEvent actionEvent) {
        testsNumber.clear();
        testsNumber = generateNumber();
        clearAll();
    }


// -------------------------------------All SUPPORT UI FUNCTIONS START FROM HERE--------------------------------------//

    /**Name: loadTable
     **Event: executed when being called in another function
     **Purpose: load information to table component in UI
     **Passed: All code line perform without errors
     **Returns: Void
     **Input: None // Output: The result info is showed in table
     **Effect: This method help user to view all gained results such as steps, guess number and its strikes, hits values
     */
    public void loadTable() {
        // Condition whether the list for result is empty or not
        // IF NOT
        if(resultList != null) {
            // Add list of staff to the observableArrayList
            resultData =  FXCollections.observableArrayList(resultList);

            // Set column in array to present as different attributes of staff
            stepColumn.setCellValueFactory(new PropertyValueFactory<>("stepNumber"));
            numberColumn.setCellValueFactory(new PropertyValueFactory<>("guessNumber"));
            strikeColumn.setCellValueFactory(new PropertyValueFactory<>("strikeNumber"));
            hitColumn.setCellValueFactory(new PropertyValueFactory<>("hitNumber"));
            missColumn.setCellValueFactory(new PropertyValueFactory<>("missNumber"));

            // Set the created list to the staff table
            resultTable.setItems(null);
            resultTable.setItems(this.resultData);
        }
        // IF YES
        resultTable.setPlaceholder(new Label("Make a guess to gain result"));
    }

    /**Name: clearAll
     **Event: executed when being called in another function
     **Purpose: clear all information showed in UI
     **Passed: All code line perform without errors
     **Returns: Void
     **Input: None // Output: The UI is cleared
     **Effect: This method help user to clear all UI components
     */
    public void clearAll() {
        // Empty all UI components
        firstgField.setText("");
        snumberField.setText("");
        gnumberField.setText("");
        percentField.setText("");
        resultTable.setItems(null);
        strikeBox.setValue(0);
        hitBox.setValue(0);
        missBox.setValue(0);
        nextGuessBtn.setDisable(false);
        nextGuessBtn.setText("Get Next Guess");
        guessBtn.setDisable(false);
        guessBtn.setText("Generate First Guess");
        resBtn.setDisable(false);
        resBtn.setText("Get Result");

        // Reset all variable
        snumberList = new ArrayList<>();
        infoList = new ArrayList<>();
        rivalGuessList = new ArrayList<>();
        ourGuessList = new ArrayList<>();
        resultList = new ArrayList<>();
        isFirst = true;
        stepsNumber = 0;

    }

    /**Name: showAlert
     **Event: executed when being called in another function
     **Purpose: show alert when error occur, prevent the application crashing
     **Passed: All code line perform without errors
     **Returns: String - Empty: if the function perform properly
     **Input: None // Output: The alert dialog is showed with error infomation
     **Effect: This method help user know the error when playing the game
     */
    public String showAlert() {
        // Create alert dialog with type ERROR
        Alert alert = new Alert(Alert.AlertType.ERROR);
        // Set all messages that should be delivered to user
        alert.setTitle("Error Message");
        alert.setHeaderText("Wrong result gained from rivals");
        alert.setContentText("The number of strike, hits, and miss provided by rivals are invalid");
        // Create additional button for alert dialog
        ButtonType resetBtn = new ButtonType("New Game !!!");
        alert.getButtonTypes().setAll(resetBtn);
        // Handle when user click on button
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == resetBtn) {
            // ... user chose "new game"
            isFirst = true;
            percent = "";
            // clear all UI components
            clearAll();
        }
        return "";
    }


// -------------------------------------All LOGIC GAME FUNCTIONS START FROM HERE--------------------------------------//

    /**Name: isValidNumber
     **Event: executed when being called in another function
     **Purpose: check if user input is a valid number for the game
     **Passed: All code line perform without errors
     ** @param input - Contains the user input
     ** @return Boolean - TRUE: if the input is valid, otherwise: FALSE
     */
    public static Boolean isValidNumber(String input) {
        // Check if the input is contains exactly 4 characters
        if (input.length() != 4) {
            return Boolean.FALSE;
        }
        // Loop through the string to check each character is a number of not
        for (int i = 0; i < input.length(); i++) {
            char d = input.charAt(i);
            // Check if the character is a digits
            if (d >= '0' && d <= '9') {} else {
                // Return FALSE value when the character is not a number
                return Boolean.FALSE;
            }
        }
        // Otherwise, return TRUE value
        return Boolean.TRUE;
    }

    /**Name: generateNumber
     **Event: executed when being called in another function
     **Purpose: generate a 4-digits number randomly
     **Passed: All code line perform without errors
     **Input: Void
     **@return  List<Integer> - Contains information of the number
     */
    public List<Integer> generateNumber() {
        // Create new list to store values
        List<Integer> generatedNumber = new ArrayList<>();
        int i ;
        // Loop 4 times to create four randomly numbers
        for(i = 0 ; i <4; i++) {
            generatedNumber.add(ThreadLocalRandom.current().nextInt(0, 9 + 1));
        }
        // Return the list contains number info
        return  generatedNumber;
    }

    /**Name: generateHolder
     **Event: executed when being called in another function
     **Purpose: generate the holder that ensure contains the secret number - from 0000 to 9999
     **Passed: All code line perform without errors
     **Input: Void
     **@return  List<String> - Contains list of number in string form
     */
    public List<String> generateHolder() {
        // Create new list to store values
        List<String> generatedNumber = new ArrayList<>();
        int i ;
        // Loop 10000 times to create number from 0000 to 10000
        for(i = 0 ; i <10000; i++) {
            generatedNumber.add(String.format("%04d", i));
        }
        // Return the list contains number info
        return  generatedNumber;
    }

    /**Name: stringTransform
     **Event: executed when being called in another function
     **Purpose: transform the string to list of integer
     **Passed: All code line perform without errors
     ** @param gnumber - Contains guess number gained from UI
     ** @return List<Integer> - Contains similar information but under list of integer type
     */
    public List<Integer> stringTransform(String gnumber) {
        // Create new list to store values
        List<Integer> list = new ArrayList<>();
        int i;
        // Loop through each character of input
        for(i = 0; i< gnumber.length();i++) {
            // Add character to list under integer type
            list.add(Integer.parseInt(String.valueOf(gnumber.charAt(i))));
        }
        // Return the list contains number info
        return list;
    }

    /**Name: getResult
     **Event: executed when being called in another function
     **Purpose: get result of a specific number
     **Passed: All code line perform without errors
     ** @param sNumber - presented as secret number that contains information of current secret number
     ** @param gNumber - presented as guess number that contains information of current guess number
     ** @return List<Integer> - Contains result based on guess and secret number
     **                         First index of list: present for strike value
     **                         Second index of list: present for hit value
     **                         Third index of list: present for miss value
     */
    public List<Integer> getResult(List<Integer> sNumber, List<Integer> gNumber) {
        // Create new list to store values
        List<Integer> resultList = new ArrayList<>();
        // Create new variables to store result values
        int strike, hit, miss;
        // Add strike, hit, miss values to result list
        resultList.add(0); // 0: Strike
        resultList.add(0);//  1: Hit
        resultList.add(0);//  2: Miss

        // Create necessary variables
        int i,j;
        // Loop through each character of the guess number
        for(i = 0; i< gNumber.size(); i++) {
            // Compare two characters that located at same index in guess and secret number
            // IF SIMILARLY
            if(sNumber.get(i).equals(gNumber.get(i))) {
                // Increase value of strike result
                strike = resultList.get(0) + 1;
                // Set again the strike value in result list
                resultList.set(0, strike);
                continue;
            }
            // Loop through each character of the secret number
            for(j = 0; j < sNumber.size(); j++) {
                // Compare two characters in guess and secret number
                // IF SIMILARLY
                if(gNumber.get(i).equals(sNumber.get(j))) {
                    // Increase value of hit result
                    hit = resultList.get(1) + 1;
                    // Set again the hit value in result list
                    resultList.set(1, hit);
                    break;
                }
            }
        }
        // Calculate the miss value based on strike and hit result: 4 - strike - hit
        miss = 4 - resultList.get(0) - resultList.get(1);
        // Set again the miss value in result list
        resultList.set(2, miss);
        // Return the list contains result info
        return resultList;
    }

    /**Name: guessNumber
     **Event: executed when being called in another function
     **Purpose: guess the next number based on gained result from previous one
     **Passed: All code line perform without errors
     ** @param currentGuess - Contains guess number gained from UI
     ** @param result - Contains result gained from previous guess
     ** @return String - Contains information about next guess
     */
    public String guessNumber(String currentGuess, List<Integer> result) {
        // Create new lists to store values
        List<Integer> tempResult;
        List<String> possible = new ArrayList<>();
        List<String> impossible = new ArrayList<>();
        // Check if this si the run for the first guess
        // IF YES
        if(isFirst) {
            // Generate the holder for the game: contains numbers from 0000 to 9999
            holder = generateHolder();
            // Set the Boolean variable to false
            isFirst = false;
        }
        // Create necessary variable used in for loop
        int i;
        // Loop through each number in the holder
        for(i = 0; i < holder.size(); i++) {
            // Get result between the current index number with the current guess number
            tempResult = getResult( stringTransform(holder.get(i)), stringTransform(currentGuess));
            // Condition whether the gained result is similar with the provided result
            // IF YES
            if(result.get(0).equals(tempResult.get(0)) && result.get(1).equals(tempResult.get(1))) {
                // Add the current index number to the specific lists
                tempHolder.add(holder.get(i));
                possible.add(holder.get(i));
            // IF NO
            } else {
                // Add the current index number to the another list
                impossible.add(holder.get(i));
            }
        }
        // Calculate the percent of the correctness for the next guess
        percent = ((possible.size() * 100)/holder.size()) + " %";
        // Clear the holder list
        holder.clear();
        // Loop through the tempHolder
        for(i = 0; i < tempHolder.size(); i++) {
            // Add each element from tempHolder to holder list
            holder.add(tempHolder.get(i));
        }
        // Check if the current holder is empty
        if(holder.isEmpty()) {
            // IF YES: show the error dialog
            return  showAlert();
        }
        // Clear the tempHolder list
        tempHolder.clear();
        // Return the best guess after applying additional function
        return guessBestNumber(impossible, possible);
    }

    /**Name: guessBestNumber
     **Event: executed when being called in another function
     **Purpose: guess the best next guess number based on gained information
     **Passed: All code line perform without errors
     ** @param impossible - Contains the list of numbers that absolutely not contains secret number
     ** @param possible - Contains the list of numbers that definitely  contains secret number
     ** @return String - Contains information of the next guess
     */
    public String guessBestNumber(List<String> impossible, List<String> possible) {
        // Create all necessary variables
        String returnString;
        List<Integer> bestGuess = null;
        List<Integer> result;
        int minOut = -1;
        // Combine both possible and impossible list as one list
        List<String> tempList = new LinkedList<>(possible);
        tempList.addAll(impossible);
        // Loop through the above list
        for (String a : tempList) {
            // Create a two-dimensions array to perform as a table that stored upcoming values
            int[][] tempTable = new int[5][5];
            // Loop through the possible list
            for (String b : possible) {
                // Get result between a element of tempList and a element of current index element if possible list
                result = getResult(stringTransform(a),stringTransform(b));
                tempTable[result.get(0)][result.get(1)]++;
            }
            // Create a variable to store the value of hits that biggest
            int largestHits = -1;
            // Loop through the created table row by row (as one dimension array)
            for (int[] row : tempTable) {
                // Loop through each array in the table
                for (int i : row) {
                    // Set value of largestHits variables by comparing with the current element
                    largestHits = Integer.max(i, largestHits);
                }
            }
            // Create a variable by minus size of possible list with the value of largestHits variable
            int mark = possible.size() - largestHits;
            // Condition if value of mark bigger than minOut variable
            if (mark > minOut) {
                // Set minOut equal to mark
                minOut = mark;
                // Set bestGuess equal to current element of tempList
                bestGuess = stringTransform(String.valueOf(a));
            }
        }
        // Initialize the gained guess with the test one
        testGuess = bestGuess;
        // Combine the guess as a string
        returnString = bestGuess.get(0) + ""+bestGuess.get(1) + "" +bestGuess.get(2) + "" + bestGuess.get(3);
        // Return the guess under string form
        return returnString;
    }
}
