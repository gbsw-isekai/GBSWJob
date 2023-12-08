package kr.hs.gbsw.gbswjob.company.service

import jakarta.persistence.Id
import kr.hs.gbsw.gbswjob.auth.authentication.provider.IdPwAuthenticationProvider
import kr.hs.gbsw.gbswjob.company.domain.Company
import kr.hs.gbsw.gbswjob.company.repository.CompanyRepository
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException
import java.util.*

@Service
class CompanyService(
    private val companyRepository: CompanyRepository,
) {
    fun getCompanies(): List<Company> {
        val company = companyRepository.findAll();
        return company;
    }

    fun getCompany(companyId: Int): Company {
        val company = companyRepository.findById(companyId).orElseThrow {
            IllegalArgumentException("회사가 존재하지 않습니다")
        }
        return company;
    }
}