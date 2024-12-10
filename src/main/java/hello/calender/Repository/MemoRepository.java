package hello.calender.Repository;

import hello.calender.Dto.MemoResponseDto;
import hello.calender.Entity.Calender;

import java.util.List;
import java.util.Optional;

public interface MemoRepository {

    MemoResponseDto saveMemo(Calender calender);

    List<MemoResponseDto> findAllMemos();

    Optional<Calender> findMemoById(Long id);

    int updateMemo(Long id, String author, String contents);

    int deleteMemo(Long id);
}
