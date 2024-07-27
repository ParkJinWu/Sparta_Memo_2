package com.sparta.memo.controller;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.service.MemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MemoController {

    private final MemoService memoService;

    @Autowired
    public MemoController(MemoService memoService) {
        this.memoService = memoService;
    }

    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {
        // 객체 간 이동 즉 다른 class의 메서드를 호출하려면 그 클래스를 객체를 만들어야 한다 ➡️ instance화
        return memoService.createMemo(requestDto);

    }

    //컬럼하나가 자바 객체의 field 하나가 되고,
    //DB 테이블에서 한 줄 즉 한 row가 자바 객체 하나가 된다.
    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos() {
        return memoService.getMemos();
    }

    @PutMapping("/memos/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
        return memoService.updateMemo(id,requestDto); // id,수정할 id

    }

    @DeleteMapping("/memos/{id}")
    public Long deleteMemo(@PathVariable Long id) {
        return memoService.deleteMemo(id);
    }

}


/*
    @RestController  ➡️ @Controller와 @ResponseBody를 결합한 역할

     RequestDto를 통해 클라이언트로부터 데이터를 받고, 이를 엔티티로 변환한 후 데이터베이스에 저장

     @RestController
@RequestMapping("/api")
public class MemoController {

    //DB대체의 구현체
    private final Map<Long, Memo> memoList = new HashMap<>();

    // 메모 생성 api
    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {

        // RequestDto ➡️ Entity로 변환 (DB와 소통해야함)
        Memo memo = new Memo(requestDto);

        // Memo Max ID Check
        Long maxId = memoList.size() > 0 ? Collections.max(memoList.keySet()) + 1 : 1;
        memo.setId(maxId);

        // DB 저장
        memoList.put(memo.getId(), memo);

        // Entity ➡️ RequestDto
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

        return memoResponseDto;
    }

    // 메모 조회 api
    @GetMapping("/memos")
    // Memo는 당연히 여러개일 수 가 있기 때문에 List로 반환.
    public List<MemoResponseDto> getMemos() {
        // Map to List
        // 메모들을 vlause()메서드로 값만 가져오고 stream API를 사용하여 하나 씩
        // MemoResponseDto로 변환 후 List로 변환
        List<MemoResponseDto> responseList = memoList.values().stream()
                .map(MemoResponseDto::new)
                .toList();
        return responseList;
    }

    // 메모 변경 api
    @PutMapping("/memos/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
        // Update 할 메모의 id를 PathVariable로 받아온다.
        // 수정할 id를 RequestBody로 받아온다.

        // 메모가 존재하는 지 확인
        if (memoList.containsKey(id)) {
            // 1) 메모 가져오기
            Memo memo = memoList.get(id);

            // 2) 메모 수정
            memo.update(requestDto);

            // 3) 수정된 id 반환
            return memo.getId();
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

    // 메모 삭제 api
    @DeleteMapping("/memos/{id}")
    public Long deleteMemo(@PathVariable Long id) {
        // 메모가 존재하는 지 확인
        if (memoList.containsKey(id)) {
            memoList.remove(id);
            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

}
* */