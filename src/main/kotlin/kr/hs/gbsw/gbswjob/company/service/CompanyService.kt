package kr.hs.gbsw.gbswjob.company.service

import kr.hs.gbsw.gbswjob.company.domain.Company
import kr.hs.gbsw.gbswjob.company.domain.CompanyView
import kr.hs.gbsw.gbswjob.company.dto.CompanyGetDto
import kr.hs.gbsw.gbswjob.company.dto.CreateCompanyDto
import kr.hs.gbsw.gbswjob.company.repository.CompanyRepository
import kr.hs.gbsw.gbswjob.company.repository.CompanyViewsRepository
import kr.hs.gbsw.gbswjob.user.repository.UserRepository
import org.springframework.stereotype.Service
import kotlin.IllegalArgumentException

@Service
class CompanyService(
    private val companyRepository: CompanyRepository,
    private val companyViews: CompanyViewsRepository,
    private val repository: UserRepository
) {
    fun create(dto: CreateCompanyDto): Company {
        val company = Company(
            null,
            dto.name,
            dto.stack,
            dto.averageSalary,
            null,
            null,
            null,
            0
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
                it.reviews,
                it.viewCount
            )
        }
        return company;
    }

    fun getCompany(companyId: Int): Company {
        return companyRepository.findById(companyId).orElseThrow {
            IllegalArgumentException("회사가 존재하지 않습니다")
        }
    }

    fun countUp(companyId: Int, userId: String?): String {
        val company = companyRepository.findById(companyId).orElseThrow {
            IllegalArgumentException("회사가 존재하지 않습니다")
        }

        val user = userId?.let {
            repository.findById(it)
            null
        }

        val views = CompanyView(
            null,
            user,
            company
        )

        company.viewCount += 1

        companyViews.save(views)

        return "조회 완료"
    }

}