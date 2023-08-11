package es.udc.paproject.backend.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Movie {

    private Long id;

    private String title;

    private String summary;

    private Long duration;

    public Movie(){}

    public Movie(Long id, String Title, String Summary, Long Duration){
        this.id = id;
        this.title = Title;
        this.summary = Summary;
        this.duration = Duration;
    }

    public Movie(String Title, String Summary, Long Duration){
        this.title = Title;
        this.summary = Summary;
        this.duration = Duration;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getTitle(){
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary(){
        return summary;
    }

    public void setSummary(String summary){
        this.summary = summary;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }


}

