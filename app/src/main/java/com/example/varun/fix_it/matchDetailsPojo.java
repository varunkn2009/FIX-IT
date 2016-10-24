package com.example.varun.fix_it;

/**
 * Created by Varun on 3/29/2015.
 */
public class matchDetailsPojo {

    private  String Location;
    private String Team;
    private String Details;
    private String setDate;
    private String setTime;

    public String getLocation() {
        return Location;
    }
    public void setLocation(String Location) {
        this.Location = Location;
    }

    public String getTeam() {
        return Team;
    }
    public void setTeam(String Team) {
        this.Team = Team;
    }

    public String getDetails() {
        return Details;
    }
    public void setDetails(String Details) {
        this.Details = Details;
    }

    public String getSetDate() {
        return setDate;
    }
    public void setSetDate(String setDate) {
        this.setDate = setDate;
    }

    public String getSetTime() {
        return setTime;
    }
    public void setSetTime(String setTime) {
        this.setTime = setTime;
    }


    public void setDate(String date) {
        this.setDate=date;
    }

    public void Location(String Location) {
        this.Location= Location;
    }

    public void Team(String Team) {this.Team= Team; }

    public void Details(String Details) {this.Details= Details; }

    public void setTime(String time) {
        this.setTime= time;
    }
}