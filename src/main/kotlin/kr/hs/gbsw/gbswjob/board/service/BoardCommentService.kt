package kr.hs.gbsw.gbswjob.board.service

import kr.hs.gbsw.gbswjob.board.domain.BoardComment
import kr.hs.gbsw.gbswjob.board.domain.BoardCommentLike
import kr.hs.gbsw.gbswjob.board.dto.BoardCommentCreateDto
import kr.hs.gbsw.gbswjob.board.dto.BoardCommentUpdateDto
import kr.hs.gbsw.gbswjob.board.repository.BoardRepository
import kr.hs.gbsw.gbswjob.board.repository.BoardCommentLikeRepository
import kr.hs.gbsw.gbswjob.board.repository.BoardCommentRepository
import kr.hs.gbsw.gbswjob.user.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class BoardCommentService(
        private val repository: BoardCommentRepository,
        private val userRepository: UserRepository,
        private val boardRepository: BoardRepository,
        private val commentLikeRepository: BoardCommentLikeRepository
) {

    fun getByBoardId(boardId: Int): List<BoardComment> {
        val board = boardRepository.findById(boardId).orElseThrow {
            IllegalArgumentException("존재하지 않는 게시글 입니다.")
        }

        return board.comment ?: mutableListOf()
    }

    fun create(userId: String, id: Int, dto: BoardCommentCreateDto): BoardComment {

        val user = userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("존재하지 않는 사용자 입니다.")
        }

        val board = boardRepository.findById(id).orElseThrow {
            IllegalArgumentException("존재하지 않는 게시글 입니다.")
        };

        val comment = BoardComment(
                null,
                dto.content,
                user,
                board,
                null,
                LocalDateTime.now(),
                LocalDateTime.now()
        )

        return repository.save(comment)
    }

    fun update(userId: String, boardId: Int, commentId: Int, dto: BoardCommentUpdateDto): BoardComment {
        val user = userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("존재하지 않는 사용자 입니다.")
        }

        val board = boardRepository.findById(boardId).orElseThrow {
            IllegalArgumentException("존재하지 않는 게시글 입니다.")
        }

        val comment = repository.findById(commentId).orElseThrow {
            IllegalArgumentException("존재하지 않는 댓글 입니다.")
        };

        if(board.comment.isNullOrEmpty() || comment.board != board) {
            throw IllegalArgumentException("존재하지 않는 댓글 입니다.")
        }

        if (comment.writer != user) {
            throw IllegalStateException("자신의 댓글만 수정할 수 있습니다.")
        }

        comment.content = dto.content
        comment.updatedAt = LocalDateTime.now()

        return repository.save(comment)
    }

    fun delete(userId: String, boardId: Int, commentId: Int) {
        val user = userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("존재하지 않는 사용자 입니다.")
        }

        val board = boardRepository.findById(boardId).orElseThrow {
            IllegalArgumentException("존재하지 않는 게시글 입니다.")
        }

        val comment = repository.findById(commentId).orElseThrow {
            IllegalArgumentException("존재하지 않는 댓글 입니다.")
        }

        if(board.comment.isNullOrEmpty() || comment.board != board) {
            throw IllegalArgumentException("존재하지 않는 댓글 입니다.")
        }

        if (comment.writer != user) {
            throw IllegalStateException("자신의 댓글만 삭제할 수 있습니다.")
        }

        repository.delete(comment)

        return
    }

    fun createLike(userId: String, boardId: Int, commentId: Int): BoardCommentLike {
        val user = userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("존재하지 않는 사용자 입니다.")
        }

        val board = boardRepository.findById(boardId).orElseThrow {
            IllegalArgumentException("존재하지 않는 게시글 입니다.")
        }

        val comment = repository.findById(commentId).orElseThrow {
            IllegalArgumentException("존재하지 않는 댓글 입니다.")
        }

        if (comment.board != board) {
            throw IllegalStateException("존재하지 않는 댓글 입니다.")
        }

        val Like = BoardCommentLike(
                null,
                user,
                comment
        )

        if (!comment.like.isNullOrEmpty() && commentLikeRepository.existsByUserAndComment(user, comment)) {
            throw IllegalStateException("이미 좋아요를 누른 댓글 입니다.")
        }

        return commentLikeRepository.save(Like)
    }

    fun deleteLike(userId: String, boardId: Int, commentId: Int) {
        val user = userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("존재하지 않는 사용자 입니다.")
        }

        val board = boardRepository.findById(boardId).orElseThrow {
            IllegalArgumentException("존재하지 않는 게시글 입니다.")
        }

        val comment = repository.findById(commentId).orElseThrow {
            IllegalArgumentException("존재하지 않는 댓글 입니다.")
        }

        if (comment.board != board) {
            throw IllegalStateException("존재하지 않는 댓글 입니다.")
        }

        if (comment.like.isNullOrEmpty() || !commentLikeRepository.existsByUserAndComment(user, comment)) {
            throw IllegalStateException("좋아요를 누르지 않았습니다.")
        }

        val like = commentLikeRepository.getByUserAndComment(user, comment)

        commentLikeRepository.delete(like)

        return
    }
}