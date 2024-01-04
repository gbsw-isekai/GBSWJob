package kr.hs.gbsw.gbswjob.company.service

import kr.hs.gbsw.gbswjob.company.domain.CompanyView
import kr.hs.gbsw.gbswjob.company.dto.CompanyGetDto
import kr.hs.gbsw.gbswjob.company.dto.CompanyListGetDto
import kr.hs.gbsw.gbswjob.company.dto.CompanyPriceGetDto
import kr.hs.gbsw.gbswjob.company.repository.CompanyNpsEmployeeDataRepository
import kr.hs.gbsw.gbswjob.company.repository.CompanyRepository
import kr.hs.gbsw.gbswjob.company.repository.CompanyViewsRepository
import kr.hs.gbsw.gbswjob.user.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.IllegalArgumentException
import kotlin.math.roundToLong

@Service
class CompanyService(
    private val companyRepository: CompanyRepository,
    private val companyViews: CompanyViewsRepository,
    private val repository: UserRepository,
    private val companyNpsEmployeeDataRepository: CompanyNpsEmployeeDataRepository
) {
    fun getCompanies(
        query: String?,
        pageable: Pageable
    ): Page<CompanyListGetDto> {
        var averageYearPrice = 0L;
        if (query != null) {
            return companyRepository.findByNameContaining(query ?: "", pageable).map {
                CompanyListGetDto(
                    it.id,
                    it.name,
                    it.postalCode,
                    it.address,
                    it.industryCode,
                    it.industry,
                    it.registrationNumber,
                    it.viewCount,
                    it.latestEmployeeCount,
                    it.latestAverageSalary
                )
            }
        } else {
            return companyRepository.findAll(pageable).map {
                CompanyListGetDto(
                    it.id,
                    it.name,
                    it.postalCode,
                    it.address,
                    it.industryCode,
                    it.industry,
                    it.registrationNumber,
                    it.viewCount,
                    it.latestEmployeeCount,
                    it.latestAverageSalary
                )
            }
        }
    }

    @Transactional(readOnly = true)
    fun getCompany(companyId: Int): CompanyGetDto {
        val company = companyRepository.findById(companyId).orElseThrow {
            IllegalArgumentException("회사가 존재하지 않습니다")
        }

//        val latestNpsData = company.companyNpsEmployeeData?.maxByOrNull { it.year * 10 + it.month }
//        val latestEmployeeCount = latestNpsData?.total
//        val averageYearPrice = (latestNpsData?.monthlyPrice?.div(0.09)?.div(latestEmployeeCount ?: 1));

        return CompanyGetDto(
            company.id,
            company.name,
            company.postalCode,
            company.address,
            company.industryCode,
            company.industry,
            company.registrationNumber,
            company.viewCount,
            company.latestEmployeeCount,
            company.latestAverageSalary,
            company.companyNpsEmployeeData,
            company.reviews
        )
    }

    fun getCompanyPrice(companyId: Int): List<CompanyPriceGetDto> {
        companyRepository.findById(companyId).orElseThrow {
            IllegalArgumentException("회사가 존재하지 않습니다.")
        }

        return companyNpsEmployeeDataRepository.findAllByCompanyId(companyId).map {
            CompanyPriceGetDto(
                it.month.toString(),
                (it.monthlyPrice.toInt() / 0.09 / it.total).toString()
            )
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