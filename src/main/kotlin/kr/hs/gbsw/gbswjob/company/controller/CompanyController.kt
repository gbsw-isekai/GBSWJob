package kr.hs.gbsw.gbswjob.company.controller

import jakarta.persistence.Id
import kr.hs.gbsw.gbswjob.company.domain.Company
import kr.hs.gbsw.gbswjob.company.service.CompanyService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/companies")
class CompanyController (
    private val service: CompanyService
){
    @GetMapping
    fun getCompanies(): List<Company> {
        return service.getCompanies()
    }

    @GetMapping("/{companyId}")
    fun getCompany(@PathVariable companyId: Int): Company {
        return service.getCompany(companyId)
    }
}