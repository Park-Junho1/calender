package hello.calender.Dto;

import hello.calender.Entity.Calender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MemoResponseDto {

    private Long id;
    private String title;
    private String author;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public MemoResponseDto(Calender calender) {
        this.id = calender.getId();
        this.title = calender.getTitle();
        this.author = calender.getAuthor();
        this.contents = calender.getContents();
        this.createdAt = calender.getCreatedAt();
        this.updatedAt = calender.getUpdatedAt();
    }
}
