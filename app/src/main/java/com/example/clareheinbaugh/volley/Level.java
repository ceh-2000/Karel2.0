package com.example.clareheinbaugh.volley;

public class Level {
    private String directions;
    private String answer;
    private String hint;

    public Level(String dir, String ans, String hi) {
        directions = dir;
        answer = ans;
        hint  = hi;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hin) {
        this.hint = hin;
    }

}
