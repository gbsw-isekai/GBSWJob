package kr.hs.gbsw.gbswjob.company.service

import kr.hs.gbsw.gbswjob.company.domain.Company
import kr.hs.gbsw.gbswjob.company.domain.CompanyView
import kr.hs.gbsw.gbswjob.company.dto.CompanyGetDto
import kr.hs.gbsw.gbswjob.company.dto.CompanyListGetDto
import kr.hs.gbsw.gbswjob.company.repository.CompanyNpsEmployeeDataRepository
import kr.hs.gbsw.gbswjob.company.repository.CompanyRepository
import kr.hs.gbsw.gbswjob.company.repository.CompanyViewsRepository
import kr.hs.gbsw.gbswjob.user.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import kotlin.IllegalArgumentException
import kotlin.math.roundToInt
import kotlin.math.roundToLong

@Service
class CompanyService(
    private val companyRepository: CompanyRepository,
    private val companyViews: CompanyViewsRepository,
    private val repository: UserRepository,
    private val companyNpsEmployeeDataRepository: CompanyNpsEmployeeDataRepository
) {
//    fun create(dto: CreateCompanyDto): Company {
//        val company = Company(
//            null,
//            dto.name,
//            dto.stack,
//            dto.genre,
//            dto.area,
//            dto.averageSalary,
//            null,
//            null,
//            null,
//            0
//        )
//
//        return companyRepository.save(company)
//    }
    fun getCompanies(pageId: Int, searchId: String?): Page<CompanyListGetDto> {
        val pageable = PageRequest.of(pageId, 10, Sort.by(Sort.Direction.DESC, "viewCount"))

        if(searchId == null) {
            return companyRepository.findAll(pageable).map {
                CompanyListGetDto(
                    it.id,
                    it.name,
                    it.postalCode,
                    it.address,
                    it.industryCode,
                    it.industry,
                    it.registrationNumber,
                    it.viewCount
                )
            }
        } else {
            return companyRepository.findAllByName(pageable, searchId).map {
                CompanyListGetDto(
                    it.id,
                    it.name,
                    it.postalCode,
                    it.address,
                    it.industryCode,
                    it.industry,
                    it.registrationNumber,
                    it.viewCount
                )
            }
        }
    }

    fun getCompany(companyId: Int): CompanyGetDto {
        val company = companyRepository.findById(companyId).orElseThrow {
            IllegalArgumentException("회사가 존재하지 않습니다")
        }

        var averageYearPrice = 0L;

        companyNpsEmployeeDataRepository.findAllByCompanyId(companyId).map {
            averageYearPrice = (it.monthlyPrice.toInt() / 0.09 / it.total.toInt()).roundToLong()
        }

        return CompanyGetDto(
            company.id,
            company.name,
            company.postalCode,
            company.address,
            company.industryCode,
            company.industry,
            company.registrationNumber,
            company.viewCount,
            averageYearPrice
        )
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