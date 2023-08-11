package es.udc.paproject.backend.rest.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Map;

public class BillboardRowDto {
    private String title;
    private Long id;
    private Map<Long,Long> map;

    public BillboardRowDto() {}

    public BillboardRowDto(String title, Long id, Map<Long, Long> map) {
        this.title = title;
        this.id = id;
        this.map = map;
    }
    @NotBlank
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

   @NotEmpty
    public Map<Long, Long> getMap() {
        return map;
    }

    public void setMap(Map<Long, Long> map) {
        this.map = map;
    }
}
