package kr.hs.gbsw.gbswjob.company.repository

import kr.hs.gbsw.gbswjob.company.domain.Company
import kr.hs.gbsw.gbswjob.company.domain.CompanyReview
import kr.hs.gbsw.gbswjob.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface CompanyReviewRepository : JpaRepository<CompanyReview, Int> {
    fun findByCompany(company: Company): List<CompanyReview>
    fun existsByIdAndUser(reviewId: Int, user: User): Boolean
}