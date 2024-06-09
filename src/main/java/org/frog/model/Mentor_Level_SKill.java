package org.frog.model;

public class Mentor_Level_SKill {
    private Mentor mentor;
    private Level_Skills level_skills;

    public Mentor_Level_SKill(Mentor mentor, Level_Skills level_skills) {
        this.mentor = mentor;
        this.level_skills = level_skills;
    }

    public Mentor getMentor() {
        return mentor;
    }

    public Level_Skills getLevel_skills() {
        return level_skills;
    }

    public void setLevel_skills(Level_Skills level_skills) {
        this.level_skills = level_skills;
    }

    public void setMentor(Mentor mentor) {
        this.mentor = mentor;
    }
}
