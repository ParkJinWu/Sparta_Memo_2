package com.sparta.memo.repository;

import com.sparta.memo.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemoRepository extends JpaRepository<Memo, Long> {

    //
    List<Memo> findAllByOrderByIdDesc();

    // 파라미터로 받은 username만 가져온다.
    //List<Memo> findAllByUsername(String username);

}
