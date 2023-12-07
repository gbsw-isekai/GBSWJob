package kr.hs.gbsw.gbswjob.board.service

import kr.hs.gbsw.gbswjob.board.domain.Board
import kr.hs.gbsw.gbswjob.board.domain.Comment
import kr.hs.gbsw.gbswjob.board.dto.BoardCreateDto
import kr.hs.gbsw.gbswjob.board.dto.BoardUpdateDto
import kr.hs.gbsw.gbswjob.board.dto.CommentCreateDto
import kr.hs.gbsw.gbswjob.board.dto.CommentUpdateDto
import kr.hs.gbsw.gbswjob.board.repository.BoardRepository
import kr.hs.gbsw.gbswjob.board.repository.CommentRepository
import kr.hs.gbsw.gbswjob.user.domain.User
import kr.hs.gbsw.gbswjob.user.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class BoardService(
        private val repository: BoardRepository,
        private val userRepository: UserRepository,
        private val commentRepository: CommentRepository
) {

    fun getAll(): List<Board> {
        val boards = repository.findAll();

        return boards;
    }

    fun getById(id: Int): Board {
        val board = repository.findById(id).get();

        return board;
    }

    fun createQuestion(user: User, dto: BoardCreateDto): Board {
        // 1. 질문글을 불러온다.
        val question = repository.findById(dto.questionId!!).orElseThrow {
            IllegalArgumentException("존재하지 않는 게시글 입니다.")
        }
        // 2. 답변글을 저장한다.
        val answer = Board(
                null,
                dto.title,
                dto.content,
                user,
                null,
                question,
                null,
                LocalDateTime.now(),
                LocalDateTime.now()
        )
        repository.save(answer)

        // 3. 질문글의 answers에 답변을 추가한다.
        if (question.answers == null) { // question 의 answer 가 null 이라면
            question.answers = mutableListOf(answer) // question 의 answer 에 새로운 List를 만들어준다
        } else { // question 의 answer 가 null 이 아니라면
            question.answers!!.add(answer) // question 의 answer 리스트에 새로운 답변을 추가해준다
        }

        repository.save(question)

        return answer
    }

    fun createAnswer(user: User, dto: BoardCreateDto): Board {
        val question = Board(
                null,
                dto.title,
                dto.content,
                user,
                null,
                null,
                null,
                LocalDateTime.now(),
                LocalDateTime.now(),
        )
        return repository.save(question)
    }

    fun create(userId: String, dto: BoardCreateDto): Board {
        val user = userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("존재하지 않는 사용자 입니다.")
        }

        // 목표 : 질문 글, 답변 글을 구분해서 등록할 수 있따.
        // questionId 가 있다면 답변 글
        if (dto.questionId != null) {
            return createQuestion(user, dto)
        }

        return createAnswer(user, dto)
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

    fun delete(userId: String, id: Int): Boolean {

        val user = userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("존재하지 않는 사용자 입니다.")
        }

        val board = repository.findById(id).orElseThrow {
            IllegalArgumentException("존재하지 않는 게시글 입니다.")
        };

        if(user != board.writer) {
            throw IllegalStateException("자신의 글만 삭제할 수 있습니다.")
        }

        repository.delete(board);

        return true;
    }

    fun createComment(userId: String, id: Int, dto: CommentCreateDto): Comment {

        val user = userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("존재하지 않는 사용자 입니다.")
        }

        val board = repository.findById(id).orElseThrow {
            IllegalArgumentException("존재하지 않는 게시글 입니다.")
        };

        val comment = Comment(
                null,
                dto.content,
                user,
                board,
                LocalDateTime.now(),
                LocalDateTime.now()
        )

        return commentRepository.save(comment)
    }

    fun updateComment(userId: String, dto: CommentUpdateDto): Comment {
        val user = userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("존재하지 않는 사용자 입니다.")
        }

        val comment = commentRepository.findById(dto.id).orElseThrow {
            IllegalArgumentException("존재하지 않는 댓글 입니다.")
        };

        if (user != comment.writer) {
            throw IllegalStateException("자신의 댓글만 수정할 수 있습니다.")
        }

        comment.content = dto.content
        comment.updatedAt = LocalDateTime.now()

        return commentRepository.save(comment)
    }

    fun deleteComment(userId: String, id: Int): Boolean {
        val user = userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("존재하지 않는 사용자 입니다.")
        }

        val comment = commentRepository.findById(id).orElseThrow {
            IllegalArgumentException("존재하지 않는 댓글 입니다.")
        }

        if (user != comment.writer) {
            throw IllegalStateException("자신의 댓글만 삭제할 수 있습니다.")
        }

        commentRepository.delete(comment)

        return true
    }
}