package com.groupOne.model;
/**
 * This class created to support UI (in specific: table component) by creating additional model for result
 *
 * @author COSC2658: Data Strucure - Group 1
 * Dong Nguyen: s3634096
 * Duc Ho:      s
 * Thuan Trang: s
 * Danh Le:     s
 *
 */
public class Result {
    // Create attributes for result model
    int stepNumber;
    String guessNumber;
    int strikeNumber;
    int hitNumber;
    int missNumber;

    // Getter and Setter result model
    public Result(int stepNumber, String guessNumber, int strikeNumber, int hitNumber, int missNumber) {
        this.stepNumber = stepNumber;
        this.guessNumber = guessNumber;
        this.strikeNumber = strikeNumber;
        this.hitNumber = hitNumber;
        this.missNumber = missNumber;
    }

    // Getter and Setter of each attributes of result model
    public int getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public String getGuessNumber() {
        return guessNumber;
    }

    public void setGuessNumber(String guessNumber) {
        this.guessNumber = guessNumber;
    }

    public int getStrikeNumber() {
        return strikeNumber;
    }

    public void setStrikeNumber(int strikeNumber) {
        this.strikeNumber = strikeNumber;
    }

    public int getHitNumber() {
        return hitNumber;
    }

    public void setHitNumber(int hitNumber) {
        this.hitNumber = hitNumber;
    }

    public int getMissNumber() {
        return missNumber;
    }

    public void setMissNumber(int missNumber) {
        this.missNumber = missNumber;
    }
}
