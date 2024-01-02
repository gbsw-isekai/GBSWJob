package kr.hs.gbsw.gbswjob.company.controller

import kr.hs.gbsw.gbswjob.common.AuthUserId
import kr.hs.gbsw.gbswjob.company.domain.Company
import kr.hs.gbsw.gbswjob.company.dto.CompanyGetDto
import kr.hs.gbsw.gbswjob.company.dto.CreateCompanyDto
import kr.hs.gbsw.gbswjob.company.service.CompanyService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
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
    fun getCompanies(): List<CompanyGetDto> {
        return companyService.getCompanies()
    }

    //회사 일괄 조회
    @GetMapping("/{companyId}")
    fun getCompany(@PathVariable companyId: Int): Company {
        return companyService.getCompany(companyId)
    }

    //회사 조회수 추가
    @PostMapping("/{companyId}/views")
    fun countUp(
        @PathVariable companyId: Int,
        @AuthUserId userId: String?,
    ): String {
        return companyService.countUp(companyId, userId)
    }
}