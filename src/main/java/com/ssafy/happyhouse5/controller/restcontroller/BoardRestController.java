package com.ssafy.happyhouse5.controller.restcontroller;

import static com.ssafy.happyhouse5.constant.BoardConst.*;
import static com.ssafy.happyhouse5.constant.MemberConst.MEMBER_SESSION;

import com.ssafy.happyhouse5.dto.board.Board;
import com.ssafy.happyhouse5.dto.board.BoardRegisterDto;
import com.ssafy.happyhouse5.dto.board.BoardUpdateDto;
import com.ssafy.happyhouse5.dto.member.MemberSession;
import com.ssafy.happyhouse5.service.BoardService;
import com.ssafy.happyhouse5.service.impl.BoardSearchOption;
import io.swagger.annotations.ApiOperation;
import java.net.URI;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@RequestMapping("/api/boards")
@RestController
@RequiredArgsConstructor
public class BoardRestController {

    private final BoardService boardService;

    private final OptionMapper optionMapper;

    @PostMapping
    public ResponseEntity<Void> create(
        @Validated @RequestBody BoardRegisterDto boardRegisterDto,
        @SessionAttribute(MEMBER_SESSION) MemberSession memberSession) {
        Board board = Board.builder()
            .title(boardRegisterDto.getTitle())
            .content(boardRegisterDto.getContent())
            .memberId(memberSession.getId())
            .build();
        boardService.create(board);
        return ResponseEntity.created(URI.create("/api/boards/" + board.getId())).build();
    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<Void> update(
        @PathVariable int boardId,
        @Validated @RequestBody BoardUpdateDto boardUpdateDto,
        @SessionAttribute(MEMBER_SESSION) MemberSession memberSession) {
        Board board = Board.builder()
            .id(boardId)
            .title(boardUpdateDto.getTitle())
            .content(boardUpdateDto.getContent())
            .build();
        boardService.update(memberSession.getId(), board);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<Board> detail(@PathVariable int boardId) {
        Board board = boardService.selectById(boardId);
        if (board == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(board);
    }

    // TODO : require: class for response data (BoardResponse.java ... etc)
    // TODO : require: interceptor for processing auth (and session attr)
    @GetMapping
    @ApiOperation(value = "게시글 조회", notes = "title, content, member 값을 쿼리로 주어 조회할 수 있다. 쿼리의 개수는 0개 또는 1개여야 한다.")
    public ResponseEntity<?> find(
        @RequestParam Map<String, String> map) {
        log.debug("find called. map = {}", map);

        if (map == null || map.size() == 0) {
            return ResponseEntity.ok(boardService.findAll());
        }

        if (map.size() != 1) {
            return ResponseEntity.badRequest().body(INVALID_SIZE_OF_QUERY);
        }

        String queryKey = map.entrySet().stream()
            .findAny()
            .map(Map.Entry::getKey)
            .orElseThrow(IllegalArgumentException::new);

        BoardSearchOption option = optionMapper.get(queryKey);
        if (option == null) {
            return ResponseEntity.badRequest().body(NOT_ALLOWED_FIND_QUERY);
        }

        return ResponseEntity.ok(boardService.findByOption(option, map.get(queryKey)));
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> delete(@PathVariable int boardId) {
        boardService.delete(boardId);
        return ResponseEntity.ok().build();
    }
}
