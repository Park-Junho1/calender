package hello.calender.Repository;

import hello.calender.Dto.MemoResponseDto;
import hello.calender.Entity.Calender;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplateMemoRepository implements MemoRepository {

    private final JdbcTemplate jdbcTemplate;
    public JdbcTemplateMemoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public MemoResponseDto saveMemo(Calender calender) {
        LocalDateTime date = LocalDateTime.now();
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");

        Map<String, Object> params = new HashMap<>();
        params.put("title", calender.getTitle());
        params.put("author", calender.getAuthor());
        params.put("contents", calender.getContents());
        params.put("password", calender.getPassword());
        params.put("created_at", date);
        params.put("updated_at", date);

        Number key = jdbcInsert.executeAndReturnKey(params);
        return new MemoResponseDto(key.longValue(), calender.getTitle(), calender.getAuthor(), calender.getContents(),
                date, date);
    }

    @Override
    public List<MemoResponseDto> findAllMemos() {
        return jdbcTemplate.query("select * from schedule", memoRowMapper());
    }

    @Override
    public Optional<Calender> findMemoById(Long id) {
        List<Calender> result = jdbcTemplate.query("select * from schedule where id = ?", memoRowMapperV2(), id);
        return result.stream().findAny();
    }

    @Override
    public int updateMemo(Long id, String author, String contents) {
        jdbcTemplate.update("update schedule set author = ?, contents = ?, updated_at = ? where id = ?", author, contents, LocalDateTime.now(), id);
        return 0;
    }

    private RowMapper<MemoResponseDto> memoRowMapper() {
        return new RowMapper<MemoResponseDto>() {

            @Override
            public MemoResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new MemoResponseDto(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("contents"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
            }
        };
    }

    private RowMapper<Calender> memoRowMapperV2() {
        return (rs, rowNum) -> new Calender(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getString("contents"),
                rs.getString("password"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }

    @Override
    public int deleteMemo(Long id) {
        return jdbcTemplate.update("delete from schedule where id = ?", id);
    }
}
