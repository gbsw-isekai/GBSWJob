package kr.hs.gbsw.gbswjob.company.service

import kr.hs.gbsw.gbswjob.company.domain.CompanyReview
import kr.hs.gbsw.gbswjob.company.dto.ReviewCreateDto
import kr.hs.gbsw.gbswjob.company.dto.ReviewUpdateDto
import kr.hs.gbsw.gbswjob.company.repository.CompanyRepository
import kr.hs.gbsw.gbswjob.company.repository.CompanyReviewRepository
import kr.hs.gbsw.gbswjob.user.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CompanyReviewService (
    private var companyRepository: CompanyRepository,
    private var companyReviewRepository: CompanyReviewRepository,
    private var userRepository: UserRepository
) {
    fun getReviews(companyId: Int): List<CompanyReview> {
        val company = companyRepository.findById(companyId).orElseThrow {
            IllegalArgumentException("회사를 찾을 수 없습니다.")
        }

        return companyReviewRepository.findByCompany(company)
    }

    fun create(userId: String, companyId: Int, dto: ReviewCreateDto): CompanyReview {
        val user = userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("유저를 찾을 수 없습니다.")
        }

        val company = companyRepository.findById(companyId).orElseThrow {
            IllegalArgumentException("회사를 찾을 수 없습니다.")
        }

        val now = LocalDateTime.now()
        val companyReview = CompanyReview(
            null,
            dto.title,
            dto.pro,
            dto.con,
            dto.welfareAndSalaryRating,
            dto.atmosphereRating,
            dto.workloadRating,
            dto.transportationRating,
            company,
            user,
            now,
            now
        )

        return companyReviewRepository.save(companyReview)
    }

    fun update(userId: String, reviewId: Int, dto: ReviewUpdateDto):CompanyReview {
        val user = userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("유저를 찾을 수 없습니다.")
        }

        val updateReview = companyReviewRepository.findById(reviewId).orElseThrow {
            IllegalArgumentException("수정할 유저를 찾을 수 없습니다.")
        }

        updateReview.apply {
            title = dto.title
            pro = dto.pro
            con = dto.con
            welfareAndSalaryRating = dto.welfareAndSalaryRating
            atmosphereRating = dto.atmosphereRating
            workloadRating = dto.workloadRating
            transportationRating = dto.transportationRating
        }

        return companyReviewRepository.save(updateReview)
    }

    fun delete(userId: String, reviewId: Int): String {
        val user = userRepository.findById(userId).orElseThrow {
            IllegalArgumentException("유저를 찾을 수 없습니다")
        }

        if (!companyReviewRepository.existsById(reviewId)) {
            throw IllegalArgumentException("리뷰가 존재하지 않습니다.")
        }

        if (!companyReviewRepository.existsByIdAndUser(reviewId, user)) {
            throw IllegalStateException("자신의 리뷰가 아니거나 리뷰를 찾을 수 없습니다.")
        }

        companyReviewRepository.deleteById(reviewId)

        return "삭제되었습니다."
    }
}