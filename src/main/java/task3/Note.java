package task3;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class Note {
    public Long id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String title;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String value;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<Note> values;
}
