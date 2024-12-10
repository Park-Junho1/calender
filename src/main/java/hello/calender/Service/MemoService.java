package hello.calender.Service;

import hello.calender.Dto.MemoRequestDto;
import hello.calender.Dto.MemoResponseDto;

import java.util.List;

public interface MemoService {

    MemoResponseDto saveMemo(MemoRequestDto dto);

    List<MemoResponseDto> findAllMemos();

    MemoResponseDto findMemoById(Long id);

    MemoResponseDto updateMemo(Long id, String authour, String contents);

    void deleteMemo(Long id);
}
