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
import javafx.scene.text.Text;

import javax.swing.text.StyledEditorKit;
import java.awt.peer.ChoicePeer;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class gameController implements Initializable {
    @FXML
    TextField snumberField;
    @FXML
    TextField gnumberField;
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


    private String snumber, gnumber, firstGuess, nextGuess ;
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set text alignment for text field
        snumberField.setAlignment(Pos.CENTER);
        gnumberField.setAlignment(Pos.CENTER);
        firstgField.setAlignment(Pos.CENTER);

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


    // EVENT FUNCTIONS

    public void generateClicked(ActionEvent actionEvent) {
            snumberList =  generateNumber();
            snumber = snumberList.get(0) + "" + snumberList.get(1) + "" + snumberList.get(2) + "" +snumberList.get(3);
            snumberField.setText(snumber);
    }

    public void resultClicked(ActionEvent actionEvent) {
        // Get user input
        gnumber = gnumberField.getText();
        // Increase steps
        stepsNumber++;
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
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText("Invalid input from user");
            alert.setContentText("The number provided by rivals is not in correct format");
            alert.showAndWait();
        }
    }

    public void generatefgClicked(ActionEvent actionEvent) {

            // Use default first guess : 3210
            ourGuessList = testfGuessNumber;
            // Show on text field
            firstGuess = ourGuessList.get(0) + "" + ourGuessList.get(1) + "" + ourGuessList.get(2) + "" +ourGuessList.get(3);
            firstgField.setText(firstGuess);
            // Set component properties
            guessBtn.setDisable(true);
            guessBtn.setText("Current Guess");

    }

    public void generatengClicked(ActionEvent actionEvent) {

        int strikeValue = strikeBox.getSelectionModel().getSelectedItem();
        int hitValue = hitBox.getSelectionModel().getSelectedItem();
        int missValue = missBox.getSelectionModel().getSelectedItem();

        // Increase steps
        stepsNumber++;
        // Load table to show result in UI
        resultList.add(new Result(stepsNumber,firstgField.getText(), strikeValue,hitValue, missValue));
        loadTable();


        // Check if already won !!!
        if(strikeValue == 4) {
            nextGuessBtn.setText("You won the game !!!");
            nextGuessBtn.setDisable(true);
        } else {
            // Get value from comboBox
            List<Integer> strike_hit_miss = new ArrayList<>();
            strike_hit_miss.add(strikeValue);
            strike_hit_miss.add(hitValue);
            strike_hit_miss.add(missValue);

            // Guess next number
            nextGuess = guessNumber(firstgField.getText(), strike_hit_miss);
            firstgField.setText(nextGuess);
        }
    }


    public void resetGame(ActionEvent actionEvent) {
        testsNumber.clear();
        testsNumber = generateNumber();
        clearAll();
    }


    // SUPPORT UI FUNCTIONS
    public void loadTable() {
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
        resultTable.setPlaceholder(new Label("Make a guess to gain result"));
    }
    public void clearAll() {
        // Empty all UI components
        firstgField.setText("");
        snumberField.setText("");
        gnumberField.setText("");
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

    public String showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText("Wrong result gained from rivals");
        alert.setContentText("The number of strike, hits, and miss provided by rivals are invalid");

        ButtonType tryBtn = new ButtonType("Try Again !!!");
        ButtonType resetBtn = new ButtonType("New Game");
        alert.getButtonTypes().setAll(tryBtn, resetBtn);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == tryBtn) {
            // ... user chose "try again"
            strikeBox.setValue(0);
            hitBox.setValue(0);
            missBox.setValue(0);
            isFirst = true;
            return firstgField.getText();
        } else if (result.get() == resetBtn) {
            // ... user chose "new game"
            clearAll();
        }
        return "";
    }



    // LOGIC FUNCTIONS

    public static Boolean isValidNumber(String input) {
        // Loop through the string to check each character
        if (input.length() != 4) {
            return Boolean.FALSE;
        }
        for (int i = 0; i < input.length(); i++) {
            char d = input.charAt(i);
            // Check if the character is a digits
            if (d >= '0' && d <= '9') {} else {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

    public List<Integer> generateNumber() {
        List<Integer> generatedNumber = new ArrayList<>();
        int i ;
        for(i = 0 ; i <4; i++) {
            generatedNumber.add(ThreadLocalRandom.current().nextInt(0, 9 + 1));
        }
        return  generatedNumber;
    }
    public List<String> generateHolder() {
        List<String> generatedNumber = new ArrayList<>();
        int i ;
        for(i = 0 ; i <10000; i++) {
            generatedNumber.add(String.format("%04d", i));
        }
        return  generatedNumber;
    }

    public List<Integer> stringTransform(String gnumber) {
        List<Integer> list = new ArrayList<>();
        int i;
        for(i = 0; i< gnumber.length();i++) {
            list.add(Integer.parseInt(String.valueOf(gnumber.charAt(i))));
        }
        return list;
    }

    public String guessNumber(String currentGuess, List<Integer> result) {

        List<Integer> tempResult;
        List<String> possible = new ArrayList<>();
        List<String> impossible = new ArrayList<>();
        if(isFirst) {
            holder = generateHolder();
            isFirst = false;
        }

        int i;
        for(i = 0; i < holder.size(); i++) {
            tempResult = getResult( stringTransform(holder.get(i)), stringTransform(currentGuess));

            if(result.get(0).equals(tempResult.get(0)) && result.get(1).equals(tempResult.get(1))) {
                tempHolder.add(holder.get(i));
                possible.add(holder.get(i));
            } else {
                impossible.add(holder.get(i));
            }
        }
        holder.clear();
        for(i = 0; i < tempHolder.size(); i++) {
            holder.add(tempHolder.get(i));
        }
        if(holder.isEmpty()) {
            return  showAlert();
        }
       // System.out.println("Current Array Size: " + holder.size());
        tempHolder.clear();
       // int index = ThreadLocalRandom.current().nextInt(0, (holder.size()));
        //return holder.get(holder.size() - 1);
        return guessBestNumber(impossible, possible);
        //return holder.get(index);
    }


    public List<Integer> getResult(List<Integer> sNumber, List<Integer> gNumber) {
        List<Integer> resultList = new ArrayList<>();
        int strike, hit, miss;
        resultList.add(0); // 0: Strike
        resultList.add(0);//  1: Hit
        resultList.add(0);//  2: Miss

        int i,j;
        for(i = 0; i< gNumber.size(); i++) {
            if(sNumber.get(i).equals(gNumber.get(i))) {
                strike = resultList.get(0) + 1;
                resultList.set(0, strike);
                continue;
            }
            for(j = 0; j < sNumber.size(); j++) {
                if(gNumber.get(i).equals(sNumber.get(j))) {
                    hit = resultList.get(1) + 1;
                    resultList.set(1, hit);
                    break;
                }
            }
        }
        miss = 4 - resultList.get(0) - resultList.get(1);
        resultList.set(2, miss);
        return resultList;
    }




    public String guessBestNumber(List<String> impossible, List<String> possible) {
        int minimumEliminated = -1;
        String returnString = "";
        List<Integer> bestGuess = null;
        List<Integer> result = null;
        List<String> unused = new LinkedList<>(possible);
        unused.addAll(impossible);
        for (String a : unused) {
          //  System.out.println(a);
            int[][] minMaxTable = new int[5][5];
            for (String b : possible) {
              //  System.out.println(b);
                result = getResult(stringTransform(a),stringTransform(b));
                minMaxTable[result.get(0)][result.get(1)]++;
            }
            int mostHits = -1;
            for (int[] row : minMaxTable) {
                for (int i : row) {
                    mostHits = Integer.max(i, mostHits);
                }
            }
            int score = possible.size() - mostHits;
            if (score > minimumEliminated) {
                minimumEliminated = score;
                bestGuess = stringTransform(String.valueOf(a));
            }
        }
        testGuess = bestGuess;
      //  System.out.println("Enter secret code: " + testGuess);
        returnString = bestGuess.get(0) + ""+bestGuess.get(1) + "" +bestGuess.get(2) + "" + bestGuess.get(3);
        return returnString;
    }

}
