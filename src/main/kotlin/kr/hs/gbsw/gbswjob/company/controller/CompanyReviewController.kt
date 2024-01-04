package kr.hs.gbsw.gbswjob.company.controller

import kr.hs.gbsw.gbswjob.common.AuthUserId
import kr.hs.gbsw.gbswjob.company.domain.CompanyReview
import kr.hs.gbsw.gbswjob.company.dto.ReviewCreateDto
import kr.hs.gbsw.gbswjob.company.dto.ReviewUpdateDto
import kr.hs.gbsw.gbswjob.company.service.CompanyReviewService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/companies")
class CompanyReviewController(
    private val companyReviewService: CompanyReviewService
) {
    @GetMapping("/{companyId}/reviews")
    fun getReviews(
        @PathVariable companyId: Int
    ): List<CompanyReview> {
        return companyReviewService.getReviews(companyId)
    }

    @PostMapping("/{companyId}/reviews")
    fun create(
        @AuthUserId userId: String,
        @PathVariable companyId: Int,
        @RequestBody dto: ReviewCreateDto
    ): CompanyReview {
        return companyReviewService.create(userId, companyId, dto)
    }

    @PutMapping("/companies/reviews/{reviewId}")
    fun update(
        @AuthUserId userId: String,
        @PathVariable reviewId: Int,
        @RequestBody dto: ReviewUpdateDto
    ): CompanyReview {
        return companyReviewService.update(userId, reviewId, dto)
    }

    @DeleteMapping("/companies/reviews/{reviewId}")
    fun delete(
        @AuthUserId userId: String,
        @PathVariable reviewId: Int
    ): String {
        return companyReviewService.delete(userId, reviewId)
    }

}