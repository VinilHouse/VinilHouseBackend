package com.ssafy.happyhouse5.controller.restcontroller;

import static com.ssafy.happyhouse5.constant.BoardConst.*;
import static com.ssafy.happyhouse5.constant.MemberConst.MEMBER_SESSION;
import static com.ssafy.happyhouse5.dto.common.Response.*;

import com.ssafy.happyhouse5.dto.board.BoardRegisterDto;
import com.ssafy.happyhouse5.dto.board.BoardResponseDto;
import com.ssafy.happyhouse5.dto.board.BoardUpdateDto;
import com.ssafy.happyhouse5.dto.common.Response;
import com.ssafy.happyhouse5.entity.Board;
import com.ssafy.happyhouse5.service.BoardService;
import com.ssafy.happyhouse5.service.impl.BoardSearchOption;
import io.swagger.annotations.ApiOperation;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<Response> create(
        @Validated @RequestBody BoardRegisterDto boardRegisterDto,
        @SessionAttribute(MEMBER_SESSION) Long memberId) throws URISyntaxException {
        Long boardId = boardService.create(memberId, boardRegisterDto);
        return ResponseEntity.created(new URI("/api/boards/" + boardId))
            .body(success(null));
    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<Response> update(
        @PathVariable Long boardId,
        @Validated @RequestBody BoardUpdateDto boardUpdateDto,
        @SessionAttribute(MEMBER_SESSION) Long memberId) {
        boardService.update(memberId, boardId, boardUpdateDto);
        return ResponseEntity.ok().body(success(boardId));
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<Response> detail(@PathVariable Long boardId) {
        Board board = boardService.selectById(boardId);
        BoardResponseDto dto = new BoardResponseDto
            (board.getId(), board.getTitle(), board.getContent(), board.getMember().getIdent());
        return ResponseEntity.ok(success(dto));
    }

    // TODO : require: interceptor for processing auth (and session attr)
    @GetMapping
    @ApiOperation(value = "게시글 조회", notes = "title, content, member 값을 쿼리로 주어 조회할 수 있다. 쿼리의 개수는 0개 또는 1개여야 한다.")
    public ResponseEntity<Response> find(
        @RequestParam Map<String, String> map) {

        if (map == null || map.size() == 0) {
            return ResponseEntity.ok(
                success(
                    boardService.findAll().stream()
                        .map(BoardResponseDto::new)
                        .collect(Collectors.toList())));
        }

        if (map.size() != 1) {
            return ResponseEntity.badRequest().body(fail(INVALID_SIZE_OF_QUERY));
        }

        String queryKey = map.entrySet().stream()
            .findAny()
            .map(Map.Entry::getKey)
            .orElseThrow(IllegalArgumentException::new);

        BoardSearchOption option = optionMapper.get(queryKey);
        if (option == null) {
            return ResponseEntity.badRequest().body(fail(NOT_ALLOWED_FIND_QUERY));
        }

        return ResponseEntity.ok(success(
            boardService.findByOption(option, map.get(queryKey))
                .stream().map(BoardResponseDto::new)
                .collect(Collectors.toList())));
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Response> delete(@PathVariable Long boardId) {
        boardService.delete(boardId);
        return ResponseEntity.ok().body(success(boardId));
    }
}
