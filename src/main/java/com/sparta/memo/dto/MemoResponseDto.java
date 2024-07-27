package com.sparta.memo.dto;

import com.sparta.memo.entity.Memo;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemoResponseDto {
    private Long id;
    private String username;
    private String contents;

    //
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;


    public MemoResponseDto(Memo memo) {
        this.id = memo.getId();
        this.username = memo.getUsername();
        this.contents = memo.getContents();

        //필드에 저장된 생성 시간과 수정 시간을 Client에 전달
        this.createdAt = memo.getCreatedAt();
        this.modifiedAt = memo.getModifiedAt();
    }


}