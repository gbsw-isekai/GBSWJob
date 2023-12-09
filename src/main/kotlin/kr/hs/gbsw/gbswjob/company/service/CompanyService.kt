package kr.hs.gbsw.gbswjob.company.service

import kr.hs.gbsw.gbswjob.company.domain.Company
import kr.hs.gbsw.gbswjob.company.dto.CompanyGetDto
import kr.hs.gbsw.gbswjob.company.dto.CreateCompanyDto
import kr.hs.gbsw.gbswjob.company.repository.CompanyRepository
import kr.hs.gbsw.gbswjob.company.repository.CompanyReviewRepository
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class CompanyService(
    private val companyRepository: CompanyRepository,
    private val companyReviewRepository: CompanyReviewRepository
) {
    fun create(dto: CreateCompanyDto): Company {
        val company = Company(
            null,
            dto.name,
            dto.stack,
            dto.averageSalary,
            null,
            null
        )

        return companyRepository.save(company)
    }
    fun getCompanies(): List<CompanyGetDto> {
        val company = companyRepository.findAll().map {
            CompanyGetDto(
                it.id,
                it.name,
                it.stack,
                it.calculateAverageRating(),
                it.averageSalary,
                it.comments,
                it.reviews
            )
        }
        return company;
    }

    fun getCompany(companyId: Int): Company {
        return companyRepository.findById(companyId).orElseThrow {
            IllegalArgumentException("회사가 존재하지 않습니다")
        }
    }
}