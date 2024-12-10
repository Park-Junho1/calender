package hello.calender.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Calender {

    @Id
    private Long id;

    private String title;
    private String author;
    private String contents;
    private String password;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Calender(String title, String author, String contents, String password, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.title = title;
        this.author = author;
        this.contents = contents;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void update(String author, String contents, LocalDateTime updatedAt) {
        this.author = author;
        this.contents = contents;
        this.updatedAt = updatedAt;
    }

}
