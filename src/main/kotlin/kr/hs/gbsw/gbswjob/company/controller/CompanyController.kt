package kr.hs.gbsw.gbswjob.company.controller

import kr.hs.gbsw.gbswjob.common.AuthUserId
import kr.hs.gbsw.gbswjob.company.dto.CompanyGetDto
import kr.hs.gbsw.gbswjob.company.dto.CompanyListGetDto
import kr.hs.gbsw.gbswjob.company.dto.CompanyPriceGetDto
import kr.hs.gbsw.gbswjob.company.service.CompanyService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/companies")
class CompanyController(
    private val companyService: CompanyService
) {
    //회사 등록
//    @PostMapping
//    fun createCompany(@RequestBody dto: CreateCompanyDto): Company {
//        return companyService.create(dto)
//    }

    //회사 목록 조회
    @GetMapping
    fun getCompanies(
        @RequestParam query: String?,
        pageable: Pageable
    ): Page<CompanyListGetDto> {
        return companyService.getCompanies(query, pageable)
    }

    //회사 일괄 조회
    @GetMapping("/{companyId}")
    fun getCompany(@PathVariable companyId: Int): CompanyGetDto {
        return companyService.getCompany(companyId)
    }
    
    //회사 월별 금액 추가
    @GetMapping("/{companyId}/price")
    fun getCompanyPrice(@PathVariable companyId: Int): List<CompanyPriceGetDto> {
        return companyService.getCompanyPrice(companyId)
    }

    //회사 조회수 추가
    @PostMapping("/{companyId}")
    fun countUp(
        @PathVariable companyId: Int,
        @AuthUserId userId: String?,
    ): String {
        return companyService.countUp(companyId, userId)
    }
}