package kr.hs.gbsw.gbswjob.company.controller

import jakarta.persistence.Id
import kr.hs.gbsw.gbswjob.common.AuthUserId
import kr.hs.gbsw.gbswjob.company.domain.Company
import kr.hs.gbsw.gbswjob.company.domain.CompanyReview
import kr.hs.gbsw.gbswjob.company.dto.CompanyGetDto
import kr.hs.gbsw.gbswjob.company.dto.CreateCompanyDto
import kr.hs.gbsw.gbswjob.company.dto.ReviewCreateDto
import kr.hs.gbsw.gbswjob.company.dto.ReviewUpdateDto
import kr.hs.gbsw.gbswjob.company.service.CompanyReviewService
import kr.hs.gbsw.gbswjob.company.service.CompanyService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/companies")
class CompanyController (
    private val service: CompanyService,
    private val reviewService: CompanyReviewService
){
    //회사 등록
    @PostMapping
    fun createCompany(@RequestBody dto: CreateCompanyDto): Company {
        return service.create(dto)
    }
    //회사 목록 조회
    @GetMapping
    fun getCompanies(): List<CompanyGetDto> {
        return service.getCompanies()
    }
    //회사 일괄 조회
    @GetMapping("/{companyId}")
    fun getCompany(@PathVariable companyId: Int): Company {
        return service.getCompany(companyId)
    }

    //원하는 회사에 맞는 리뷰 가져오기
    @GetMapping("/{companyId}/reviews")
    fun getCompanyReviews(@PathVariable("companyId") companyId: Int): List<CompanyReview> {
        return reviewService.getReviews(companyId)
    }

    //회사 리뷰 추가하기
    @PostMapping("/{companyId}/reviews")
    fun create(
        @AuthUserId userId: String,
        @PathVariable("companyId") companyId: Int,
        @RequestBody dto: ReviewCreateDto
    ): CompanyReview {
        return reviewService.create(userId, companyId, dto)
    }
    //리뷰 업데이트
    @PutMapping("/reviews/{reviewId}")
    fun update(
        @AuthUserId userId: String,
        @PathVariable reviewId: Int,
        @RequestBody dto: ReviewUpdateDto,
    ): CompanyReview {
        return reviewService.update(userId, reviewId, dto)
    }
    //리뷰 삭제하기
    @DeleteMapping("/reviews/{reviewId}")
    fun delete(
        @AuthUserId userId: String,
        @PathVariable reviewId: Int
    ) {
        return reviewService.delete(userId, reviewId)
    }
    
}