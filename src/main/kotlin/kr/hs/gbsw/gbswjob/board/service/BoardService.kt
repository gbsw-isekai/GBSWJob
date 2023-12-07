package kr.hs.gbsw.gbswjob.board.service

import kr.hs.gbsw.gbswjob.board.domain.Board
import kr.hs.gbsw.gbswjob.board.dto.BoardCreateDto
import kr.hs.gbsw.gbswjob.board.dto.BoardDeleteDto
import kr.hs.gbsw.gbswjob.board.dto.BoardUpdateDto
import kr.hs.gbsw.gbswjob.board.repository.BoardRepository
import kr.hs.gbsw.gbswjob.user.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class BoardService(
        private val repository: BoardRepository,
        private val userRepository: UserRepository
) {

    fun getAll(): List<Board> {
        val boards = repository.findAll();

        return boards;
    }

    fun getById(id: Int): Board {
        val board = repository.findById(id).get();

        return board;
    }

    fun create(userId: String, dto: BoardCreateDto): Board {

        val user = userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("존재하지 않는 사용자 입니다.")
        }

        val board = Board(
                null,
                dto.title,
                dto.content,
                user,
                LocalDateTime.now(),
                LocalDateTime.now()
        )
        return repository.save(board)
    }

    fun update(userId: String, dto: BoardUpdateDto): Board {

        val user = userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("존재하지 않는 사용자 입니다.")
        }

        val updatedBoard = repository.findById(dto.id).get();

        if(user != updatedBoard.writer) {
            throw IllegalStateException("자신의 글만 업데이트할 수 있습니다.")
        }

        updatedBoard.title = dto.title;
        updatedBoard.content = dto.title;
        updatedBoard.updatedAt = LocalDateTime.now()

        return repository.save(updatedBoard);
    }

    fun delete(userId: String, dto: BoardDeleteDto): Boolean {

        val user = userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("존재하지 않는 사용자 입니다.")
        }

        val board = repository.findById(dto.id).orElseThrow {
            IllegalArgumentException("존재하지 않는 게시글 입니다.")
        };

        if(user != board.writer) {
            throw IllegalStateException("자신의 글만 삭제할 수 있습니다.")
        }

        repository.delete(board);

        return true;
    }
}