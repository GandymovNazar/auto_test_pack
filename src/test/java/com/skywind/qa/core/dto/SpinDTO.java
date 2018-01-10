package com.skywind.qa.core.dto;

public class SpinDTO {
    private String description;
    private String combinations;
    private int totalWin;

    public SpinDTO(String description, String combinations, int totalWin) {
        this.description = description;
        this.combinations = combinations;
        this.totalWin = totalWin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCombinations() {
        return combinations;
    }

    public void setCombinations(String combinations) {
        this.combinations = combinations;
    }

    public int getTotalWin() {
        return totalWin;
    }

    public void setTotalWin(int totalWin) {this.totalWin = totalWin; }


    @Override
    public String toString() {
        return "Spin{" +
                "description='" + description + '\'' +
                ", combinations='" + combinations + '\'' +
                ", totalWin=" + totalWin +
                '}';
    }
}