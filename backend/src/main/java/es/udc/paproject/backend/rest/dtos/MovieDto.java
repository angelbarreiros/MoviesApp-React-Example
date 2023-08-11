package es.udc.paproject.backend.rest.dtos;

public class MovieDto  {
    private Long id;
    private String title;
    private String summary;
    private Long duration;

    public MovieDto(Long id, String title, String summary, Long duration) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }
}

