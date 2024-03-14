package kr.hs.gbsw.gbswjob.board.service

import kr.hs.gbsw.gbswjob.board.domain.Board
import kr.hs.gbsw.gbswjob.board.domain.BoardLike
import kr.hs.gbsw.gbswjob.board.domain.BoardView
import kr.hs.gbsw.gbswjob.board.dto.BoardCreateDto
import kr.hs.gbsw.gbswjob.board.projection.BoardQuestionProjection
import kr.hs.gbsw.gbswjob.board.dto.BoardUpdateDto
import kr.hs.gbsw.gbswjob.board.repository.BoardLikeRepository
import kr.hs.gbsw.gbswjob.board.repository.BoardRepository
import kr.hs.gbsw.gbswjob.board.repository.BoardViewRepository
import kr.hs.gbsw.gbswjob.user.domain.User
import kr.hs.gbsw.gbswjob.user.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class BoardService(
        private val repository: BoardRepository,
        private val userRepository: UserRepository,
        private val likeRepository: BoardLikeRepository,
        private val viewRepository: BoardViewRepository
) {

    fun getAll(): List<Board> {

        return repository.findAll()
    }

    fun getById(id: Int): Board {
        val board = repository.findById(id).orElseThrow {
            IllegalArgumentException("존재하지 않는 게시글 입니다.")
        }

        return board
    }

    fun createAnswer(user: User, dto: BoardCreateDto): Board {
        // 1. 질문글을 불러온다.
        val question = repository.findById(dto.questionId!!).orElseThrow {
            IllegalArgumentException("존재하지 않는 게시글 입니다.")
        }

        if (question.isAnswer()) {
            throw IllegalStateException("답변 글에 답변을 추가할 수 없습니다.")
        }

        // 2. 답변글을 저장한다.
        val answer = Board(
                null,
                null,
                dto.content,
                user,
                null,
                0,
                question,
                null,
                null,
                null,
                0,
                LocalDateTime.now(),
                LocalDateTime.now()
        )

        repository.save(answer)

        question.answersSize = question.answers?.size ?: 0

        repository.save(question)

        return answer
    }

    fun createQuestion(user: User, dto: BoardCreateDto): Board {
        val question = Board(
                null,
                dto.title,
                dto.content,
                user,
                null,
                0,
                null,
                null,
                null,
                null,
                0,
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
            return createAnswer(user, dto)
        }

        return createQuestion(user, dto)
    }

    fun update(userId: String, dto: BoardUpdateDto): Board {

        val user = userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("존재하지 않는 사용자 입니다.")
        }

        val updatedBoard = repository.findById(dto.id).get()

        if(updatedBoard.writer != user) {
            throw IllegalStateException("자신의 글만 업데이트할 수 있습니다.")
        }

        updatedBoard.title = dto.title
        updatedBoard.content = dto.content
        updatedBoard.updatedAt = LocalDateTime.now()

        return repository.save(updatedBoard)
    }

    fun delete(userId: String, id: Int) {

        val user = userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("존재하지 않는 사용자 입니다.")
        }

        val board = repository.findById(id).orElseThrow {
            IllegalArgumentException("존재하지 않는 게시글 입니다.")
        }

        if (board.writer != user) {
            throw IllegalStateException("자신의 글만 삭제할 수 있습니다.")
        }

        var question: Board? = null

        if (board.isAnswer()) {
            question = repository.findById(board.question!!.id!!).get();
        }

        repository.delete(board)

        if (question != null) {
            question.answersSize = question.answers?.size ?: 0

            repository.save(question)
        }

        return
    }

    fun createLike(userId: String, boardId: Int): BoardLike {
        val user = userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("존재하지 않는 사용자 입니다.")
        }

        val board = repository.findById(boardId).orElseThrow {
            IllegalArgumentException("존재하지 않는 게시글 입니다.")
        }

        val Like = BoardLike(
                null,
                user,
                board
        )

        if (!board.like.isNullOrEmpty() && likeRepository.existsByUserAndBoard(user, board)) {
            throw IllegalStateException("이미 좋아요를 누른 게시글 입니다.")
        }

        return likeRepository.save(Like)
    }

    fun deleteLike(userId: String, boardId: Int) {
        val user = userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("존재하지 않는 사용자 입니다.")
        }

        val board = repository.findById(boardId).orElseThrow {
            IllegalArgumentException("존재하지 않는 게시글 입니다.")
        }

        if (board.like.isNullOrEmpty() || !likeRepository.existsByUserAndBoard(user, board)) {
            throw IllegalStateException("좋아요를 누르지 않았습니다.")
        }

        val like = likeRepository.getByUserAndBoard(user, board)

        likeRepository.delete(like)

        return
    }

    fun createView(userId: String?, boardId: Int) {

        var user: User? = null

        if (!userId.isNullOrEmpty()) {
            user = userRepository.findById(userId).orElseThrow {
                IllegalArgumentException("존재하지 않는 사용자 입니다.")
            }
        }


        val board = repository.findById(boardId).orElseThrow {
            IllegalArgumentException("존재하지 않는 게시글 입니다.")
        }

        val view = BoardView(
                null,
                user,
                board
        )

        board.viewCount++

        viewRepository.save(view)
        repository.save(board)

    }

    fun getQuestions(orderType: String?): List<BoardQuestionProjection> {

        if (orderType.equals("latest")) {
            return repository.findByQuestionIdIsNullOrderByCreatedAtDesc();
        } else if (orderType.equals("answers")) {
            return repository.findByQuestionIdIsNullOrderByAnswersSizeDesc();
        } else if (orderType.equals("views")) {
            return repository.findByQuestionIdIsNullOrderByViewCountDesc();
        }

        return repository.findByQuestionIdIsNull();
    }

    fun getIsLikeByBoard(userId: String?, boardId: Int): Boolean {

        if (userId == null) return false;

        val user = userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("존재하지 않는 사용자 입니다.")
        }

        val board = repository.findById(boardId).orElseThrow {
            IllegalArgumentException("존재하지 않는 게시글 입니다.")
        }

        return likeRepository.existsByUserAndBoard(user, board);
    }
}