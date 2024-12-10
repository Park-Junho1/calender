package hello.calender.Service;

import hello.calender.Dto.MemoRequestDto;
import hello.calender.Dto.MemoResponseDto;
import hello.calender.Entity.Calender;
import hello.calender.Repository.MemoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MemoServiceImpl implements MemoService {

    private final MemoRepository memoRepository;

    public MemoServiceImpl(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    @Override
    public MemoResponseDto saveMemo(MemoRequestDto dto) {

        LocalDateTime now = LocalDateTime.now();

        Calender calender = new Calender(
                dto.getTitle(), dto.getAuthor(),
                dto.getContents(), dto.getPassword(),
                now, now);
        return memoRepository.saveMemo(calender);
    }

    @Override
    public List<MemoResponseDto> findAllMemos() {
        return memoRepository.findAllMemos();
    }

    @Override
    public MemoResponseDto findMemoById(Long id) {

        Optional<Calender> optionalMemo = memoRepository.findMemoById(id);

        if (optionalMemo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Exist id");
        }
        return new MemoResponseDto(optionalMemo.get());
    }

    @Override
    public MemoResponseDto updateMemo(Long id, String author, String contents) {

        if (author == null || contents != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author or content is null");
        }

        int updatedRow = memoRepository.updateMemo(id, author, contents);

        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Exist id");
        }

        Optional<Calender> optionalCalender = memoRepository.findMemoById(id);

        return new MemoResponseDto(optionalCalender.get());
    }

    @Override
    public void deleteMemo(Long id) {

        int deletedRow = memoRepository.deleteMemo(id);

        if (deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Exist id");
        }
    }
}
